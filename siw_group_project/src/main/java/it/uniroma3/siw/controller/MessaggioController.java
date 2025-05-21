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
            return "redirect:/error";
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

        if (messaggio.getCodDestinatario() == null || messaggio.getCodDestinatario().getId() == null ||
                messaggio.getCodSegnalazione() == null || messaggio.getCodSegnalazione().getId() == null) {
            // gestione errore, es. redirect con messaggio di errore
            return "redirect:/error";
        }

        Long destinatarioId = messaggio.getCodDestinatario().getId();
        Long segnalazioneId = messaggio.getCodSegnalazione().getId();

        messaggio.setCodDestinatario(utenteService.getUtenteById(destinatarioId));
        messaggio.setCodSegnalazione(segnalazioneService.getSegnalazioneById(segnalazioneId).orElse(null));

        messaggioService.save(messaggio);

        return "redirect:/chat/" + destinatarioId;
    }

    @GetMapping("/chat")
    public String chatDefaultView(Model model, Principal principal) {
        Utente utenteLoggato = utenteService.getUtenteByEmail(principal.getName());
        List<Utente> utentiConConversazioni = trovaUtentiConConversazioni(utenteLoggato);

        if (!utentiConConversazioni.isEmpty()) {
            Utente utenteSelezionato = utentiConConversazioni.get(0);
            return "redirect:/chat/" + utenteSelezionato.getId();
        }

        model.addAttribute("utentiConConversazioni", utentiConConversazioni);
        model.addAttribute("utenteLoggato", utenteLoggato);
        return "recapChat";
    }

    @GetMapping("/chat/{utenteId}")
    public String chatConUtente(@PathVariable Long utenteId, Model model, Principal principal) {
        Utente utenteLoggato = utenteService.getUtenteByEmail(principal.getName());
        Utente utenteSelezionato = utenteService.getUtenteById(utenteId);

        // Recupera messaggi tra i due utenti (in entrambi i sensi)
        List<Messaggio> messaggi = messaggioService.getConversazioneCompleta(utenteLoggato, utenteSelezionato);

        // Invertiamo la lista per mostrare i messaggi dal più vecchio al più recente
        // (in fondo)
        List<Messaggio> messaggiInversi = new ArrayList<>(messaggi);
        Collections.reverse(messaggiInversi);

        // Lista utenti con cui ha chattato
        List<Utente> utentiConConversazioni = trovaUtentiConConversazioni(utenteLoggato);
        model.addAttribute("messaggio", new Messaggio());
        model.addAttribute("messaggi", messaggiInversi);
        model.addAttribute("utenteAttivo", utenteSelezionato);
        model.addAttribute("utentiConConversazioni", utentiConConversazioni);
        model.addAttribute("utenteLoggato", utenteLoggato);

        return "recapChat";
    }

    private List<Utente> trovaUtentiConConversazioni(Utente utente) {
        List<Messaggio> ricevuti = messaggioService.getMessaggiRicevuti(utente);
        List<Messaggio> inviati = messaggioService.getUtenteOrd(utente);

        Set<Utente> utenti = new HashSet<>();
        for (Messaggio m : ricevuti) {
            if (m.getCodUtente() != null)
                utenti.add(m.getCodUtente());
        }
        for (Messaggio m : inviati) {
            if (m.getCodDestinatario() != null)
                utenti.add(m.getCodDestinatario());
        }

        utenti.removeIf(u -> u.getId().equals(utente.getId()));

        return utenti.stream()
                .sorted(Comparator.comparing(Utente::getNomeUtente))
                .collect(Collectors.toList());
    }

}
