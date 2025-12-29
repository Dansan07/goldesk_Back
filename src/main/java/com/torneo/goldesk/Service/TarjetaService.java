package com.torneo.goldesk.Service;

import com.torneo.goldesk.Entity.Partido;
import com.torneo.goldesk.Entity.Tarjeta;
import com.torneo.goldesk.Entity.TorneoEquipoJugador;
import com.torneo.goldesk.Repository.PartidoRepository;
import com.torneo.goldesk.Repository.TarjetaRepository;
import com.torneo.goldesk.Repository.TorneoEquipoJugadorRepository;
import com.torneo.goldesk.dto.tarjeta.TarjetaCreateDTO;
import com.torneo.goldesk.dto.tarjeta.TarjetaResponseDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarjetaService {

    private final TarjetaRepository tarjetaRepository;
    private final PartidoRepository partidoRepository;
    private final TorneoEquipoJugadorRepository torneoEquipoJugadorRepository;

    public TarjetaService(TarjetaRepository tarjetaRepository, PartidoRepository partidoRepository, TorneoEquipoJugadorRepository torneoEquipoJugadorRepository) {
        this.tarjetaRepository = tarjetaRepository;
        this.partidoRepository = partidoRepository;
        this.torneoEquipoJugadorRepository = torneoEquipoJugadorRepository;
    }

    // Metodo para listar las tarjetas de un jugador en un partido
    public List<TarjetaResponseDTO> obtenerTarjetasJugadorPartido(Integer idPartido, Integer idTEJ) {
        return tarjetaRepository.buscarTarjetasJugadorEnPartido(idPartido, idTEJ);
    }

    @Transactional
    public void registrarTarjeta(TarjetaCreateDTO dto) {
        // 1. Buscar referencias
        Partido partido = partidoRepository.findByIdPartido(dto.getIdPartido())
                .orElseThrow(()-> new RuntimeException("No se encontró el Partido"));
        TorneoEquipoJugador jugador = torneoEquipoJugadorRepository.findByIdTorneoEquipoJugador(dto.getIdJugador())
                .orElseThrow(()-> new RuntimeException("No se encontró el Jugador"));

        // 2. Crear objeto
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setPartido(partido);
        tarjeta.setJugador(jugador);
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
