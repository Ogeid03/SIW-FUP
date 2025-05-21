package it.uniroma3.siw.service;

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

    public List<Messaggio> getUtenteOrd(Utente mittente){
        return messaggioRepository.findByCodUtenteOrderByDataOraDesc(mittente);
    }

    public List<Messaggio> getMessaggiTraUtenti(Utente mittente, Utente destinatario){
        return messaggioRepository.findByCodUtenteAndCodDestinatario(mittente, destinatario);
    }

    public void save(Messaggio messaggio) {
        messaggioRepository.save(messaggio);
    }
}
