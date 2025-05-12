package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Denuncia;
import it.uniroma3.siw.model.Segnalazione;

public interface SegnalazioneRepository extends JpaRepository<Segnalazione, Long> {

    Optional<Segnalazione> findById(Long id);

    @Query(value = """
            SELECT d.*,
                   (6371 * acos(
                       cos(radians(:lat)) * cos(radians(d.latitudine)) *
                       cos(radians(d.longitudine) - radians(:lng)) +
                       sin(radians(:lat)) * sin(radians(d.latitudine))
                   )) AS distanza
            FROM denuncia d
            HAVING distanza < :raggio
            ORDER BY distanza
            """, nativeQuery = true)
    List<Denuncia> findByLocationWithinRadius(@Param("lat") double lat,
            @Param("lng") double lng,
            @Param("raggio") double raggioKm);

}
