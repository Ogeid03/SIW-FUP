package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import it.uniroma3.siw.model.Avvistamento;
import it.uniroma3.siw.repository.AvvistamentoRepository;

@Service
public class AvvistamentoService {
    @Autowired
    private final AvvistamentoRepository avvistamentoRepository;

    
    public AvvistamentoService(AvvistamentoRepository avvistamentoRepository) {
        this.avvistamentoRepository = avvistamentoRepository;
    }

    public void salvaAvvistamento(Avvistamento avvistamento) {
        avvistamentoRepository.save(avvistamento);
    }

    public List<Avvistamento> getTuttiGliAvvistamenti() {
        return avvistamentoRepository.findAll();
    }
}
