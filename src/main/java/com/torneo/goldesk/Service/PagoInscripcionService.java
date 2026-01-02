package com.torneo.goldesk.Service;

import com.torneo.goldesk.Entity.PagoInscripcion;
import com.torneo.goldesk.Entity.TorneoEquipo;
import com.torneo.goldesk.Repository.PagoInscripcionRepository;
import com.torneo.goldesk.Repository.TorneoEquipoRepository;
import com.torneo.goldesk.dto.registroPagos.inscripcion.AbonoDetalleDTO;
import com.torneo.goldesk.dto.registroPagos.inscripcion.PagoInscripcionCreateDTO;
import com.torneo.goldesk.dto.registroPagos.inscripcion.EstadoCuentaInscripcionDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoInscripcionService {

    private final PagoInscripcionRepository pagoInscripcionRepository;
    private final TorneoEquipoRepository torneoEquipoRepository;

    public PagoInscripcionService(PagoInscripcionRepository pagoInscripcionRepository, TorneoEquipoRepository torneoEquipoRepository) {
        this.pagoInscripcionRepository = pagoInscripcionRepository;
        this.torneoEquipoRepository = torneoEquipoRepository;
    }

    @Transactional
    public void registrarPagoInscripcion(PagoInscripcionCreateDTO dto) {
        TorneoEquipo equipo = torneoEquipoRepository.findByIdTorneoEquipo(dto.getIdTorneoEquipo())
                .orElseThrow(() -> new RuntimeException("No se encontró el equipo en el torneo"));

        PagoInscripcion pago = new PagoInscripcion();
        pago.setTorneoEquipo(equipo);
        pago.setMonto(dto.getMonto());

        pagoInscripcionRepository.save(pago);
    }

    public EstadoCuentaInscripcionDTO obtenerEstadoCuenta(Integer idTorneoEquipo){
        // 1. Obtener el equipo y el valor del torneo
        TorneoEquipo equipo = torneoEquipoRepository.findById(idTorneoEquipo)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado en el torneo"));

        Double valorTotalTorneo = equipo.getTorneo().getValorInscripcion();

        List<PagoInscripcion> pagos = pagoInscripcionRepository.findByTorneoEquipoIdTorneoEquipo(idTorneoEquipo);

        List<AbonoDetalleDTO> historial = pagos.stream()
                .map(p -> new AbonoDetalleDTO(
                        p.getIdPagoInscripcion(),
                        p.getMonto(),
                        p.getFechaPago()
                )).toList();

        Double totalAbonado = historial.stream().mapToDouble(AbonoDetalleDTO::getMonto).sum();
        Double saldoPendiente = (valorTotalTorneo - totalAbonado);

        return new EstadoCuentaInscripcionDTO(
                valorTotalTorneo,
                totalAbonado,
                saldoPendiente,
                historial

        );
    }
}
