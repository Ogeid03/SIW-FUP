package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.model.Avvistamento;



public interface AvvistamentoRepository extends JpaRepository<Avvistamento, Long> {
    List<Avvistamento> findAll();
    List<Avvistamento> findByRazzaAndIdNot(String razza, Long id);
    List<Avvistamento> findByRazza(String razza);
}
