package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.model.Avvistamento;
import it.uniroma3.siw.model.Denuncia;
import it.uniroma3.siw.repository.DenunciaRepository;
import it.uniroma3.siw.repository.SegnalazioneRepository;

@Controller
public class RicercaController {

    @Autowired
    private SegnalazioneRepository segnalazioneRepository;
    @Autowired
    private DenunciaRepository denunciaRepository;

    @GetMapping("/ricerca")
    public String visualizza(Model model) {
        List<Avvistamento> avvistamenti = segnalazioneRepository.findAll();
        List<Denuncia> denunce = denunciaRepository.findAll();
        model.addAttribute("avvistamenti", avvistamenti);
        model.addAttribute("denunce", denunce);
        return "ricerca";
    }
}
