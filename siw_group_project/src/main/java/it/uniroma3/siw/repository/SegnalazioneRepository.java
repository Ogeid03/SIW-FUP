package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.model.Avvistamento;



public interface SegnalazioneRepository extends JpaRepository<Avvistamento, Long> {}
