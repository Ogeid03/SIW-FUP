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
public String conferma(@ModelAttribute Avvistamento avvistamento,
                       BindingResult bindingResult,
                       @RequestParam("foto") MultipartFile fotoFile) {
    if (bindingResult.hasErrors()) {
        System.out.println("Errori di binding: " + bindingResult.getAllErrors());
        return "segnalazioneForm";
    }

    if (!fotoFile.isEmpty()) {
        try {
            String uploadDir = "uploads/";
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Estrai l'estensione del file originale (es: ".jpg")
            String originalFilename = fotoFile.getOriginalFilename();
            String extension = "";
            int i = originalFilename.lastIndexOf('.');
            if (i > 0) {
                extension = originalFilename.substring(i);
            }

            // Trova il numero progressivo del nuovo file
            int fotoIndex = 1;
            while (Files.exists(uploadPath.resolve("foto" + fotoIndex + extension))) {
                fotoIndex++;
            }

            String fileName = "foto" + fotoIndex + extension;
            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath, fotoFile.getBytes());

            avvistamento.setFoto("/uploads/" + fileName);

        } catch (IOException e) {
            e.printStackTrace();
            return "error"; // pagina di errore personalizzata
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

