package it.uniroma3.siw.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    // Metodo per la configurazione della sicurezza HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/login", "/register", "/img/**", "/css/**", "/js/**", "/segnalazioni/{id:[0-9]+}",
                            "/ricerca", "/error")
                        .permitAll() // Consenti l'accesso a login e register senza autenticazione
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login") // Imposta la pagina di login
                        .permitAll())
                .logout(logout -> logout
                        .permitAll()); // Consenti il logout senza autenticazione

        return http.build();
    }

    // Bean per il PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Usa BCrypt per codificare le password
    }
}
