package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
                      @RequestParam("file") MultipartFile file,
                      Model model) {
    avvistamento.setLatitudine(lat);
    avvistamento.setLongitudine(lon);

    if (!file.isEmpty()) {
        try {
            String uploadDir = "uploads/";
            Files.createDirectories(Paths.get(uploadDir));

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + filename);
            Files.write(path, file.getBytes());

            avvistamento.setFoto("/uploads/" + filename);

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("errore", "Errore durante il caricamento dell’immagine.");
            return "segnalazioneForm"; 
        }
    }

    
    model.addAttribute("segnalazione", avvistamento);
    var simili = avvistamentoService.trovaDenunceSimili(avvistamento);
    model.addAttribute("denunceRilevanti", simili);
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
