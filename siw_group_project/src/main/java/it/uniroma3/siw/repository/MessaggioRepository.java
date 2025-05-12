package it.uniroma3.siw.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Messaggio;
import it.uniroma3.siw.model.Utente;

public interface MessaggioRepository extends CrudRepository<Messaggio, Long>{
    List<Messaggio> findByCodUtenteOrCodDestinatarioOrderByDataOraDesc(Utente mittente, Utente destinatario);
}
