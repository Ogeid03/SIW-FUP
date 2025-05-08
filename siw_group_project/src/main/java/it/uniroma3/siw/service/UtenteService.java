package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.UtenteRepository;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Metodo di login che verifica il nome utente e la password
    public boolean authenticate(String nomeUtente, String password) {
        Utente utente = utenteRepository.findByNomeUtente(nomeUtente);
        if (utente == null) {
            return false;
        }
        // Confronta la password con quella cifrata nel database
        return passwordEncoder.matches(password, utente.getPassword());
    }

    // Metodo per registrare un nuovo utente
    public void register(Utente utente, String password) {
        utente.setPassword(passwordEncoder.encode(password));
        utenteRepository.save(utente);
    }
}
