package com.torneo.goldesk.Service;

import com.torneo.goldesk.Entity.*;
import com.torneo.goldesk.Exception.ResourceNotFoundException;
import com.torneo.goldesk.Repository.*;
import com.torneo.goldesk.dto.gol.GolResponseDTO;
import com.torneo.goldesk.dto.partido.FiltroHistorialPartidos;
import com.torneo.goldesk.dto.partido.PartidoCreateDTO;
import com.torneo.goldesk.dto.partido.PartidoHistorialResponseDTO;
import com.torneo.goldesk.dto.partido.PartidoResDuplicateDTO;
import com.torneo.goldesk.dto.planillaDigital.JugadorPlanillaDTO;
import com.torneo.goldesk.dto.planillaDigital.PlanillaDigitalDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PartidoService {

    private final PartidoRepository partidoRepository;
    private final TorneoEquipoRepository torneoEquipoRepository;
    private final TorneoEquipoJugadorRepository torneoEquipoJugadorRepository;
    private final ParticipacionJugadorRepository participacionJugadorRepository;
    private final PagoArbitrajeRepository pagoArbitrajeRepository;
    private final TorneoRepository torneoRepository;
    private final GolRepository golRepository;


    public PartidoService(PartidoRepository partidoRepository, TorneoEquipoRepository torneoEquipoRepository, TorneoEquipoJugadorRepository torneoEquipoJugadorRepository, ParticipacionJugadorRepository participacionJugadorRepository, PagoArbitrajeRepository pagoArbitrajeRepository, TorneoRepository torneoRepository, GolRepository golRepository) {
        this.partidoRepository = partidoRepository;
        this.torneoEquipoRepository = torneoEquipoRepository;
        this.torneoEquipoJugadorRepository = torneoEquipoJugadorRepository;
        this.participacionJugadorRepository = participacionJugadorRepository;
        this.pagoArbitrajeRepository = pagoArbitrajeRepository;
        this.torneoRepository = torneoRepository;
        this.golRepository = golRepository;
    }

    //Crea la lista completa de todos los partidos por torneo
    public List<PartidoHistorialResponseDTO> listaPartidosPorTorneo(FiltroHistorialPartidos filtro){

        if (filtro.getFechaInicio()!=null && filtro.getFechaFin()!=null){
            return partidoRepository.findByRangoFechasOrderByFechaDesc(
                    filtro.getFechaInicio(),
                    filtro.getFechaFin()
            );
        }
        if (filtro.getNombreEquipo()!=null && !filtro.getNombreEquipo().isEmpty()){
            return partidoRepository.findByNombreEquipoOrderByFechaDesc(filtro.getNombreEquipo());
        }
        return partidoRepository.findByTorneoOrderByFechaDesc(filtro.getIdTorneo());
    }

    @Transactional
    public PlanillaDigitalDTO abrirPlanillaDigital(Integer idPartido) {
        Partido partido = partidoRepository.findById(idPartido)
                .orElseThrow(() -> new RuntimeException("Partido no encontrado"));

        Torneo torneo = torneoRepository.findByIdTorneo(partido.getLocal().getTorneo().getIdTorneo())
                .orElseThrow(()->new RuntimeException("No se encontró Torneo"));

        PagoArbitraje pagoArbitrajeLocal = pagoArbitrajeRepository.findByPartidoIdPartidoAndTorneoEquipoIdTorneoEquipo(idPartido, partido.getLocal().getIdTorneoEquipo())
                .orElse(null);

        PagoArbitraje pagoArbitrajeVisitante = pagoArbitrajeRepository.findByPartidoIdPartidoAndTorneoEquipoIdTorneoEquipo(idPartido, partido.getVisitante().getIdTorneoEquipo())
                .orElse(null);

        //Si pago está completo
        boolean pagoLocal=false;
        boolean pagoVisitante=false;


        //Pagos de Arbitraje
        Double arbitrajeLocal=0.0;
        Double arbitrajeVisitante=0.0;

        //lista de jugadores
        List<JugadorPlanillaDTO> jugadoresLocal;
        List<JugadorPlanillaDTO> jugadoresVisitantes;

        //validar si existe un pago Registrado
        if (pagoArbitrajeLocal!=null){
            arbitrajeLocal=pagoArbitrajeLocal.getMonto();
        }

        if (pagoArbitrajeVisitante!=null){
            arbitrajeVisitante=pagoArbitrajeVisitante.getMonto();
        }

        //validar si ya se pago el arbitraje
        if (arbitrajeLocal.equals(torneo.getValorArbitraje())) {
            pagoLocal = true;
        }
        if (arbitrajeVisitante.equals(torneo.getValorArbitraje())){
            pagoVisitante = true;
        }

        // Evitar duplicar participaciones si el partido ya inició
        if (!"PROGRAMADO".equals(partido.getEstado())) {
            jugadoresLocal=listarParticipantesPorPartido(partido.getIdPartido(),partido.getLocal().getIdTorneoEquipo());
            jugadoresVisitantes=listarParticipantesPorPartido(partido.getIdPartido(),partido.getVisitante().getIdTorneoEquipo());
        }else {
            jugadoresLocal=listarDisponiblesPorEquipo(partido.getLocal().getIdTorneoEquipo());
            jugadoresVisitantes=listarDisponiblesPorEquipo(partido.getVisitante().getIdTorneoEquipo());
        }
        String nombreEquipoLocal = partido.getLocal().getEquipo().getNombreEquipo();
        String nombreEquipoVisitante= partido.getVisitante().getEquipo().getNombreEquipo();
        LocalDate fechaPartido = partido.getFechaPartido();
        LocalTime horaPartido = partido.getHoraPartido();
        String nombreCancha = partido.getNombreCancha();
        String faseTorneo = partido.getFaseTorneo();

        return new PlanillaDigitalDTO(
                partido.getIdPartido(),
                torneo.getIdTorneo(),
                torneo.getNombreTorneo(),
                fechaPartido,
                horaPartido,
                nombreCancha,
                partido.getGolesLocal(),
                partido.getGolesVisitante(),
                partido.getPenalesLocal(),
                partido.getPenalesVisitante(),
                faseTorneo,
                partido.getEstado(),
                partido.getLocal().getIdTorneoEquipo(),
                nombreEquipoLocal,
                jugadoresLocal,
                arbitrajeLocal,
                pagoLocal,
                partido.getVisitante().getIdTorneoEquipo(),
                nombreEquipoVisitante,
                jugadoresVisitantes,
                arbitrajeVisitante,
                pagoVisitante
        );
    }

    @Transactional
    public void iniciarPartido(Integer idPartido) {
        // 1. Validar el partido
        Partido partido = partidoRepository.findById(idPartido)
                .orElseThrow(() -> new RuntimeException("Partido no encontrado"));

        // 2. Evitar duplicar participaciones si el partido ya inició
        if (!partido.getEstado().equals("PROGRAMADO")) {
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
        partido.setEstado("EN CURSO");
        partidoRepository.save(partido);
    }

    @Transactional
    public void finalizarPartido(Integer idPartido){
        Partido partido = partidoRepository.findByIdPartido(idPartido)
                .orElseThrow(()-> new ResourceNotFoundException("Partido No encontrado"));

        partido.setEstado("FINALIZADO");
        partidoRepository.save(partido);
    }

    //retorna una lista de jugadores disponibles para el partido.
    public List<JugadorPlanillaDTO> listarDisponiblesPorEquipo(Integer idTorneoEquipo) {
        return torneoEquipoJugadorRepository.findByTorneoEquipoIdTorneoEquipoAndActivo(idTorneoEquipo, true)
                .stream()
                .map(j -> new JugadorPlanillaDTO(
                        j.getIdTorneoEquipoJugador(),
                        j.getJugador().getNombreJugador()+" "+
                                j.getJugador().getApellidosJugador()))
                .collect(Collectors.toList());
    }

    //retorna una lista de jugadores que ya fueron participantes del partido
    public List<JugadorPlanillaDTO> listarParticipantesPorPartido(Integer idPartido, Integer idTorneoEquipo) {
        return participacionJugadorRepository.findJugadoresPlanilla(idPartido,idTorneoEquipo)
                .stream()
                .map(p -> new JugadorPlanillaDTO(
                        p.getIdReferencia(),
                        p.getNombreJugador(),
                        p.getDorsal(),
                        p.getCantGoles(),
                        p.getCantAmarillas(),
                        p.getCantRojas(),
                        p.getCantAzules()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void actualizarDorsalManual(Integer idParticipacion, String nuevoDorsal) {
        ParticipacionJugador participacion = participacionJugadorRepository.findById(idParticipacion)
                .orElseThrow(() -> new RuntimeException("No se encontró la participación del jugador"));

        participacion.setDorsalJugador(nuevoDorsal);
        participacionJugadorRepository.save(participacion);
    }

    private void registrarParticipaciones(Partido partido, List<TorneoEquipoJugador> jugadores) {
        for (TorneoEquipoJugador tej : jugadores) {
            ParticipacionJugador participacion = new ParticipacionJugador();
            participacion.setPartido(partido);
            participacion.setTorneoEquipoJugador(tej); // El 'id_torneo_equipos_jugadores' de tu entidad
            participacionJugadorRepository.save(participacion);
        }
    }

    public PartidoResDuplicateDTO programarPartido(PartidoCreateDTO dto) {

        Optional<TorneoEquipo> localOpt= torneoEquipoRepository.findByTorneo_IdTorneoAndEquipo_IdEquipo(dto.getIdTorneo(),dto.getIdEquipoLocal());
        Optional<TorneoEquipo> visitantelOpt= torneoEquipoRepository.findByTorneo_IdTorneoAndEquipo_IdEquipo(dto.getIdTorneo(),dto.getIdEquipoVisitante());

        if (localOpt.isEmpty()){
            return new PartidoResDuplicateDTO("ERROR","Equipo local no encontrado");
        }
        if (visitantelOpt.isEmpty()){
            return new PartidoResDuplicateDTO("ERROR",
                    "Equipo visitante no encontrado");
        }

        TorneoEquipo local=localOpt.get();
        TorneoEquipo visitante=visitantelOpt.get();

        if (local.getIdTorneoEquipo().equals(visitante.getIdTorneoEquipo())) {
            return new PartidoResDuplicateDTO("ERROR",
                    "Un equipo no puede jugar contra sí mismo");
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
        nuevoPartido.setEstado("PROGRAMADO");

        partidoRepository.save(nuevoPartido);
        return new PartidoResDuplicateDTO("SUCCESS", "Partido programado exitosamente.");
    }
}
