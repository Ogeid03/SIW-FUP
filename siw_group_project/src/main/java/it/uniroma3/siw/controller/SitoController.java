package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SitoController {

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

    
    @GetMapping("/error")
    public String error() {
        return "error"; 
    }

}
