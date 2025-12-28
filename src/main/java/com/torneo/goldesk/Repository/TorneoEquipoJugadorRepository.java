package com.torneo.goldesk.Repository;

import com.torneo.goldesk.Entity.Jugador;
import com.torneo.goldesk.Entity.TorneoEquipo;
import com.torneo.goldesk.Entity.TorneoEquipoJugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TorneoEquipoJugadorRepository extends JpaRepository<TorneoEquipoJugador,Integer> {

    // Consulta para validar que el jugador no esté en el mismo torneo con otro equipo
    @Query("SELECT COUNT(tej) > 0 FROM TorneoEquipoJugador tej " +
            "WHERE tej.jugador.cedulaJug = :cedula " +
            "AND tej.torneoEquipo.torneo.idTorneo = :idTorneo")
    boolean existsByJugadorAndTorneo(@Param("cedula") String cedula, @Param("idTorneo") Integer idTorneo);

    // Para buscar la inscripción específica que queremos borrar o recuperar
    Optional<TorneoEquipoJugador> findByIdTorneoEquipoJugador(Integer id);

    // Opcional: Para el listado de la nómina, poder ver solo los activos
    List<TorneoEquipoJugador> findByTorneoEquipo_IdTorneoEquipoAndActivoTrue(Integer idTorneoEquipo);

    List<TorneoEquipoJugador> findByTorneoEquipo_IdTorneoEquipo(Integer idTorneoEquipo);

    Optional<TorneoEquipoJugador> findActiveInscripcionByJugadorIdJugador(Integer idJugador);

    Optional<TorneoEquipoJugador> findByJugadorAndTorneoEquipo(Jugador jugador, TorneoEquipo torneoEquipoActual);

    boolean existsByJugadorAndTorneoEquipoAndActivoTrue(Jugador jugador, TorneoEquipo torneoEquipoSolicita);
}
