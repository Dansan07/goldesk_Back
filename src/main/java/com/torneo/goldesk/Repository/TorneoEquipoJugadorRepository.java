package com.torneo.goldesk.Repository;

import com.torneo.goldesk.Entity.TorneoEquipoJugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TorneoEquipoJugadorRepository extends JpaRepository<TorneoEquipoJugador,Integer> {

    // Consulta para validar que el jugador no esté en el mismo torneo con otro equipo
    @Query("SELECT COUNT(tej) > 0 FROM TorneoEquipoJugador tej " +
            "WHERE tej.jugador.cedulaJug = :cedula " +
            "AND tej.torneoEquipo.torneo.idTorneo = :idTorneo")
    boolean existsByJugadorAndTorneo(@Param("cedula") String cedula, @Param("idTorneo") Integer idTorneo);

}
