package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import it.uniroma3.siw.model.Avvistamento;
import it.uniroma3.siw.model.Denuncia;
import it.uniroma3.siw.repository.AvvistamentoRepository;
import it.uniroma3.siw.repository.DenunciaRepository;

@Service
public class SegnalazioneService {

    @Autowired
    private final AvvistamentoRepository avvistamentoRepository;
    @Autowired
    private final DenunciaRepository denunciaRepository;

    public SegnalazioneService(AvvistamentoRepository avvistamentoRepository, DenunciaRepository denunciaRepository) {
        this.avvistamentoRepository = avvistamentoRepository;
        this.denunciaRepository = denunciaRepository;
    }

    public Optional<Avvistamento> getAvvistamentoById(Long id) {
        return avvistamentoRepository.findById(id);
    }

    public Optional<Denuncia> getDenunciaById(Long id) {
        return denunciaRepository.findById(id);
    }

    public List<Denuncia> getDenunceSimiliPerRazza(String razza, Long idDaEscludere) {
        return denunciaRepository.findByRazzaAndIdNot(razza, idDaEscludere);
    }

    public List<Avvistamento> getAvvistamentiSimiliPerRazza(String razza, Long idDaEscludere) {
        return avvistamentoRepository.findByRazzaAndIdNot(razza, idDaEscludere);
    }
}
