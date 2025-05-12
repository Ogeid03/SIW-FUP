package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Denuncia;
import it.uniroma3.siw.model.Utente;

public interface DenunciaRepository extends JpaRepository<Denuncia, Long> {
    List<Denuncia> findAll();

    List<Denuncia> findByRazzaAndIdNot(String razza, Long id);

    List<Denuncia> findByRazza(String razza);

    List<Denuncia> findByCodUtente(Utente utente);

    List<Denuncia> findBySpecieAndRazzaAndLuogoContainingIgnoreCase(String specie, String razza, String luogo);

    /* QUERY di ricerca per i caroselli */
    @Query(value = """
            SELECT *
            FROM(
                    SELECT d.*,
                           (6371 * acos(
                               cos(radians(:lat)) * cos(radians(d.latitudine)) *
                               cos(radians(d.longitudine) - radians(:lng)) +
                               sin(radians(:lat)) * sin(radians(d.latitudine))
                           )) AS distanza
                    FROM denuncia d
                    WHERE d.specie = :specie
                      AND d.razza = :razza
                      ) AS sub
                    WHERE distanza < :raggio
                    ORDER BY distanza
                    """, nativeQuery = true)
    List<Denuncia> findDenunceBySpecieAndRazzaAndDistanza(
            @Param("specie") String specie,
            @Param("razza") String razza,
            @Param("lat") double lat,
            @Param("lng") double lng,
            @Param("raggio") double raggioKm);

}
