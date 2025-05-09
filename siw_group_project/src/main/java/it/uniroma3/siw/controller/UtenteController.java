package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Utente;
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
    public String handleLogin(@RequestParam String email, @RequestParam String password, Model model) {
        // Logica per il login dell'utente
        if (utenteService.authenticate(email, password)) {
            return "redirect:/";  // Dopo login, reindirizza alla home
        }
        model.addAttribute("error", "Credenziali non valide!");
        return "login"; // Torna alla pagina di login in caso di errore
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
        return "redirect:/";
    }
}

