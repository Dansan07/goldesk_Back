package com.torneo.goldesk.Repository;

import com.torneo.goldesk.Entity.PagoInscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoInscripcionRepository extends JpaRepository<PagoInscripcion,Integer> {

    // Busca si un equipo específico tiene un registro de pago de inscripción
    List<PagoInscripcion> findByTorneoEquipoIdTorneoEquipo(Integer idTorneoEquipo);
}
