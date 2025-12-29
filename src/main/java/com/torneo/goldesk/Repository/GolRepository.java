package com.torneo.goldesk.Repository;

import com.torneo.goldesk.Entity.Gol;
import com.torneo.goldesk.dto.gol.GolResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GolRepository extends JpaRepository<Gol,Integer> {

    Optional<Gol> findByIdGol(Integer idGol);

    // Usamos el constructor del DTO en la consulta (importante poner la ruta completa del paquete)
    @Query("SELECT new com.torneo.goldesk.dto.gol.GolResponseDTO(g.idGol, g.jugador.jugador.nombreJugador) " +
            "FROM Gol g WHERE g.partido.idPartido = :idPartido " +
            "AND g.jugador.idTorneoEquipoJugador = :idTorneoEquipoJugador")
    List<GolResponseDTO> findByPartidoIdPartidoAndJugadorIdTorneoEquipoJugador(
            @Param("idPartido") Integer idPartido,
            @Param("idTorneoEquipoJugador") Integer idTorneoEquipoJugador);
}
