package it.uniroma3.siw.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;


import it.uniroma3.siw.model.Denuncia;
import it.uniroma3.siw.repository.DenunciaRepository;


@Controller
public class DenunciaController {

    @Autowired
    private DenunciaRepository denunciaRepository;

    // Mostra il modulo per inserire i dati
    @GetMapping("/denunce")
    public String mostraForm(Model model) {
        model.addAttribute("denuncia", new Denuncia());
        return "denunciaForm"; // nome del form
    }

    // Mostra la pagina di recap per rivedere i dati
    @PostMapping("/conferma-denuncia")
    public String conferma(@ModelAttribute Denuncia denuncia, Model model) {
        // Passiamo l'oggetto denuncia alla vista di recap
        model.addAttribute("denuncia", denuncia);
        return "recap-denuncia"; // nome della view di recap
    }

    // Conferma e salva la denuncia nel database
    @PostMapping("/salva-denuncia")
    public String salvaDenuncia(@ModelAttribute Denuncia denuncia) {
        denunciaRepository.save(denuncia); // Salva i dati nel database
        return "redirect:/"; // Torna alla homepage o un'altra pagina di conferma
    }

}


