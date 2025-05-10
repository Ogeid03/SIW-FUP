package it.uniroma3.siw.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import it.uniroma3.siw.model.Denuncia;
import it.uniroma3.siw.model.Stato;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.DenunciaService;
import it.uniroma3.siw.service.UtenteService;

@Controller
public class DenunciaController {

    private final DenunciaService denunciaService;
    private final UtenteService utenteService;

    public DenunciaController(DenunciaService denunciaService, UtenteService utenteService) {
        this.denunciaService = denunciaService;
        this.utenteService = utenteService;
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
        Utente utente = utenteService.getUtenteAutenticato();
        if (utente == null) {
            return "redirect:/login";
        }

        denuncia.setCodUtente(utente);
        denuncia.setDataOra(LocalDateTime.now());
        denuncia.setCodStatus(Stato.ATTIVO);
        denunciaService.salvaDenuncia(denuncia);
        return "redirect:/";
    }
}
