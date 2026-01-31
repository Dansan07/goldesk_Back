package com.torneo.goldesk.Repository;

import com.torneo.goldesk.Entity.Jugador;
import com.torneo.goldesk.Entity.TorneoEquipoJugador;
import com.torneo.goldesk.dto.actores.jugador.JugadorResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JugadorRepositoty extends JpaRepository<Jugador, Integer> {

    Optional<Jugador> findByCedulaJug(String cedula);
}
