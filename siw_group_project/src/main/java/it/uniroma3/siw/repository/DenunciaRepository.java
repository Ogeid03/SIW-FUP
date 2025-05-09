package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.model.Denuncia;
import it.uniroma3.siw.model.Utente;



public interface DenunciaRepository extends JpaRepository<Denuncia, Long> {
    List<Denuncia> findAll();
    List<Denuncia> findByRazzaAndIdNot(String razza, Long id);
    List<Denuncia> findByRazza(String razza);
    List<Denuncia> findByCodUtente(Utente utente);
}
