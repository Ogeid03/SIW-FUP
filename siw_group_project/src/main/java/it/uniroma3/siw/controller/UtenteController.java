package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.UtenteService;

@Controller
public class UtenteController {

    @Autowired
    private final UtenteService utenteService;

    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @GetMapping("/utenti")
    public String mostraForm(Model model) {
        model.addAttribute("utente", new Utente());
        return "utenteForm";
    }

    @PostMapping("/salva")
    public String salvaUtente(@ModelAttribute Utente utente) {
        utenteService.salvaUtente(utente);
        return "redirect:/";
    }
}
