package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.UtenteRepository;

@Controller
@ControllerAdvice
public class SitoController {

    @Autowired
    private UtenteRepository utenteRepository;

    @ModelAttribute("nomeUtente")
    public String addNomeUtenteToModel(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        if (user == null) return null;
        Utente utente = utenteRepository.findByEmail(user.getUsername());
        return utente != null ? utente.getNomeUtente() : null;
    }

    public SitoController() {
        System.out.println("✅ MovieController caricato!");
    }

    @GetMapping("/")
    public String index() {
        return "homepage"; 
    }

   
    @GetMapping("/about")
    public String about() {
        return "about"; 
    }

    @GetMapping("/segnala")
    public String mostraForm() {
        return "segnala";
    }
    
    @GetMapping("/error")
    public String error() {
        return "error"; 
    }

}
