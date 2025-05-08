package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Avvistamento;
import it.uniroma3.siw.model.Denuncia;
import it.uniroma3.siw.repository.DenunciaRepository;
import it.uniroma3.siw.repository.AvvistamentoRepository;

@Service
public class RicercaService {
    @Autowired
    private final AvvistamentoRepository segnalazioneRepository;
    @Autowired
    private final DenunciaRepository denunciaRepository;

    
    public RicercaService(AvvistamentoRepository segnalazioneRepository, DenunciaRepository denunciaRepository) {
        this.segnalazioneRepository = segnalazioneRepository;
        this.denunciaRepository = denunciaRepository;
    }

    public List<Avvistamento> getTuttiGliAvvistamenti() {
        return segnalazioneRepository.findAll();
    }

    public List<Denuncia> getTutteLeDenunce() {
        return denunciaRepository.findAll();
    }
}
