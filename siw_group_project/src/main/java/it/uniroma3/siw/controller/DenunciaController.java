package it.uniroma3.siw.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import it.uniroma3.siw.model.Denuncia;
import it.uniroma3.siw.model.Stato;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.DenunciaService;
import it.uniroma3.siw.service.UtenteService;

@Controller
public class DenunciaController {

    private final DenunciaService denunciaService;
    private final UtenteService utenteService;

    public DenunciaController(DenunciaService denunciaService, UtenteService utenteService) {
        this.denunciaService = denunciaService;
        this.utenteService = utenteService;
    }

    @GetMapping("/denunce")
    public String mostraForm(Model model) {
        model.addAttribute("denuncia", new Denuncia());
        return "denunciaForm";
    }

    @PostMapping("/conferma-denuncia")
    public String conferma(@ModelAttribute Denuncia denuncia, 
                                @RequestParam("latitudine") Double lat,
                                @RequestParam("longitudine") Double lon, 
                                @RequestParam("file") MultipartFile file,
                                Model model) {
        denuncia.setLatitudine(lat);
        denuncia.setLongitudine(lon);

        if (!file.isEmpty()) {
        try {
            String uploadDir = "uploads/";
            Files.createDirectories(Paths.get(uploadDir)); // crea la cartella se non esiste

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + filename);
            Files.write(path, file.getBytes());

            // Qui assegni il path relativo, non il MultipartFile
            denuncia.setFoto("/uploads/" + filename);

            

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("errore", "Errore durante il caricamento dell’immagine.");
            return "denunciaForm"; // torna alla form se fallisce
        }
    }

        
        var simili = denunciaService.trovaAvvistamentiSimili(denuncia);
        model.addAttribute("denuncia", denuncia);
        model.addAttribute("avvistamentiSimili", simili);
        return "recap-denuncia";
    }

    @PostMapping("/salva-denuncia")
    public String salvaDenuncia(@ModelAttribute Denuncia denuncia) {
        Utente utente = utenteService.getUtenteAutenticato();
        if (utente == null) {
            return "redirect:/login";
        }

        denuncia.setCodUtente(utente);
        denuncia.setDataOra(LocalDateTime.now());
        denuncia.setCodStatus(Stato.ATTIVO);
        denunciaService.salvaDenuncia(denuncia);
        return "redirect:/";
    }
}
