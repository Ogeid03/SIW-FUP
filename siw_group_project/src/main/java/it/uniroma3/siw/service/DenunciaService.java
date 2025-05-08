package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Denuncia;
import it.uniroma3.siw.repository.DenunciaRepository;

@Service
public class DenunciaService {
    @Autowired
    private final DenunciaRepository denunciaRepository;

    public DenunciaService(DenunciaRepository denunciaRepository) {
        this.denunciaRepository = denunciaRepository;
    }

    public void salvaDenuncia(Denuncia denuncia) {
        denunciaRepository.save(denuncia);
    }
}
