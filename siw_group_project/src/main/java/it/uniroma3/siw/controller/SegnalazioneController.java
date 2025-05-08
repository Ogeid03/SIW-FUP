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
import it.uniroma3.siw.service.SegnalazioneService;

@Controller
public class SegnalazioneController {

    @Autowired
    private final SegnalazioneService segnalazioneService;

    public SegnalazioneController(SegnalazioneService segnalazioneService) {
        this.segnalazioneService = segnalazioneService;
    }

    @GetMapping("/segnalazioni/{id:[0-9]+}")
    public String dettagliSegnalazione(@PathVariable Long id, Model model) {

        Optional<Avvistamento> avv = segnalazioneService.getAvvistamentoById(id);
        if (avv.isPresent()) {
            Avvistamento avvistamento = avv.get();
            model.addAttribute("segnalazione", avvistamento);
            model.addAttribute("tipo", "avvistamento");

            List<Denuncia> rilevanti = segnalazioneService.getDenunceSimiliPerRazza(avvistamento.getRazza(), id);
            model.addAttribute("rilevanti", rilevanti);

            return "segnalazione";
        }

        Optional<Denuncia> den = segnalazioneService.getDenunciaById(id);
        if (den.isPresent()) {
            Denuncia denuncia = den.get();
            model.addAttribute("segnalazione", denuncia);
            model.addAttribute("tipo", "denuncia");

            List<Avvistamento> simili = segnalazioneService.getAvvistamentiSimiliPerRazza(denuncia.getRazza(), id);
            model.addAttribute("simili", simili);

            return "segnalazione";
        }

        return "not-found";
    }
}
