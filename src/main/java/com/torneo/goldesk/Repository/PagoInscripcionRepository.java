package com.torneo.goldesk.Repository;

import com.torneo.goldesk.Entity.PagoInscripcion;
import com.torneo.goldesk.dto.registroPagos.inscripcion.InscripcionTorneoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoInscripcionRepository extends JpaRepository<PagoInscripcion,Integer> {

    // Busca si un equipo específico tiene un registro de pago de inscripción
    List<PagoInscripcion> findByTorneoEquipoIdTorneoEquipo(Integer idTorneoEquipo);

    @Query(value = "select * from obtener_inscripciones_torneo(:idTorneo, :estadoPago)", nativeQuery = true)
    List<InscripcionTorneoResponse.Vista> obtenerInscripcioneTorneo(@Param("idTorneo") Integer idTorneo,
                                                              @Param("estadoPago") String estadoPago);

}
