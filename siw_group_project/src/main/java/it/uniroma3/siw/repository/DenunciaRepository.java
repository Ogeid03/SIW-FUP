package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import it.uniroma3.siw.model.Denuncia;



public interface DenunciaRepository extends JpaRepository<Denuncia, Long> {
    List<Denuncia> findAll();
}
