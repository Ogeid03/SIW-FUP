package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import java.util.List;

import it.uniroma3.siw.model.Avvistamento;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.AvvistamentoService;
import it.uniroma3.siw.service.UtenteService;

@Controller
public class AvvistamentoController {
    
    private final AvvistamentoService avvistamentoService;
    private final UtenteService utenteService;

    public AvvistamentoController(AvvistamentoService avvistamentoService, UtenteService utenteService) {
        this.avvistamentoService = avvistamentoService;
        this.utenteService = utenteService;
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
    public String salvaAvvistamento(@ModelAttribute Avvistamento avvistamento) {
         Utente utente = utenteService.getUtenteAutenticato();
        if (utente == null) {
            return "redirect:/login";
        }
        avvistamento.setCodUtente(utente);
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
