package it.uniroma3.siw.model;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
public class Messaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String contenuto;
    private LocalDateTime dataOra;
    
    @ManyToOne
    @JoinColumn(name = "codUtente")
    private Utente codUtente;

    @ManyToOne
    @JoinColumn(name = "codDestinatario")
    private Utente codDestinatario;

    @ManyToOne
    @JoinColumn(name = "codSegnalazione")
    private Segnalazione codSegnalazione;

    // Getter e Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenuto() {
        return contenuto;
    }

    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }

    public LocalDateTime getDataOra() {
        return dataOra;
    }

    public void setDataOra(LocalDateTime dataOra) {
        this.dataOra = dataOra;
    }

    public Utente getCodUtente() {
        return codUtente;
    }

    public void setCodUtente(Utente codUtente) {
        this.codUtente = codUtente;
    }

    public Utente getCodDestinatario() {
        return codDestinatario;
    }
    
    public void setCodDestinatario(Utente codDestinatario) {
        this.codDestinatario = codDestinatario;
    }

    public Segnalazione getCodSegnalazione() {
        return codSegnalazione;
    }

    public void setCodSegnalazione(Segnalazione codSegnalazione) {
        this.codSegnalazione = codSegnalazione;
    }

    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Messaggio that = (Messaggio) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}