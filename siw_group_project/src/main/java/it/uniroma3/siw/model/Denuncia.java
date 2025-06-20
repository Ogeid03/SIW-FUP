package it.uniroma3.siw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Denuncia extends Segnalazione {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "segnalazione_seq")
    @SequenceGenerator(name = "segnalazione_seq", sequenceName = "segnalazione_seq", allocationSize = 1)
    private Long id;

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
