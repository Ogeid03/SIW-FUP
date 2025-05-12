package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;

import it.uniroma3.siw.model.Avvistamento;
import it.uniroma3.siw.model.Stato;
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
    public String conferma(@ModelAttribute Avvistamento avvistamento, 
                                @RequestParam("latitudine") Double lat,
                                @RequestParam("longitudine") Double lon, 
                                Model model) {
        avvistamento.setLatitudine(lat);
        avvistamento.setLongitudine(lon);
        model.addAttribute("segnalazione", avvistamento);
        var simili = avvistamentoService.trovaDenunceSimili(avvistamento);
        model.addAttribute("simili", simili);
        return "recap-avvistamento";
    }

    @PostMapping("/salva-avvistamento")
    public String salvaAvvistamento(@ModelAttribute Avvistamento avvistamento) {
         Utente utente = utenteService.getUtenteAutenticato();
        if (utente == null) {
            return "redirect:/login";
        }
        avvistamento.setCodUtente(utente);
        avvistamento.setDataOra(LocalDateTime.now());
        avvistamento.setCodStatus(Stato.ATTIVO);
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
