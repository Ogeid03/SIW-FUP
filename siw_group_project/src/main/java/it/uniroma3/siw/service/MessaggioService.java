package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import it.uniroma3.siw.model.Messaggio;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.MessaggioRepository;

@Service
public class MessaggioService {

    @Autowired
    private MessaggioRepository messaggioRepository;

    public List<Messaggio> getMessaggiRicevuti(Utente destinatario) {
        return messaggioRepository.findByCodDestinatarioOrderByDataOraDesc(destinatario);
    }

    public List<Messaggio> getUtenteOrd(Utente mittente) {
        return messaggioRepository.findByCodUtenteOrderByDataOraDesc(mittente);
    }

    public List<Messaggio> getMessaggiTraUtenti(Utente mittente, Utente destinatario) {
        return messaggioRepository.findByCodUtenteAndCodDestinatario(mittente, destinatario);
    }

    public void save(Messaggio messaggio) {
        messaggioRepository.save(messaggio);
    }

    public List<Messaggio> getConversazioneCompleta(Utente u1, Utente u2) {
        List<Messaggio> m1 = messaggioRepository.findByCodUtenteAndCodDestinatarioOrderByDataOraAsc(u1, u2);
        List<Messaggio> m2 = messaggioRepository.findByCodUtenteAndCodDestinatarioOrderByDataOraAsc(u2, u1);
        Set<Messaggio> setMessaggi = new HashSet<>();
        setMessaggi.addAll(m1);
        setMessaggi.addAll(m2);
        List<Messaggio> tutti = new ArrayList<>(setMessaggi);
        tutti.sort(Comparator.comparing(Messaggio::getDataOra));
        return tutti;
    }

    public List<Messaggio> getTuttiMessaggiUtente(Utente utente) {
        List<Messaggio> inviati = messaggioRepository.findByCodUtente(utente);
        List<Messaggio> ricevuti = messaggioRepository.findByCodDestinatario(utente);
        List<Messaggio> unione = new ArrayList<>(inviati);
        unione.addAll(ricevuti);
        return unione;
    }

    public void preparaModelloChatPerUtente(Utente utente, Model model) {
        List<Utente> utentiConConversazioni = getUtentiConConversazioni(utente);

        if (!utentiConConversazioni.isEmpty()) {
            Utente utenteSelezionato = utentiConConversazioni.get(0);
            List<Messaggio> messaggi = getConversazioneCompleta(utente, utenteSelezionato);
            List<Messaggio> messaggiInversi = new ArrayList<>(messaggi);
            Collections.reverse(messaggiInversi);

            model.addAttribute("messaggio", new Messaggio());
            model.addAttribute("messaggi", messaggiInversi);
            model.addAttribute("utenteAttivo", utenteSelezionato);
        }

        model.addAttribute("utentiConConversazioni", utentiConConversazioni);
        model.addAttribute("utenteLoggato", utente);
    }

    private List<Utente> getUtentiConConversazioni(Utente utente) {
        List<Messaggio> ricevuti = getMessaggiRicevuti(utente);
        List<Messaggio> inviati = getUtenteOrd(utente);

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
                .toList();
    }

}
