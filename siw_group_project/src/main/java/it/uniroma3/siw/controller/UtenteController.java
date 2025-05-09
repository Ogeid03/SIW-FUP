package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Avvistamento;
import it.uniroma3.siw.model.Denuncia;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.RicercaService;
import it.uniroma3.siw.service.UtenteService;

@Controller
public class UtenteController {

    @Autowired
    private UtenteService utenteService;
    @Autowired
    private RicercaService ricercaService;

   @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Pagina di login
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("utente", new Utente());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute Utente utente,
                               @RequestParam("password") String password) {
        utenteService.register(utente, password);
        return "login";
    }

    @GetMapping("/account")
    public String getSegnalazioniUtente(Model model) {
        Utente utente = utenteService.getUtenteAutenticato();

        if (utente == null) {
            return "redirect:/error"; // o una pagina di errore
        }

        List<Denuncia> denunce = ricercaService.getDenByUtente(utente);
        List<Avvistamento> avvistamenti = ricercaService.getAvvByUtente(utente);

        model.addAttribute("utente", utente);
        model.addAttribute("denunce", denunce);
        model.addAttribute("avvistamenti", avvistamenti);

        return "myAccount"; // Thymeleaf HTML
    }

}

