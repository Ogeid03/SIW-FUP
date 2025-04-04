package it.uniroma3.siw.model;

import java.util.Objects;
import jakarta.persistence.*;

@Entity
public class Stato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String tipologia;

    // Getter e Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    // Equals e hashCode basati su ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stato stato = (Stato) o;
        return Objects.equals(id, stato.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}