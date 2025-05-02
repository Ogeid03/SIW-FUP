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

    @PostMapping("/conferma")
        public String conferma(@ModelAttribute Avvistamento avvistamento,
                        @RequestParam("foto") MultipartFile fotoFile) {
        if (!fotoFile.isEmpty()) {
            try {
                String uploadDir = "uploads/";
                String fileName = System.currentTimeMillis() + "_" + fotoFile.getOriginalFilename();
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Path filePath = uploadPath.resolve(fileName);
                Files.write(filePath, fotoFile.getBytes());
                avvistamento.setFoto("/uploads/" + fileName); // salva il path per mostrarlo nel sito
            } catch (IOException e) {
                e.printStackTrace();
                return "erroreCaricamento"; // puoi creare una pagina di errore
            }
        }

        segnalazioneRepository.save(avvistamento);
        return "redirect:/";
    }

    @GetMapping("/carosello")
    public String mostraCarosello(Model model) {
        List<Avvistamento> avvistamenti = segnalazioneRepository.findAll();
        model.addAttribute("avvistamenti", avvistamenti);
        return "allAvvistamenti"; // nome del template Thymeleaf
    }
}

