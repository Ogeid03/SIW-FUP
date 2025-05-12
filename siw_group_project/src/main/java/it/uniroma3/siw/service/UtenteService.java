package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Ruolo;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.UtenteRepository;

@Service
public class UtenteService implements UserDetailsService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Metodo di login che verifica il nome utente e la password
    public boolean authenticate(String email, String password) {
        Utente utente = utenteRepository.findByEmail(email);
        if (utente == null) {
            return false;
        }
        // Confronta la password con quella cifrata nel database
        return passwordEncoder.matches(password, utente.getPassword());
    }

    // Metodo per registrare un nuovo utente
    public void register(Utente utente, String password) {
        utente.setPassword(passwordEncoder.encode(password));
        utente.setRuolo(Ruolo.USER); // Imposta il ruolo di default
        utenteRepository.save(utente);
    }

    public Utente getUtenteByEmail(String email) {
        return utenteRepository.findByEmail(email);
    }

    public Utente getUtenteById(Long id) {
        return utenteRepository.findById(id).orElse(null);
    }
    
    public Utente getUtenteAutenticato() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated())
            return null;

        String email = authentication.getName();
        return utenteRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utente utente = utenteRepository.findByEmail(email);
        if (utente == null) {
            throw new UsernameNotFoundException("Utente non trovato con email: " + email);
        }

        return new User(
                utente.getEmail(),
                utente.getPassword(),
                List.of(new SimpleGrantedAuthority(utente.getRuolo().name())));
    }
}
