package com.torneo.goldesk.Service;

import com.torneo.goldesk.Entity.ParticipacionJugador;
import com.torneo.goldesk.Entity.Tarjeta;
import com.torneo.goldesk.Repository.ParticipacionJugadorRepository;
import com.torneo.goldesk.Repository.TarjetaRepository;
import com.torneo.goldesk.Repository.TorneoRepository;
import com.torneo.goldesk.dto.tarjeta.TarjetaCreateDTO;
import com.torneo.goldesk.dto.tarjeta.TarjetaResponseDTO;
import com.torneo.goldesk.dto.registroPagos.tarjetas.TarjetaTorneoResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarjetaService {

    private final TarjetaRepository tarjetaRepository;
    private final ParticipacionJugadorRepository participacionJugadorRepository;
    private final TorneoRepository torneoRepository;

    public TarjetaService(TarjetaRepository tarjetaRepository, ParticipacionJugadorRepository participacionJugadorRepository, TorneoRepository torneoRepository) {
        this.tarjetaRepository = tarjetaRepository;
        this.participacionJugadorRepository = participacionJugadorRepository;
        this.torneoRepository = torneoRepository;
    }

    public List<TarjetaTorneoResponse> obtenerTarjetasTorneo(Integer idTorneo, String estadoPago){
        return tarjetaRepository.obtenerTarjetasTorneo(idTorneo,estadoPago)
                .stream()
                .map(TarjetaTorneoResponse::new)
                .toList();
    }

    //metodo para listar tarjetas de un torneo específico
    public List<TarjetaResponseDTO> obtenerHistorialTarjetas(Integer idTorneo){
        return tarjetaRepository.findByParticipacionJugador_TorneoEquipoJugador_TorneoEquipo_Torneo_IdTorneo(idTorneo)
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    // Metodo para listar las tarjetas de un jugador en un partido
    public List<TarjetaResponseDTO> obtenerTarjetasPorParticipacion(Integer idParticipacion, String tipoTarjeta) {
        return tarjetaRepository.findByParticipacionJugador_IdParticipacionAndTipoTarjeta(
                idParticipacion, tipoTarjeta)
                .stream()
                .map(this::convertirADTO
                ).toList();
    }

    @Transactional
    public void registrarTarjeta(TarjetaCreateDTO dto) {
        // 1. Buscamos la participación única (Auditoría: vincula jugador, equipo y partido en un solo ID)
        ParticipacionJugador participacion = participacionJugadorRepository
                .findById(dto.getIdParticipacion())
                .orElseThrow(() -> new RuntimeException("El jugador no tiene una participación activa en este partido"));

        // 2. Crear objeto Tarjeta vinculado a la participación
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setParticipacionJugador(participacion);
        tarjeta.setTipoTarjeta(dto.getTipoTarjeta().toLowerCase().trim());
        tarjeta.setValorTarjeta(dto.getValorTarjeta());
        tarjeta.setMotivoTarjeta(dto.getMotivoTarjeta());
        tarjeta.setPeriodoPartido(dto.getPeriodoPartido());
        tarjeta.setTiempoEvento(dto.getTiempoEvento());

        tarjetaRepository.save(tarjeta);
    }

    // Metodo para eliminar una tarjeta por su ID
    @Transactional
    public void eliminarTarjeta(Integer idTarjeta) {
        Tarjeta tarjeta = tarjetaRepository.findByIdTarjeta(idTarjeta)
                .orElseThrow(() -> new RuntimeException("La tarjeta no existe"));

        tarjetaRepository.delete(tarjeta);
    }

    public TarjetaResponseDTO convertirADTO(Tarjeta t){
        return new TarjetaResponseDTO(
                t.getIdTarjeta(),
                t.getTipoTarjeta(),
                t.getParticipacionJugador().getTorneoEquipoJugador().getJugador().getNombreJugador(),
                t.getValorTarjeta(),
                t.getMotivoTarjeta(),
                t.getPeriodoPartido(),
                t.getTiempoEvento()
        );
    }
}
