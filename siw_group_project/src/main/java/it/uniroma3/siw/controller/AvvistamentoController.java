package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.model.Avvistamento;
import it.uniroma3.siw.repository.AvvistamentoRepository;

@Controller
public class AvvistamentoController {

    @Autowired
    private AvvistamentoRepository repository;

    @GetMapping("/avvistamento/form")
    public String mostraForm(Model model) {
        model.addAttribute("avvistamento", new Avvistamento());
        return "avvistamento-form";
    }

    @PostMapping("/avvistamento/submit")
    public String salvaAvvistamento(@ModelAttribute Avvistamento avvistamento) {
        repository.save(avvistamento);
        return "redirect:/avvistamento/form";
    }
}