package com.torneo.goldesk.Repository;

import com.torneo.goldesk.Entity.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipoRepository extends JpaRepository<Equipo, Integer> {

    List<Equipo> findByActivoTrue();
    List<Equipo> findByActivoFalse();

}
