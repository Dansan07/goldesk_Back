package com.torneo.goldesk.Repository;

import com.torneo.goldesk.Entity.PagoArbitraje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PagoArbitrajeRepository extends JpaRepository<PagoArbitraje,Integer> {

    // Para validar en la Planilla Digital si un equipo ya pagó
    Optional<PagoArbitraje> findByPartidoIdPartidoAndTorneoEquipoIdTorneoEquipo(Integer idPartido, Integer idTorneoEquipo);

    // Para obtener el historial de pagos de un partido (local y visitante)
    List<PagoArbitraje> findByPartidoIdPartido(Integer idPartido);

    Optional<PagoArbitraje> findByIdPagoArbitraje(Integer idPagoArbiotraje);
}
