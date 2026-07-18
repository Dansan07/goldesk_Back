package com.torneo.goldesk.Repository;

import com.torneo.goldesk.Entity.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Integer> {

    Optional<Jugador> findByCedulaJug(String cedula);
}
