package it.uniroma3.siw.controller;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.nio.file.*;
import java.util.List;

import it.uniroma3.siw.model.Avvistamento;

import it.uniroma3.siw.repository.SegnalazioneRepository;



@Controller
public class SegnalazioneController {

    @Autowired
    private SegnalazioneRepository segnalazioneRepository;

    @GetMapping("/segnalazioni")
    public String mostraFormSegnalazione(Model model) {
        model.addAttribute("segnalazione", new Avvistamento());
        return "segnalazioneForm";
    }

    @PostMapping("/conferma-avvistamento")
    public String conferma(@ModelAttribute Avvistamento avvistamento) {
        segnalazioneRepository.save(avvistamento);
        return "redirect:/"; // Pagina di successo dopo l'invio del modulo
    }


    @GetMapping("/carosello")
    public String mostraCarosello(Model model) {
        List<Avvistamento> avvistamenti = segnalazioneRepository.findAll();
        model.addAttribute("avvistamenti", avvistamenti);
        return "allAvvistamenti"; // nome del template Thymeleaf
    }
}

