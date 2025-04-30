package it.uniroma3.siw.controller;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//import it.uniroma3.siw.service.SitoService;

@Controller
public class SitoController {

    //@Autowired
    //private SitoService sitoService;

    public SitoController() {
        System.out.println("✅ MovieController caricato!");
    }

    // Homepage con form
    @GetMapping("/")
    public String index(Model model) {
        //model.addAttribute("movie", new Movie()); // Per il form
        return "homepage"; // Thymeleaf cercherà "index.html"
    }

    // Homepage con form
    @GetMapping("/error")
    public String error(Model model) {
        //model.addAttribute("movie", new Movie()); // Per il form
        return "error"; // Thymeleaf cercherà "index.html"
    }

}
