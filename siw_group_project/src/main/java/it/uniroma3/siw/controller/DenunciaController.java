package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import it.uniroma3.siw.model.Denuncia;
import it.uniroma3.siw.service.DenunciaService;

@Controller
public class DenunciaController {
    @Autowired
    private final DenunciaService denunciaService;

    public DenunciaController(DenunciaService denunciaService) {
        this.denunciaService = denunciaService;
    }

    @GetMapping("/denunce")
    public String mostraForm(Model model) {
        model.addAttribute("denuncia", new Denuncia());
        return "denunciaForm";
    }

    @PostMapping("/conferma-denuncia")
    public String conferma(@ModelAttribute Denuncia denuncia, Model model) {
        model.addAttribute("denuncia", denuncia);
        return "recap-denuncia";
    }

    @PostMapping("/salva-denuncia")
    public String salvaDenuncia(@ModelAttribute Denuncia denuncia) {
        denunciaService.salvaDenuncia(denuncia);
        return "redirect:/";
    }
}
