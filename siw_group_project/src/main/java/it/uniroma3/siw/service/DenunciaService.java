package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Avvistamento;
import it.uniroma3.siw.model.Denuncia;
import it.uniroma3.siw.model.Segnalazione;
import it.uniroma3.siw.repository.AvvistamentoRepository;
import it.uniroma3.siw.repository.DenunciaRepository;

@Service
public class DenunciaService {

    private final DenunciaRepository denunciaRepository;
    private final AvvistamentoRepository avvistamentoRepository;

    public DenunciaService(DenunciaRepository denunciaRepository, AvvistamentoRepository avvistamentoRepository) {
        this.denunciaRepository = denunciaRepository;
        this.avvistamentoRepository = avvistamentoRepository;
    }

    public void salvaDenuncia(Denuncia denuncia) {
        denunciaRepository.save(denuncia);
    }

    public List<Avvistamento> trovaAvvistamentiSimili(Segnalazione avvistamento) {
        // Esempio grezzo: puoi raffinarlo molto!
        return avvistamentoRepository.findBySpecieAndRazzaAndLuogoContainingIgnoreCase(
                avvistamento.getSpecie(),
                avvistamento.getRazza(),
                avvistamento.getLuogo());
    }

    
}
