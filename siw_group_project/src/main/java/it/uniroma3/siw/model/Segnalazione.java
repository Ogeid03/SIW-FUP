package it.uniroma3.siw.model;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Segnalazione {
    @Id
    @SequenceGenerator(name = "segnalazione_seq", sequenceName = "segnalazione_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "segnalazione_seq")
    private Long id;
    
    private LocalDateTime dataOra;
    private String specie;
    private String razza;
    private String descrizioneFisica;
    private String foto;
    private String informazioniExtra;
    private String luogo;
        private Double latitudine;
        private Double longitudine;

    @Enumerated(EnumType.STRING)
    private Stato codStatus;
    @Enumerated(EnumType.STRING)
    private Sesso codSesso;
    
    @ManyToOne
    @JoinColumn(name = "codUtente")
    private Utente codUtente;

    public Segnalazione() {
        
    }
    // Getter e Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataOra() {
        return dataOra;
    }

    public void setDataOra(LocalDateTime dataOra) {
        this.dataOra = dataOra;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public String getRazza() {
        return razza;
    }

    public void setRazza(String razza) {
        this.razza = razza;
    }

    public String getDescrizioneFisica() {
        return descrizioneFisica;
    }

    public void setDescrizioneFisica(String descrizioneFisica) {
        this.descrizioneFisica = descrizioneFisica;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getInformazioniExtra() {
        return informazioniExtra;
    }

    public void setInformazioniExtra(String informazioniExtra) {
        this.informazioniExtra = informazioniExtra;
    }

    public String getCodLuogo() {
        return luogo;
    }

    public void setCodLuogo(String codLuogo) {
        this.luogo = codLuogo;
    }

    public Double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(Double latitudine) {
        this.latitudine = latitudine;
    }

    public Double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(Double longitudine) {
        this.longitudine = longitudine;
    }

    public Utente getCodUtente() {
        return codUtente;
    }

    public void setCodUtente(Utente codUtente) {
        this.codUtente = codUtente;
    }

    public Stato getCodStatus() {
        return codStatus;
    }

    public void setCodStatus(Stato codStatus) {
        this.codStatus = codStatus;
    }

    // Equals basato su ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Segnalazione that = (Segnalazione) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}