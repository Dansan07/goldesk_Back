package com.torneo.goldesk.Repository;

import com.torneo.goldesk.Entity.ParticipacionJugador;
import com.torneo.goldesk.Repository.Projection.JugadorPlanillaProyeccion;
import com.torneo.goldesk.dto.planillaDigital.JugadorPlanillaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipacionJugadorRepository extends JpaRepository<ParticipacionJugador,Integer> {

    Optional<ParticipacionJugador> findByPartidoIdPartidoAndTorneoEquipoJugadorIdTorneoEquipoJugador(Integer idPartido, Integer idTorneoEquipoJugador);

    // En ParticipacionJugadorRepository
    List<ParticipacionJugador> findByPartidoIdPartidoAndTorneoEquipoJugadorTorneoEquipoIdTorneoEquipo(Integer idPartido, Integer idTorneoEquipo);

    @Query(value = """
            SELECT
                    p.id_participacion AS idReferencia,
                    p.dorsal AS dorsal,
                    CONCAT(j.nombre_jugador, ' ', j.apellidos_jugador) AS nombreJugador,
                    (SELECT COUNT(*) FROM public.goles g WHERE g.id_participacion = p.id_participacion) AS cantGoles,
                    COUNT(CASE WHEN t.tipo_tarjeta = 'amarilla' THEN 1 END) AS cantAmarillas,
                    COUNT(CASE WHEN t.tipo_tarjeta = 'azul' THEN 1 END) AS cantAzules,
                    COUNT(CASE WHEN t.tipo_tarjeta = 'roja' THEN 1 END) AS cantRojas
                FROM public.participaciones_jugadores p
                JOIN public.torneo_equipos_jugadores tej ON p.id_torneo_equipos_jugadores = tej.id_torneo_equipos_jugadores
                JOIN public.jugadores j ON tej.id_jugador = j.id_jugador
                LEFT JOIN public.tarjetas t ON t.id_participacion = p.id_participacion
                WHERE p.id_partido = :idPartido
                  AND tej.id_torneo_equipos = :idTorneoEquipo
                GROUP BY p.id_participacion, p.dorsal, j.nombre_jugador, j.apellidos_jugador
                ORDER BY
                    CASE WHEN p.dorsal ~ '^[0-9]+$' THEN CAST(p.dorsal AS INTEGER) ELSE 999 END,
                    p.dorsal ASC
        """, nativeQuery = true)
    List<JugadorPlanillaProyeccion> findJugadoresPlanilla(Integer idPartido, Integer idTorneoEquipo);
}
