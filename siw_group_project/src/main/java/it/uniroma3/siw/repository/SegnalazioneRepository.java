package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.model.Avvistamento;



public interface SegnalazioneRepository extends JpaRepository<Avvistamento, Long> {
    List<Avvistamento> findAll();
}
