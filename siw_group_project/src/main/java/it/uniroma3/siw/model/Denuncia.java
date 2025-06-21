package it.uniroma3.siw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Denuncia extends Segnalazione {

    @SequenceGenerator(name = "segnalazione_seq", sequenceName = "segnalazione_seq", allocationSize = 1)
    
    private String nome;
    private Double premioOfferto;
    private String eta;

    // Getter e Setter
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPremioOfferto() {
        return premioOfferto;
    }

    public void setPremioOfferto(Double premioOfferto) {
        this.premioOfferto = premioOfferto;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }
}
