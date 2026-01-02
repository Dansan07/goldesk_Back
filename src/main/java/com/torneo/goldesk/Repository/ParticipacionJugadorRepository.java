package com.torneo.goldesk.Repository;

import com.torneo.goldesk.Entity.ParticipacionJugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipacionJugadorRepository extends JpaRepository<ParticipacionJugador,Integer> {

    Optional<ParticipacionJugador> findByPartidoIdPartidoAndTorneoEquipoJugadorIdTorneoEquipoJugador(Integer idPartido, Integer idTorneoEquipoJugador);

    // En ParticipacionJugadorRepository
    List<ParticipacionJugador> findByPartidoIdPartidoAndTorneoEquipoJugadorTorneoEquipoIdTorneoEquipo(Integer idPartido, Integer idTorneoEquipo);
}
