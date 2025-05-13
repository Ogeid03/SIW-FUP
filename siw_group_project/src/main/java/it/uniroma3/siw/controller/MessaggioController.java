package it.uniroma3.siw.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.ui.Model;
import it.uniroma3.siw.model.Messaggio;
import it.uniroma3.siw.model.Segnalazione;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.MessaggioRepository;
import it.uniroma3.siw.service.SegnalazioneService;
import it.uniroma3.siw.service.UtenteService;

@Controller
public class MessaggioController {
    @Autowired
    private SegnalazioneService segnalazioneService;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private MessaggioRepository messaggioRepository;

    @GetMapping("/messaggi/nuovo")
    public String nuovoMessaggio(@RequestParam Long segnalazioneId,
            @RequestParam Long destinatarioId,
            Model model, Principal principal) {
        Utente mittente = utenteService.getUtenteByEmail(principal.getName());
        Utente destinatario = utenteService.getUtenteById(destinatarioId);
        Optional<Segnalazione> optionalSegnalazione = segnalazioneService.getSegnalazioneById(segnalazioneId);

        if (optionalSegnalazione.isEmpty()) {
            // Se la segnalazione non esiste, gestisci il caso (es. redirect o errore)
            return "redirect:/errore"; // oppure mostra un messaggio nel model
        }

        Segnalazione segnalazione = optionalSegnalazione.get();

        Messaggio m = new Messaggio();
        m.setCodUtente(mittente);
        m.setCodDestinatario(destinatario);
        m.setCodSegnalazione(segnalazione);

        model.addAttribute("messaggio", m);
        return "chat";
    }

    @PostMapping("/invia")
    public String inviaMessaggio(@ModelAttribute("messaggio") Messaggio messaggio) {
        messaggio.setDataOra(LocalDateTime.now());
        messaggioRepository.save(messaggio);
        return "redirect:/segnalazioni/" + messaggio.getCodSegnalazione().getId();
    }
}
