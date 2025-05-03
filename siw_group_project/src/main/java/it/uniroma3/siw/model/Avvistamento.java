package it.uniroma3.siw.model;

import jakarta.persistence.Entity;

@Entity
public class Avvistamento extends Segnalazione{
    
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
