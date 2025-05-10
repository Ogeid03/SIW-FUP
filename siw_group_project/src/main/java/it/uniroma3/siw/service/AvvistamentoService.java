package it.uniroma3.siw.service;

import org.springframework.stereotype.Service;

import java.util.List;

import it.uniroma3.siw.model.Avvistamento;
import it.uniroma3.siw.model.Denuncia;
import it.uniroma3.siw.model.Segnalazione;
import it.uniroma3.siw.repository.AvvistamentoRepository;
import it.uniroma3.siw.repository.DenunciaRepository;

@Service
public class AvvistamentoService {
    
    private final AvvistamentoRepository avvistamentoRepository;
    private final DenunciaRepository denunciaRepository;

    public AvvistamentoService(AvvistamentoRepository avvistamentoRepository, DenunciaRepository denunciaRepository) {
        this.avvistamentoRepository = avvistamentoRepository;
        this.denunciaRepository = denunciaRepository;
    }

    public void salvaAvvistamento(Avvistamento avvistamento) {
        avvistamentoRepository.save(avvistamento);
    }

    public List<Avvistamento> getTuttiGliAvvistamenti() {
        return avvistamentoRepository.findAll();
    }

    public List<Denuncia> trovaDenunceSimili(Segnalazione denuncia) {
        // Esempio grezzo: puoi raffinarlo molto!
        return denunciaRepository.findBySpecieAndRazzaAndLuogoContainingIgnoreCase(
            denuncia.getSpecie(), 
            denuncia.getRazza(), 
            denuncia.getLuogo()
        );
    }
}
