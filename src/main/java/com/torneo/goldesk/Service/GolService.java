package com.torneo.goldesk.Service;

import com.torneo.goldesk.Entity.Gol;
import com.torneo.goldesk.Entity.ParticipacionJugador;
import com.torneo.goldesk.Entity.Partido;
import com.torneo.goldesk.Exception.GlobalExceptionHandler;
import com.torneo.goldesk.Exception.ResourceNotFoundException;
import com.torneo.goldesk.Repository.GolRepository;
import com.torneo.goldesk.Repository.ParticipacionJugadorRepository;
import com.torneo.goldesk.Repository.PartidoRepository;
import com.torneo.goldesk.dto.gol.GolCreateDTO;
import com.torneo.goldesk.dto.gol.GolResponseDTO;
import com.torneo.goldesk.dto.tablaGoleadores.TablaGoleadoresDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GolService {

    private final GolRepository golRepository;
    private final PartidoRepository partidoRepository;
    private final ParticipacionJugadorRepository participacionJugadorRepository;

    public GolService(GolRepository golRepository, PartidoRepository partidoRepository, ParticipacionJugadorRepository participacionJugadorRepository) {
        this.golRepository = golRepository;
        this.partidoRepository = partidoRepository;
        this.participacionJugadorRepository = participacionJugadorRepository;
    }


    @Transactional
    public void registrarGol(GolCreateDTO dto) {
        // 1. Buscamos la participación (que ya contiene al Partido y al Jugador)
        ParticipacionJugador participacion = participacionJugadorRepository
                .findById(dto.getIdParticipacion())
                .orElseThrow(() -> new ResourceNotFoundException("Participación No registrada"));

        // 2. Crear el registro del gol
        Gol nuevoGol = new Gol();
        nuevoGol.setParticipacionJugador(participacion);
        nuevoGol.setPeriodoPartido(dto.getPeriodoPartido());
        nuevoGol.setTiempoEvento(dto.getTiempoEvento());
        golRepository.save(nuevoGol);

        // 3. ACTUALIZAR EL MARCADOR (Usando la data de la participacion)
        Partido partido = participacion.getPartido();
        Integer idEquipoAutor = participacion.getTorneoEquipoJugador().getTorneoEquipo().getIdTorneoEquipo();

        // Simplificamos la comparación
        if (idEquipoAutor.equals(partido.getLocal().getIdTorneoEquipo())) {
            partido.setGolesLocal(partido.getGolesLocal() + 1);
        } else {
            partido.setGolesVisitante(partido.getGolesVisitante() + 1);
        }
        partidoRepository.save(partido);
    }

    @Transactional
    public void eliminarGol(Integer idGol){
        // 1. Buscar el gol que se desea eliminar
        Gol gol = golRepository.findByIdGol(idGol)
                .orElseThrow(() -> new RuntimeException("El registro del gol no existe."));

        ParticipacionJugador participacion = gol.getParticipacionJugador();
        Partido partido = participacion.getPartido();

        // 2. REVERTIR EL MARCADOR DEL PARTIDO
        Integer idEquipoAutor = participacion.getTorneoEquipoJugador().getTorneoEquipo().getIdTorneoEquipo();
        Integer idLocal = partido.getLocal().getIdTorneoEquipo();
        Integer idVisitante = partido.getVisitante().getIdTorneoEquipo();

        if (idEquipoAutor.equals(idLocal)) {
            // Validamos que el marcador no sea ya 0 por algún error previo
            if (partido.getGolesLocal() > 0) {
                partido.setGolesLocal(partido.getGolesLocal() - 1);
            }
        } else if (idEquipoAutor.equals(idVisitante)) {
            if (partido.getGolesVisitante() > 0) {
                partido.setGolesVisitante(partido.getGolesVisitante() - 1);
            }
        }

        // 3. Guardar los cambios en el partido y eliminar el registro del gol
        partidoRepository.save(partido);
        golRepository.delete(gol);
    }

    public List<GolResponseDTO> listarGolesPorParticipacion(Integer idParticipacion) {
        // Optimización: Consulta directa por el ID de la tabla de hechos
        return golRepository.findByParticipacionJugador_IdParticipacion(idParticipacion);
    }

    public List<TablaGoleadoresDTO> obtenerTablaGoleadoresPorTorneo (Long idTorneo){
        if (golRepository.obtenerTablaGoleadores(idTorneo).isEmpty()){
            throw new RuntimeException("Aún no hay registro de goles");
        }
        return golRepository.obtenerTablaGoleadores(idTorneo);
    }
}
