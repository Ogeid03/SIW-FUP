package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Avvistamento;
import it.uniroma3.siw.model.Utente;

public interface AvvistamentoRepository extends JpaRepository<Avvistamento, Long> {
  List<Avvistamento> findAll();

  List<Avvistamento> findByRazzaAndIdNot(String razza, Long id);

  List<Avvistamento> findByRazza(String razza);

  List<Avvistamento> findByCodUtente(Utente utente);

  List<Avvistamento> findBySpecieAndRazzaAndLuogoContainingIgnoreCase(String specie, String razza, String luogo);

  /* QUERY di ricerca per i caroselli */
  @Query(value = """
                  SELECT *
      FROM (
          SELECT a.*,
                 (6371 * acos(
                     cos(radians(:lat)) * cos(radians(a.latitudine)) *
                     cos(radians(a.longitudine) - radians(:lng)) +
                     sin(radians(:lat)) * sin(radians(a.latitudine))
                 )) AS distanza
          FROM avvistamento a
          WHERE a.specie = :specie
            AND a.razza = :razza
      ) AS sub
      WHERE distanza < :raggio
      ORDER BY distanza
                  """, nativeQuery = true)
  List<Avvistamento> findAvvistamentiBySpecieAndRazzaAndDistanza(
      @Param("specie") String specie,
      @Param("razza") String razza,
      @Param("lat") double lat,
      @Param("lng") double lng,
      @Param("raggio") double raggioKm);
}
