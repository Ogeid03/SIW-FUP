package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.*;

@Entity
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeUtente;
    private String password;
    private String email;
    private String telefono;
    
    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;

    // Getter e Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeUtente() {
        return nomeUtente;
    }

    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Equals method basato solo sul telefono
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utente utente = (Utente) o;
        return Objects.equals(telefono, utente.telefono); // Confronta solo il telefono
    }

    // HashCode method basato solo sul telefono
    @Override
    public int hashCode() {
        return Objects.hash(telefono); // Usa solo il telefono per generare il hashCode
    }
}
