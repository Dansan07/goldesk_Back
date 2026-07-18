package com.torneo.goldesk.Service;

import com.torneo.goldesk.Entity.PagoInscripcion;
import com.torneo.goldesk.Entity.TorneoEquipo;
import com.torneo.goldesk.Repository.PagoInscripcionRepository;
import com.torneo.goldesk.Repository.TorneoEquipoRepository;
import com.torneo.goldesk.dto.registroPagos.inscripcion.AbonoDetalleInscripcionDTO;
import com.torneo.goldesk.dto.registroPagos.inscripcion.InscripcionTorneoResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class PagoInscripcionService {

    private final PagoInscripcionRepository pagoInscripcionRepository;
    private final TorneoEquipoRepository torneoEquipoRepository;

    public PagoInscripcionService(PagoInscripcionRepository pagoInscripcionRepository, TorneoEquipoRepository torneoEquipoRepository) {
        this.pagoInscripcionRepository = pagoInscripcionRepository;
        this.torneoEquipoRepository = torneoEquipoRepository;
    }

    public List<InscripcionTorneoResponse> obtenerListadoInscripcion(Integer idTorneo, String estadoPago){
        return pagoInscripcionRepository.obtenerInscripcioneTorneo(idTorneo, estadoPago)
                .stream()
                .map(InscripcionTorneoResponse::new)
                .toList();
    }

    @Transactional
    public void registrarPagoInscripcion(Map<String, Object> dto) {
        TorneoEquipo equipo = torneoEquipoRepository.findByIdTorneoEquipo((Integer) dto.get("idTorneoEquipo"))
                .orElseThrow(() -> new RuntimeException("No se encontró el equipo en el torneo"));

        PagoInscripcion pago = new PagoInscripcion();
        pago.setTorneoEquipo(equipo);
        pago.setMonto((Double) dto.get("monto"));

        pagoInscripcionRepository.save(pago);
    }

    @Transactional
    public List<AbonoDetalleInscripcionDTO> obtenerEstadoCuenta(Integer idTorneoEquipo){
        // 1. Obtener el equipo y el valor del torneo
        torneoEquipoRepository.findById(idTorneoEquipo)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado en el torneo"));

        return pagoInscripcionRepository.findByTorneoEquipoIdTorneoEquipo(idTorneoEquipo)
                .stream()
                .map(p -> new AbonoDetalleInscripcionDTO(
                        p.getIdPagoInscripcion(),
                        p.getMonto(),
                        p.getFechaPago()
                )).toList();
    }
}
