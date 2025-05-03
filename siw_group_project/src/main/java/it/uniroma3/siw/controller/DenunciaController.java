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
import it.uniroma3.siw.model.Denuncia;

import it.uniroma3.siw.repository.DenunciaRepository;


@Controller
public class DenunciaController {

     @Autowired
    private DenunciaRepository denunciaRepository;

    @GetMapping("/denunce")
    public String mostraFormDenunce(Model model) {
        model.addAttribute("denuncia", new Denuncia());
        return "denunciaForm";
    }

    @PostMapping("/conferma-denuncia")
        public String conferma(@ModelAttribute Denuncia denuncia,
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
                denuncia.setFoto("/uploads/" + fileName); // salva il path per mostrarlo nel sito
            } catch (IOException e) {
                e.printStackTrace();
                return "erroreCaricamento"; // puoi creare una pagina di errore
            }
        }

        denunciaRepository.save(denuncia);
        return "redirect:/";

    }
}
