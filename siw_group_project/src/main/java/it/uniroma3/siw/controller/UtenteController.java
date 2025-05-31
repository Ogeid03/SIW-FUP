package it.uniroma3.siw.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Avvistamento;
import it.uniroma3.siw.model.Denuncia;
import it.uniroma3.siw.model.Messaggio;
import it.uniroma3.siw.model.Segnalazione;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.MessaggioService;
import it.uniroma3.siw.service.RicercaService;
import it.uniroma3.siw.service.SegnalazioneService;
import it.uniroma3.siw.service.UtenteService;

@Controller
public class UtenteController {

    @Autowired
    private UtenteService utenteService;
    @Autowired
    private SegnalazioneService segnalazioneService;
    @Autowired
    private RicercaService ricercaService;
    @Autowired
    private MessaggioService messaggioService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Pagina di login
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("utente", new Utente());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute Utente utente,
            @RequestParam("password") String password) {
        utenteService.register(utente, password);
        return "login";
    }

    @GetMapping("/account")
    public String getSegnalazioniUtente(Model model) {
        Utente utente = utenteService.getUtenteAutenticato();

        if (utente == null) {
            return "redirect:/error";
        }

        List<Denuncia> denunce = ricercaService.getDenByUtente(utente);
        List<Avvistamento> avvistamenti = ricercaService.getAvvByUtente(utente);
        List<Messaggio> messaggiRicevuti = messaggioService.getMessaggiRicevuti(utente);

        model.addAttribute("utente", utente);
        model.addAttribute("denunce", denunce);
        model.addAttribute("avvistamenti", avvistamenti);
        model.addAttribute("messaggiRicevuti", messaggiRicevuti);

        return "myAccount";
    }

    @PostMapping("/segnalazioni/elimina/{id}")
    public String eliminaSegnalazione(@PathVariable Long id) {
        segnalazioneService.eliminaById(id);
        return "redirect:/account";
    }

    @GetMapping("/segnalazioni/modifica/{id}")
    public String mostraFormModifica(@PathVariable Long id, Model model) {
        Optional<Segnalazione> segnalazioneOpt = segnalazioneService.getSegnalazioneById(id);

        if (segnalazioneOpt.isPresent()) {
            Segnalazione segnalazione = segnalazioneOpt.get();
            model.addAttribute("segnalazione", segnalazione);

            String tipoSegnalazione;
            if (segnalazione instanceof Denuncia) {
                tipoSegnalazione = "denuncia";
            } else if (segnalazione instanceof Avvistamento) {
                tipoSegnalazione = "avvistamento";
            } else {
                tipoSegnalazione = "generico";
            }

            model.addAttribute("tipoSegnalazione", tipoSegnalazione);
            return "modificaSegnalazione";
        } else {
            return "redirect:/error"; // oppure puoi mostrare un messaggio di errore
        }
    }

    @PostMapping("/segnalazioni/modifica/{id}")
    public String salvaModifica(@PathVariable Long id,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String eta,
            @RequestParam(required = false) Double premioOfferto,
            @RequestParam(required = false) String statoSalute,
            @RequestParam(required = false) String azioniIntraprese,
            @ModelAttribute Segnalazione segnalazioneBase) {

        Segnalazione originale = segnalazioneService.getSegnalazioneById(id).orElse(null);
        if (originale == null)
            return "redirect:/error";

        // Set campi comuni
        originale.setSpecie(segnalazioneBase.getSpecie());
        originale.setRazza(segnalazioneBase.getRazza());
        originale.setLuogo(segnalazioneBase.getLuogo());
        originale.setDescrizioneFisica(segnalazioneBase.getDescrizioneFisica());
        originale.setFoto(segnalazioneBase.getFoto());
        originale.setCodStatus(segnalazioneBase.getCodStatus());
        originale.setDataOra(segnalazioneBase.getDataOra());

        if (originale instanceof Denuncia) {
            Denuncia denuncia = (Denuncia) originale;
            denuncia.setNome(nome);
            denuncia.setEta(eta);
            denuncia.setPremioOfferto(premioOfferto);
        } else if (originale instanceof Avvistamento) {
            Avvistamento avv = (Avvistamento) originale;
            avv.setStatoSalute(statoSalute);
            avv.setAzioniIntraprese(azioniIntraprese);
        }

        segnalazioneService.save(originale); // <-- aggiungi un metodo save() al service se serve
        return "redirect:/account";
    }

}
