package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        List<Messaggio> tutti = new ArrayList<>();
        tutti.addAll(m1);
        tutti.addAll(m2);
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
}
