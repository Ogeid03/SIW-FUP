package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.service.UtenteService;

@Controller
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

   @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Pagina di login
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, @RequestParam String password, Model model) {
        // Logica per il login dell'utente
        if (utenteService.authenticate(username, password)) {
            return "redirect:/";  // Dopo login, reindirizza alla home
        }
        model.addAttribute("error", "Credenziali non valide!");
        return "login"; // Torna alla pagina di login in caso di errore
    }

    @GetMapping("/home")
    public String home() {
        return "home"; // Pagina protetta a cui si accede solo dopo il login
    }
}

