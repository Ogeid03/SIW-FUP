package it.uniroma3.siw.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;
import it.uniroma3.siw.model.Messaggio;
import it.uniroma3.siw.model.Segnalazione;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.MessaggioService;
import it.uniroma3.siw.service.SegnalazioneService;
import it.uniroma3.siw.service.UtenteService;

@Controller
public class MessaggioController {
    @Autowired
    private SegnalazioneService segnalazioneService;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private MessaggioService messaggioService;

    @GetMapping("/messaggi/nuovo")
    public String nuovoMessaggio(@RequestParam Long segnalazioneId,
                                 @RequestParam Long destinatarioId,
                                 Model model, Principal principal) {
        Utente mittente = utenteService.getUtenteByEmail(principal.getName());
        Utente destinatario = utenteService.getUtenteById(destinatarioId);
        Optional<Segnalazione> optionalSegnalazione = segnalazioneService.getSegnalazioneById(segnalazioneId);

        if (optionalSegnalazione.isEmpty()) {
            return "redirect:/errore";
        }

        Segnalazione segnalazione = optionalSegnalazione.get();

        Messaggio m = new Messaggio();
        m.setCodUtente(mittente);
        m.setCodDestinatario(destinatario);
        m.setCodSegnalazione(segnalazione);

        model.addAttribute("messaggio", m);
        return "chat";
    }

    @PostMapping("/messaggi/invia")
    public String inviaMessaggio(@ModelAttribute("messaggio") Messaggio messaggio, Principal principal) {
        Utente mittente = utenteService.getUtenteByEmail(principal.getName());
        messaggio.setCodUtente(mittente);
        messaggio.setDataOra(LocalDateTime.now());
        messaggioService.save(messaggio);

        return "redirect:/chat/" + messaggio.getCodDestinatario().getId();
    }

    @GetMapping("/chat")
    public String chatDefaultView(Model model, Principal principal) {
        Utente utenteLoggato = utenteService.getUtenteByEmail(principal.getName());
        List<Utente> utentiConConversazioni = trovaUtentiConConversazioni(utenteLoggato);

        model.addAttribute("utentiConConversazioni", utentiConConversazioni);
        model.addAttribute("utenteLoggato", utenteLoggato);
        return "recapChat";
    }

    @GetMapping("/chat/{utenteId}")
    public String chatConUtente(@PathVariable Long utenteId, Model model, Principal principal) {
        Utente utenteLoggato = utenteService.getUtenteByEmail(principal.getName());
        Utente utenteSelezionato = utenteService.getUtenteById(utenteId);

        // Recupera messaggi tra i due utenti (in entrambi i sensi)
        List<Messaggio> messaggi = messaggioService.getMessaggiTraUtenti(utenteLoggato, utenteSelezionato);
        messaggi.addAll(messaggioService.getMessaggiTraUtenti(utenteSelezionato, utenteLoggato));
        messaggi.sort(Comparator.comparing(Messaggio::getDataOra)); // Ordine cronologico

        // Lista utenti con cui ha chattato
        List<Utente> utentiConConversazioni = trovaUtentiConConversazioni(utenteLoggato);

        model.addAttribute("messaggi", messaggi);
        model.addAttribute("utenteAttivo", utenteSelezionato);
        model.addAttribute("utentiConConversazioni", utentiConConversazioni);
        model.addAttribute("utenteLoggato", utenteLoggato);

        return "recapChat";
    }

    private List<Utente> trovaUtentiConConversazioni(Utente utente) {
        List<Messaggio> ricevuti = messaggioService.getMessaggiRicevuti(utente);
        List<Messaggio> inviati = messaggioService.getUtenteOrd(utente);

        Set<Utente> utenti = new HashSet<>();
        ricevuti.forEach(m -> utenti.add(m.getCodUtente()));
        inviati.forEach(m -> utenti.add(m.getCodDestinatario()));

        return utenti.stream()
                .filter(u -> !u.getId().equals(utente.getId())) // esclude se stesso
                .sorted(Comparator.comparing(Utente::getNomeUtente))
                .collect(Collectors.toList());
    }
}
