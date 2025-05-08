package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import java.util.List;

import it.uniroma3.siw.model.Avvistamento;
import it.uniroma3.siw.service.AvvistamentoService;

@Controller
public class AvvistamentoController {
    @Autowired
    private final AvvistamentoService avvistamentoService;

    public AvvistamentoController(AvvistamentoService avvistamentoService) {
        this.avvistamentoService = avvistamentoService;
    }

    @GetMapping("/segnalazioni")
    public String mostraFormSegnalazione(Model model) {
        model.addAttribute("segnalazione", new Avvistamento());
        return "segnalazioneForm";
    }

    @PostMapping("/conferma-avvistamento")
    public String conferma(@ModelAttribute Avvistamento avvistamento, Model model) {
        model.addAttribute("segnalazione", avvistamento);
        return "recap-avvistamento";
    }

    @PostMapping("/salva-avvistamento")
    public String salvaDenuncia(@ModelAttribute Avvistamento avvistamento) {
        avvistamentoService.salvaAvvistamento(avvistamento);
        return "redirect:/";
    }

    @GetMapping("/carosello")
    public String mostraCarosello(Model model) {
        List<Avvistamento> avvistamenti = avvistamentoService.getTuttiGliAvvistamenti();
        model.addAttribute("avvistamenti", avvistamenti);
        return "allAvvistamenti";
    }
}
