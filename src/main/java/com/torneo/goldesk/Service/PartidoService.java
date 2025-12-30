package com.torneo.goldesk.Service;

import com.torneo.goldesk.Entity.ParticipacionJugador;
import com.torneo.goldesk.Entity.Partido;
import com.torneo.goldesk.Entity.TorneoEquipo;
import com.torneo.goldesk.Entity.TorneoEquipoJugador;
import com.torneo.goldesk.Repository.ParticipacionJugadorRepository;
import com.torneo.goldesk.Repository.PartidoRepository;
import com.torneo.goldesk.Repository.TorneoEquipoJugadorRepository;
import com.torneo.goldesk.Repository.TorneoEquipoRepository;
import com.torneo.goldesk.dto.partido.PartidoCreateDTO;
import com.torneo.goldesk.dto.partido.PartidoResDuplicateDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartidoService {

    private final PartidoRepository partidoRepository;
    private final TorneoEquipoRepository torneoEquipoRepository;
    private final TorneoEquipoJugadorRepository torneoEquipoJugadorRepository;
    private final ParticipacionJugadorRepository participacionJugadorRepository;


    public PartidoService(PartidoRepository partidoRepository, TorneoEquipoRepository torneoEquipoRepository, TorneoEquipoJugadorRepository torneoEquipoJugadorRepository, ParticipacionJugadorRepository participacionJugadorRepository) {
        this.partidoRepository = partidoRepository;
        this.torneoEquipoRepository = torneoEquipoRepository;
        this.torneoEquipoJugadorRepository = torneoEquipoJugadorRepository;
        this.participacionJugadorRepository = participacionJugadorRepository;
    }

    @Transactional
    public void iniciarPartido(Integer idPartido) {
        // 1. Validar el partido
        Partido partido = partidoRepository.findById(idPartido)
                .orElseThrow(() -> new RuntimeException("Partido no encontrado"));

        // 2. Evitar duplicar participaciones si el partido ya inició
        if (!"PROGRAMADO".equals(partido.getEstado())) {
            throw new RuntimeException("El partido ya ha sido iniciado o finalizado.");
        }

        // 3. Obtener jugadores activos de ambos equipos (Local y Visitante)
        // Usamos el repositorio de TorneoEquipoJugador para filtrar por estado 'ACTIVO'
        List<TorneoEquipoJugador> jugadoresLocal = torneoEquipoJugadorRepository
                .findByTorneoEquipo_IdTorneoEquipoAndActivoTrue(partido.getLocal().getIdTorneoEquipo());

        List<TorneoEquipoJugador> jugadoresVisitante = torneoEquipoJugadorRepository
                .findByTorneoEquipo_IdTorneoEquipoAndActivoTrue(partido.getVisitante().getIdTorneoEquipo());

        // 4. Crear los registros de Participación (Snapshot de Auditoría)
        registrarParticipaciones(partido, jugadoresLocal);
        registrarParticipaciones(partido, jugadoresVisitante);

        // 5. Cambiar estado del partido
        partido.setEstado("EN_CURSO");
        partidoRepository.save(partido);
    }

    private void registrarParticipaciones(Partido partido, List<TorneoEquipoJugador> jugadores) {
        for (TorneoEquipoJugador tej : jugadores) {
            ParticipacionJugador participacion = new ParticipacionJugador();
            participacion.setPartido(partido);
            participacion.setJugador(tej); // El 'id_torneo_equipos_jugadores' de tu entidad
            participacionJugadorRepository.save(participacion);
        }
    }

    public PartidoResDuplicateDTO programarPartido(PartidoCreateDTO dto) {

        Optional<TorneoEquipo> localOpt= torneoEquipoRepository.findById(dto.getIdEquipoLocal());
        Optional<TorneoEquipo> visitantelOpt= torneoEquipoRepository.findById(dto.getIdEquipoVisitante());

        if (localOpt.isEmpty()){
            return new PartidoResDuplicateDTO("ERROR","Equipo local no encontrado");
        }
        if (visitantelOpt.isEmpty()){
            return new PartidoResDuplicateDTO("ERROR","Equipo visitante no encontrado");
        }

        TorneoEquipo local=localOpt.get();
        TorneoEquipo visitante=visitantelOpt.get();

        if (local.getIdTorneoEquipo().equals(visitante.getIdTorneoEquipo())) {
            return new PartidoResDuplicateDTO("ERROR", "Un equipo no puede jugar contra sí mismo");
        }

        // --- VALIDACIÓN DE ENFRENTAMIENTO PREVIO ---
        //enviar siempre false por defecto cuando se cree un partido
        boolean yaExiste = partidoRepository.existeEnfrentamiento(local, visitante, dto.getFase());
        if (yaExiste && !dto.isConfirmarDuplicado()) {
            return new PartidoResDuplicateDTO("WARNING_DUPLICATE",
    "Ya existe un partido entre estos equipos en la fase " + dto.getFase() + ". ¿Deseas duplicarlo?");
        }

        Partido nuevoPartido = new Partido();
        nuevoPartido.setLocal(local);
        nuevoPartido.setVisitante(visitante);
        nuevoPartido.setFechaPartido(dto.getFecha());
        nuevoPartido.setHoraPartido(dto.getHora());
        nuevoPartido.setNombreCancha(dto.getCancha());
        nuevoPartido.setFaseTorneo(dto.getFase());

        partidoRepository.save(nuevoPartido);
        return new PartidoResDuplicateDTO("SUCCESS", "Partido programado exitosamente.");
    }
}
