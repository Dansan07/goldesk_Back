package com.torneo.goldesk.Service;

import com.torneo.goldesk.Entity.ParticipacionJugador;
import com.torneo.goldesk.Entity.Tarjeta;
import com.torneo.goldesk.Repository.ParticipacionJugadorRepository;
import com.torneo.goldesk.Repository.TarjetaRepository;
import com.torneo.goldesk.dto.tarjeta.TarjetaCreateDTO;
import com.torneo.goldesk.dto.tarjeta.TarjetaResponseDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarjetaService {

    private final TarjetaRepository tarjetaRepository;
    private final ParticipacionJugadorRepository participacionJugadorRepository;

    public TarjetaService(TarjetaRepository tarjetaRepository, ParticipacionJugadorRepository participacionJugadorRepository) {
        this.tarjetaRepository = tarjetaRepository;
        this.participacionJugadorRepository = participacionJugadorRepository;
    }


    // Metodo para listar las tarjetas de un jugador en un partido
    public List<TarjetaResponseDTO> obtenerTarjetasPorParticipacion(Integer idParticipacion) {
        return tarjetaRepository.findByParticipacionJugador_IdParticipacion(idParticipacion);
    }

    @Transactional
    public void registrarTarjeta(TarjetaCreateDTO dto) {
        // 1. Buscamos la participación única (Auditoría: vincula jugador, equipo y partido en un solo ID)
        ParticipacionJugador participacion = participacionJugadorRepository
                .findByPartidoIdPartidoAndJugadorIdTorneoEquipoJugador(dto.getIdPartido(), dto.getIdJugador())
                .orElseThrow(() -> new RuntimeException("El jugador no tiene una participación activa en este partido"));

        // 2. Crear objeto Tarjeta vinculado a la participación
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setParticipacionJugador(participacion);
        tarjeta.setTipoTarjeta(dto.getTipoTarjeta().toUpperCase());
        tarjeta.setValorTarjeta(dto.getValorTarjeta());
        tarjeta.setMotivoTarjeta(dto.getMotivoTarjeta());

        tarjetaRepository.save(tarjeta);
    }

    // Metodo para eliminar una tarjeta por su ID
    @Transactional
    public void eliminarTarjeta(Integer idTarjeta) {
        Tarjeta tarjeta = tarjetaRepository.findByIdTarjeta(idTarjeta)
                .orElseThrow(() -> new RuntimeException("La tarjeta no existe"));

        tarjetaRepository.delete(tarjeta);
    }
}
