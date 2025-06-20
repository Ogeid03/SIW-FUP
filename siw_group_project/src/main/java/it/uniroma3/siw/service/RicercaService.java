package it.uniroma3.siw.service;

import java.util.List;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.model.Avvistamento;
import it.uniroma3.siw.model.Denuncia;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.DenunciaRepository;
import it.uniroma3.siw.repository.AvvistamentoRepository;

@Service
public class RicercaService {

    private final AvvistamentoRepository segnalazioneRepository;
    private final DenunciaRepository denunciaRepository;

    
    public RicercaService(AvvistamentoRepository segnalazioneRepository, DenunciaRepository denunciaRepository) {
        this.segnalazioneRepository = segnalazioneRepository;
        this.denunciaRepository = denunciaRepository;
    }

    public List<Avvistamento> getTuttiGliAvvistamenti() {
        return segnalazioneRepository.findAllByOrderByDataOraDesc();
    }

    public List<Denuncia> getTutteLeDenunce() {
        return denunciaRepository.findAllByOrderByDataOraDesc();
    }

    public List<Avvistamento> getAvvByUtente(Utente utente) {
        return segnalazioneRepository.findByCodUtente(utente);
    }

    public List<Denuncia> getDenByUtente(Utente utente) {
        return denunciaRepository.findByCodUtente(utente);
    }
}
