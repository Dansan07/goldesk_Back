package com.torneo.goldesk.Service;

import com.torneo.goldesk.Entity.PagoArbitraje;
import com.torneo.goldesk.Entity.Partido;
import com.torneo.goldesk.Entity.TorneoEquipo;
import com.torneo.goldesk.Repository.PagoArbitrajeRepository;
import com.torneo.goldesk.Repository.PartidoRepository;
import com.torneo.goldesk.Repository.TorneoEquipoRepository;
import com.torneo.goldesk.dto.registroPagos.arbitraje.PagoArbitrajeCreateDTO;
import com.torneo.goldesk.dto.registroPagos.arbitraje.PagoArbitrajeResponseDTO;
import com.torneo.goldesk.dto.registroPagos.arbitraje.PagoArbitrajeUpdateDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PagoArbitrajeService {

    public final PagoArbitrajeRepository pagoArbitrajeRepository;
    private final PartidoRepository partidoRepository;
    private final TorneoEquipoRepository torneoEquipoRepository;

    public PagoArbitrajeService(PagoArbitrajeRepository pagoArbitrajeRepository, PartidoRepository partidoRepository, TorneoEquipoRepository torneoEquipoRepository) {
        this.pagoArbitrajeRepository = pagoArbitrajeRepository;
        this.partidoRepository = partidoRepository;
        this.torneoEquipoRepository = torneoEquipoRepository;
    }

    @Transactional
    public void registrarPagoArbitraje(PagoArbitrajeCreateDTO dto){
        Partido partido = partidoRepository.findByIdPartido(dto.getIdPartido())
                .orElseThrow(()-> new RuntimeException("No se encontró partido"));

        TorneoEquipo equipo = torneoEquipoRepository.findByIdTorneoEquipo(dto.getIdTorneoEquipo())
                .orElseThrow(()-> new RuntimeException("No se encontró equipo"));

        //crea el registro si sale bien.
        PagoArbitraje pagoArbitraje=new PagoArbitraje();
        pagoArbitraje.setPartido(partido);
        pagoArbitraje.setTorneoEquipo(equipo);
        pagoArbitraje.setMonto(dto.getMonto());
        pagoArbitraje.setObservacion(dto.getObservacion());

        pagoArbitrajeRepository.save(pagoArbitraje);
    }

    @Transactional
    public void actualizarPagoArbitraje(PagoArbitrajeUpdateDTO dto){
        PagoArbitraje pagoArbitraje = pagoArbitrajeRepository.findByIdPagoArbitraje(dto.getIdPagoArbitraje())
                .orElseThrow(()->new RuntimeException("No se encontró Registro de pago para este partido"));

        pagoArbitraje.setMonto(dto.getMonto());
        pagoArbitraje.setObservacion(dto.getObservacion());

        pagoArbitrajeRepository.save(pagoArbitraje);
    }

    @Transactional
    public PagoArbitrajeResponseDTO mostrarPagoArbitraje(Integer idPagoArbitraje){
        PagoArbitraje pagoArbitraje= pagoArbitrajeRepository
                .findByIdPagoArbitraje(idPagoArbitraje)
                .orElseThrow(()->new RuntimeException("Pago No encontrado"));
        return new PagoArbitrajeResponseDTO(
                pagoArbitraje.getIdPagoArbitraje(),
                pagoArbitraje.getPartido().getIdPartido(),
                pagoArbitraje.getTorneoEquipo().getIdTorneoEquipo(),
                pagoArbitraje.getMonto(),
                pagoArbitraje.getFechaPago(),
                pagoArbitraje.getObservacion()
        );
    }
}
