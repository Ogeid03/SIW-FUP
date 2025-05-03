package it.uniroma3.siw.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.ui.Model;


import it.uniroma3.siw.model.Denuncia;



@Controller
public class DenunciaController {

    @GetMapping("/denunce")
    public String mostraForm(Model model) {
        model.addAttribute("denuncia", new Denuncia());
        return "denunciaForm";
    }

    @PostMapping("/conferma-denuncia")
    public String conferma(@ModelAttribute Denuncia denuncia, Model model) {
        model.addAttribute("denuncia", denuncia);
        return "recap-denuncia"; // nome della view di recap
    }
}


