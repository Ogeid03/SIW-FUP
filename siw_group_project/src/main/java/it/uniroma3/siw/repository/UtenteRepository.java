package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.model.Utente;

@Repository
public interface UtenteRepository extends CrudRepository<Utente, Long>{
    Utente findByEmail(String email);
    
    @Query("SELECT u FROM Utente u WHERE u.id = :id")
    Utente getUtenteById(@Param("id") Long id);

    Utente findByNomeUtente(String nomeUtente);
    
}