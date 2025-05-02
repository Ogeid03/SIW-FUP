package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.UtenteRepository;

@Controller
public class UtenteController {
    @Autowired
    private UtenteRepository utenteRepository;

    @GetMapping("/utenti")
    public String mostraForm(Model model) {
        model.addAttribute("utente", new Utente());
        return "utenteForm";
    }

    @PostMapping("/salva")
    public String salvaUtente(@ModelAttribute Utente utente) {
        utenteRepository.save(utente);
        return "redirect:/";
    }
}
