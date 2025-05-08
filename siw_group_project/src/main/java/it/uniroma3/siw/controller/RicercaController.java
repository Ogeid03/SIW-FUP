package it.uniroma3.siw.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.model.Avvistamento;
import it.uniroma3.siw.model.Denuncia;
import it.uniroma3.siw.service.RicercaService;

@Controller
public class RicercaController {
    
    @Autowired
    private final RicercaService ricercaService;

    public RicercaController(RicercaService ricercaService) {
        this.ricercaService = ricercaService;
    }

    @GetMapping("/ricerca")
    public String visualizza(Model model) {
        List<Avvistamento> avvistamenti = ricercaService.getTuttiGliAvvistamenti();
        List<Denuncia> denunce = ricercaService.getTutteLeDenunce();
        model.addAttribute("avvistamenti", avvistamenti);
        model.addAttribute("denunce", denunce);
        return "ricerca";
    }
}

