package it.uniroma3.siw.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Avvistamento extends Segnalazione{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "segnalazione_seq")
    @SequenceGenerator(name = "segnalazione_seq", sequenceName = "segnalazione_seq", allocationSize = 1)
    private Long id;
    
    private String statoSalute;
    private String azioniIntraprese;

    // Getter e Setter
    public String getStatoSalute() {
        return statoSalute;
    }

    public void setStatoSalute(String statoSalute) {
        this.statoSalute = statoSalute;
    }

    public String getAzioniIntraprese() {
        return azioniIntraprese;
    }

    public void setAzioniIntraprese(String azioniIntraprese) {
        this.azioniIntraprese = azioniIntraprese;
    }
}
