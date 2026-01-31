package com.torneo.goldesk.Repository;

import com.torneo.goldesk.Entity.Jugador;
import com.torneo.goldesk.Entity.TorneoEquipo;
import com.torneo.goldesk.Entity.TorneoEquipoJugador;
import com.torneo.goldesk.dto.actores.jugador.EstadisticasJugadorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TorneoEquipoJugadorRepository extends JpaRepository<TorneoEquipoJugador,Integer> {

    Optional<TorneoEquipoJugador> findByJugador_IdJugadorAndActivoTrue(Integer idJugador);

    // Consulta para validar que el jugador no esté en el mismo torneo con otro equipo
    @Query("SELECT COUNT(tej) > 0 FROM TorneoEquipoJugador tej " +
            "WHERE tej.jugador.cedulaJug = :cedula " +
            "AND tej.torneoEquipo.torneo.idTorneo = :idTorneo")
    boolean existsByJugadorAndTorneo(@Param("cedula") String cedula, @Param("idTorneo") Integer idTorneo);

    @Query("SELECT new com.torneo.goldesk.dto.actores.jugador.EstadisticasJugadorDTO(" +
            "tej.idTorneoEquipoJugador, " +
            "CONCAT(j.nombreJugador, ' ', j.apellidosJugador), " +
            "COUNT(DISTINCT p.idParticipacion), " +
            "COUNT(DISTINCT g.idGol), " +
            "SUM(CASE WHEN t.tipoTarjeta = 'amarilla' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN t.tipoTarjeta = 'azul' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN t.tipoTarjeta = 'roja' THEN 1 ELSE 0 END)) " +
            "FROM TorneoEquipoJugador tej " +
            "JOIN tej.jugador j " +
            "LEFT JOIN ParticipacionJugador p ON p.torneoEquipoJugador = tej " +
            "LEFT JOIN Gol g ON g.participacionJugador = p " +
            "LEFT JOIN Tarjeta t ON t.participacionJugador = p " +
            "WHERE tej.torneoEquipo.idTorneoEquipo = :idTorneoEquipo " +
            "AND tej.activo = true " +
            "GROUP BY tej.idTorneoEquipoJugador, j.nombreJugador, j.apellidosJugador")
    List<EstadisticasJugadorDTO> obtenerEstadisticasPorEquipo(@Param("idTorneoEquipo") Integer idTorneoEquipo);

    // Para buscar la inscripción específica que queremos borrar o recuperar
    Optional<TorneoEquipoJugador> findByIdTorneoEquipoJugador(Integer idTorneoEquipoJugador);

    // Opcional: Para el listado de la nómina, poder ver solo los activos
    List<TorneoEquipoJugador> findByTorneoEquipo_IdTorneoEquipoAndActivoTrue(Integer idTorneoEquipo);

    List<TorneoEquipoJugador> findByTorneoEquipo_IdTorneoEquipo(Integer idTorneoEquipo);

    Optional<TorneoEquipoJugador> findActiveInscripcionByJugadorIdJugador(Integer idJugador);

    Optional<TorneoEquipoJugador> findByJugadorAndTorneoEquipo(Jugador jugador, TorneoEquipo torneoEquipoActual);

    boolean existsByJugadorAndTorneoEquipoAndActivoTrue(Jugador jugador, TorneoEquipo torneoEquipoSolicita);

    //trae una lista de jugadores activos para un partido específico
    List<TorneoEquipoJugador> findByTorneoEquipoIdTorneoEquipoAndActivo(Integer idTorneoEquipo, Boolean activo);
}
