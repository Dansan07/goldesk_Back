package com.torneo.goldesk.Repository;

import com.torneo.goldesk.Entity.Gol;
import com.torneo.goldesk.dto.gol.GolResponseDTO;
import com.torneo.goldesk.dto.tablaGoleadores.TablaGoleadoresDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GolRepository extends JpaRepository<Gol,Integer> {

    Optional<Gol> findByIdGol(Integer idGol);

    // Busca los goles usando directamente la llave foránea de la tabla intermedia
    List<GolResponseDTO> findByParticipacionJugador_IdParticipacion(Integer idParticipacion);

    @Query("""
    SELECT new com.torneo.goldesk.dto.tablaGoleadores.TablaGoleadoresDTO(
        j.nombreJugador,
        te.nombrePersonalizado,
        COUNT(DISTINCT pj.idParticipacion),
        COUNT(g)
    )
    FROM ParticipacionJugador pj
    LEFT JOIN pj.torneoEquipoJugador tej
    LEFT JOIN tej.jugador j
    LEFT JOIN tej.torneoEquipo te
    LEFT JOIN te.torneo t
    LEFT JOIN Gol g
    ON g.participacionJugador = pj
    WHERE t.idTorneo = :idTorneo
    GROUP BY j.nombreJugador, te.nombrePersonalizado
    ORDER BY COUNT(g) DESC, COUNT(DISTINCT pj.idParticipacion) ASC
    """)
    List<TablaGoleadoresDTO> obtenerTablaGoleadores(@Param("idTorneo") Long idTorneo);
}
