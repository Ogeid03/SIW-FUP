package it.uniroma3.siw.model;

import jakarta.persistence.Entity;

@Entity
public class Denuncia extends Segnalazione {
    private String nome;
    private Double premioOfferto;
    private Integer eta;

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

    public Integer getEta() {
        return eta;
    }

    public void setEta(Integer eta) {
        this.eta = eta;
    }
}
