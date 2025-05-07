package it.uniroma3.siw.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



import it.uniroma3.siw.model.Avvistamento;
import it.uniroma3.siw.model.Denuncia;
import it.uniroma3.siw.repository.AvvistamentoRepository;
import it.uniroma3.siw.repository.DenunciaRepository;

@Controller
public class SegnalazioneController {

    @Autowired
    private AvvistamentoRepository avvistamentoRepository;

    @Autowired
    private DenunciaRepository denunciaRepository;

    @GetMapping("/segnalazioni/{id:[0-9]+}")
    public String dettagliSegnalazione(@PathVariable Long id, Model model) {
        // Recupera la segnalazione (avvistamento o denuncia)
        Optional<Avvistamento> avv = avvistamentoRepository.findById(id);
        if (avv.isPresent()) {
            model.addAttribute("segnalazione", avv.get());
            model.addAttribute("tipo", "avvistamento");

            // Recupera gli avvistamenti simili per razza (escludendo l'id dell'attuale segnalazione)
            List<Denuncia> rilevanti = denunciaRepository.findByRazzaAndIdNot(avv.get().getRazza(), id);
            model.addAttribute("rilevanti", rilevanti);

            return "segnalazione";  // Ritorna il template segnalazione
        }

        Optional<Denuncia> den = denunciaRepository.findById(id);
        if (den.isPresent()) {
            model.addAttribute("segnalazione", den.get());
            model.addAttribute("tipo", "denuncia");

            List<Avvistamento> simili = avvistamentoRepository.findByRazzaAndIdNot(den.get().getRazza(), id);
            model.addAttribute("simili", simili);

            return "segnalazione";  // Ritorna il template segnalazione
        }

        return "not-found";  // Se non troviamo la segnalazione
    }
}
