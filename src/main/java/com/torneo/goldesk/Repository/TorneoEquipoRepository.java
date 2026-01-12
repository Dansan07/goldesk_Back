package com.torneo.goldesk.Repository;

import com.torneo.goldesk.Entity.TorneoEquipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TorneoEquipoRepository extends JpaRepository<TorneoEquipo,Integer> {

    Optional<TorneoEquipo> findByTorneo_IdTorneoAndEquipo_IdEquipo(Integer idTorneo, Integer idEquipo);
    List<TorneoEquipo> findByTorneo_IdTorneo(Integer idTorneo);
    Optional<TorneoEquipo> findByIdTorneoEquipo(Integer idTorneoEquipo);

    //torneos con sus equipos
    List<TorneoEquipo> findByTorneo_IdTorneoAndEquipoActivoTrue(Integer idTorneo);

}
