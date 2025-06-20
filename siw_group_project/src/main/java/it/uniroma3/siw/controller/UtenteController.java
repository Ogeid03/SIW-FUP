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
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;

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
        return "login";
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

        messaggioService.preparaModelloChatPerUtente(utente, model);

        return "myAccount";
    }

    @PostMapping("/segnalazioni/elimina/{id}")
    public String eliminaSegnalazione(@PathVariable Long id, HttpServletRequest request) {
        segnalazioneService.eliminaById(id);
        String referer = request.getHeader("referer");
        return "redirect:" + (referer != null ? referer : "/error");
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
            return "redirect:/error";
        }
    }

    @PostMapping("/segnalazioni/modifica/denuncia/{id}")
    public String salvaModificaDenuncia(@PathVariable Long id,
            @ModelAttribute Denuncia segnalazioneBase,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request) {

        Segnalazione originale = segnalazioneService.getSegnalazioneById(id).orElse(null);
        if (originale == null || !(originale instanceof Denuncia))
            return "redirect:/error";

        // Se il file è stato caricato ed è valido
        if (file != null && !file.isEmpty()) {
            try {
                String uploadDir = "uploads/";
                java.nio.file.Files.createDirectories(java.nio.file.Paths.get(uploadDir));
                String filename = java.util.UUID.randomUUID() + "_" + file.getOriginalFilename();
                java.nio.file.Path path = java.nio.file.Paths.get(uploadDir + filename);
                java.nio.file.Files.write(path, file.getBytes());
                originale.setFoto("/uploads/" + filename);
            } catch (Exception e) {
                e.printStackTrace();
                return "redirect:/error";
            }
        }

        setCampiComuni(originale, segnalazioneBase);

        Denuncia denuncia = (Denuncia) originale;
        if (segnalazioneBase.getNome() != null && !segnalazioneBase.getNome().isEmpty()) {
            denuncia.setNome(segnalazioneBase.getNome());
        }
        if (segnalazioneBase.getEta() != null && !segnalazioneBase.getEta().isEmpty()) {
            denuncia.setEta(segnalazioneBase.getEta());
        }
        if (segnalazioneBase.getPremioOfferto() != null) {
            denuncia.setPremioOfferto(segnalazioneBase.getPremioOfferto());
        }

        segnalazioneService.save(originale);

        return "redirect:/account";
    }

    @PostMapping("/segnalazioni/modifica/avvistamento/{id}")
    public String salvaModificaAvvistamento(@PathVariable Long id,
            @RequestParam("file") MultipartFile file,
            @ModelAttribute Avvistamento segnalazioneBase,
            HttpServletRequest request) {

        Segnalazione originale = segnalazioneService.getSegnalazioneById(id).orElse(null);
        if (originale == null || !(originale instanceof Avvistamento))
            return "redirect:/error";
        // Se il file è stato caricato ed è valido
        if (file != null && !file.isEmpty()) {
            try {
                String uploadDir = "uploads/";
                java.nio.file.Files.createDirectories(java.nio.file.Paths.get(uploadDir));
                String filename = java.util.UUID.randomUUID() + "_" + file.getOriginalFilename();
                java.nio.file.Path path = java.nio.file.Paths.get(uploadDir + filename);
                java.nio.file.Files.write(path, file.getBytes());
                originale.setFoto("/uploads/" + filename);
            } catch (Exception e) {
                e.printStackTrace();
                return "redirect:/error";
            }
        }

        setCampiComuni(originale, segnalazioneBase);

        Avvistamento avvistamento = (Avvistamento) originale;
        if (segnalazioneBase.getStatoSalute() != null && !segnalazioneBase.getStatoSalute().isEmpty()) {
            avvistamento.setStatoSalute(segnalazioneBase.getStatoSalute());
        }
        if (segnalazioneBase.getAzioniIntraprese() != null && !segnalazioneBase.getAzioniIntraprese().isEmpty()) {
            avvistamento.setAzioniIntraprese(segnalazioneBase.getAzioniIntraprese());
        }

        segnalazioneService.save(originale);

        return "redirect:/account";
    }

    // Metodo helper per campi comuni
    private void setCampiComuni(Segnalazione originale, Segnalazione segnalazioneBase) {
        originale.setSpecie(segnalazioneBase.getSpecie());
        originale.setRazza(segnalazioneBase.getRazza());
        originale.setLuogo(segnalazioneBase.getLuogo());
        originale.setCodSesso(segnalazioneBase.getCodSesso());
        originale.setDescrizioneFisica(segnalazioneBase.getDescrizioneFisica());
        // Aggiorna la foto solo se segnalazioneBase.getFoto() non è null e non vuota
        if (segnalazioneBase.getFoto() != null && !segnalazioneBase.getFoto().isEmpty()) {
            originale.setFoto(segnalazioneBase.getFoto());
        }
        originale.setCodStatus(segnalazioneBase.getCodStatus());
        originale.setDataOra(segnalazioneBase.getDataOra());
    }

}