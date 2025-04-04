package it.uniroma3.siw_group_project.model;

import java.util.Objects;
import jakarta.persistence.*;

@Entity
public class Luogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String indicazioni;

    // Getter e Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndicazioni() {
        return indicazioni;
    }

    public void setIndicazioni(String indicazioni) {
        this.indicazioni = indicazioni;
    }

    // Equals e hashCode basati su ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Luogo luogo = (Luogo) o;
        return Objects.equals(id, luogo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
