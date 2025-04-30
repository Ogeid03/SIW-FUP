package it.uniroma3.siw.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import org.springframework.ui.Model;

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

    @PostMapping("/segnalazioni")
    public String salvaSegnalazione(@ModelAttribute Avvistamento segnalazione) {
        if (segnalazione == null) {
            System.out.println("L'oggetto segnalazione è null");
        } else {
            System.out.println("Dati ricevuti: " + segnalazione);
        }
        segnalazioneRepository.save(segnalazione);
        return "redirect:/conferma"; // oppure torna a una pagina di conferma
    }

    @GetMapping("/conferma")
    public String conferma() {
        return "homepage"; // Pagina di successo dopo l'invio del modulo
    }
}

