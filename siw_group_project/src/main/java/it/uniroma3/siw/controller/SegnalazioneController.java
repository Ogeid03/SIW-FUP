package it.uniroma3.siw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.ui.Model;
import java.util.List;
import it.uniroma3.siw.model.Avvistamento;
import it.uniroma3.siw.model.Denuncia;
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
    public String conferma(@ModelAttribute Avvistamento avvistamento, Model model) {
        model.addAttribute("segnalazione", avvistamento);
        return "recap-avvistamento"; // Pagina di successo dopo l'invio del modulo
    }

    // Conferma e salva la denuncia nel database
    @PostMapping("/salva-avvistamento")
    public String salvaDenuncia(@ModelAttribute Avvistamento avvistamento) {
        segnalazioneRepository.save(avvistamento); // Salva i dati nel database
        return "redirect:/"; // Torna alla homepage o un'altra pagina di conferma
    }

    @GetMapping("/carosello")
    public String mostraCarosello(Model model) {
        List<Avvistamento> avvistamenti = segnalazioneRepository.findAll();
        model.addAttribute("avvistamenti", avvistamenti);
        return "allAvvistamenti"; // nome del template Thymeleaf
    }
}

