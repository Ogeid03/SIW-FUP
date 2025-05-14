package it.uniroma3.siw.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import it.uniroma3.siw.model.Messaggio;
import it.uniroma3.siw.model.Utente;

@Repository
public interface MessaggioRepository extends JpaRepository<Messaggio, Long> {
    List<Messaggio> findByCodDestinatarioOrderByDataOraDesc(Utente codDestinatario);
}
