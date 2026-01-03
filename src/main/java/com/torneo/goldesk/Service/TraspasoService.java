package com.torneo.goldesk.Service;

import com.torneo.goldesk.Entity.TorneoEquipo;
import com.torneo.goldesk.Entity.TorneoEquipoJugador;
import com.torneo.goldesk.Entity.Traspaso;
import com.torneo.goldesk.Repository.OrganizadorRepository;
import com.torneo.goldesk.Repository.TorneoEquipoJugadorRepository;
import com.torneo.goldesk.Repository.TorneoEquipoRepository;
import com.torneo.goldesk.Repository.TraspasoRepository;
import com.torneo.goldesk.dto.traspaso.TraspasoCreateDTO;
import com.torneo.goldesk.dto.traspaso.TraspasoResponseDTO;
import com.torneo.goldesk.dto.traspaso.TraspasoUpdateDTO;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TraspasoService {

    private final TraspasoRepository traspasoRepository;
    private final TorneoEquipoJugadorRepository torneoEquipoJugadorRepository;
    private final OrganizadorRepository organizadorRepository;
    private final TorneoEquipoRepository torneoEquipoRepository;
    private final PdfGeneratorService pdfGeneratorService;


    public TraspasoService(TraspasoRepository traspasoRepository, TorneoEquipoJugadorRepository torneoEquipoJugadorRepository, OrganizadorRepository organizadorRepository, TorneoEquipoRepository torneoEquipoRepository, PdfGeneratorService pdfGeneratorService) {
        this.traspasoRepository = traspasoRepository;
        this.torneoEquipoJugadorRepository = torneoEquipoJugadorRepository;
        this.organizadorRepository = organizadorRepository;
        this.torneoEquipoRepository = torneoEquipoRepository;
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @Transactional
    public TraspasoResponseDTO aprobarSolicitud(Integer idTraspaso) {
        // 1. Obtener la solicitud con todos sus datos
        Traspaso solicitud = traspasoRepository.findById(idTraspaso)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        String cedulaOrg =(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!solicitud.getOrganizador().getCedulaOrg().equals(cedulaOrg)){
            throw new RuntimeException("No está autorizado para Procesar esta solicitud");
        }

        if (!"PENDIENTE".equals(solicitud.getEstado())) {
            throw new RuntimeException("La solicitud ya ha sido procesada.");
        }
        // --- VALIDACIÓN DE SEGURIDAD: Evitar duplicados ---
        boolean yaExisteEnDestino = torneoEquipoJugadorRepository
                .existsByJugadorAndTorneoEquipoAndActivoTrue(solicitud.getJugador(), solicitud.getTorneoEquipoSolicita());

        if (yaExisteEnDestino) {
            throw new RuntimeException("Error: El jugador ya se encuentra activo en el equipo de destino.");
        }

        // --- VALIDACIÓN DE SEGURIDAD: Evitar mismo equipo ---
        if (solicitud.getTorneoEquipoActual().getIdTorneoEquipo().equals(solicitud.getTorneoEquipoSolicita().getIdTorneoEquipo())) {
            throw new RuntimeException("Error: El equipo de origen y destino no pueden ser el mismo.");
        }

        // 2. INHABILITAR al jugador en el equipo antiguo
        // Accedemos al registro que vincula al jugador con el equipo de origen
        TorneoEquipoJugador registroAntiguo=torneoEquipoJugadorRepository.findByJugadorAndTorneoEquipo(solicitud.getJugador(),solicitud.getTorneoEquipoActual())
                .orElseThrow(()-> new RuntimeException("No se encontró Registro"));
        registroAntiguo.setActivo(false);

        // Guardamos el cambio en el repositorio correspondiente
        torneoEquipoJugadorRepository.save(registroAntiguo);

        // 3. CREAR nueva instancia en el equipo nuevo
        TorneoEquipoJugador nuevoRegistro = new TorneoEquipoJugador();
        nuevoRegistro.setJugador(solicitud.getJugador());
        nuevoRegistro.setTorneoEquipo(solicitud.getTorneoEquipoSolicita()); // El equipo destino
        nuevoRegistro.setActivo(true);
        nuevoRegistro.setFechaInscripcion(LocalDateTime.now());

        // Guardamos el nuevo vínculo
        torneoEquipoJugadorRepository.save(nuevoRegistro);

        // 4. Actualizar la solicitud de traspaso
        solicitud.setEstado("APROBADO");
        solicitud.setFechaRespuesta(LocalDateTime.now());
        traspasoRepository.save(solicitud);

        String nombreEquipoOrigen = solicitud.getTorneoEquipoActual().getNombrePersonalizado()==null?
                solicitud.getTorneoEquipoActual().getEquipo().getNombreEquipo(): solicitud.getTorneoEquipoActual().getNombrePersonalizado();
        String nombreEquipoDestino =solicitud.getTorneoEquipoSolicita().getNombrePersonalizado()==null?
                solicitud.getTorneoEquipoSolicita().getEquipo().getNombreEquipo(): solicitud.getTorneoEquipoSolicita().getNombrePersonalizado();

        return new TraspasoResponseDTO(
                idTraspaso,
                solicitud.getJugador().getNombreJugador(),
                solicitud.getJugador().getCedulaJug(),
                nombreEquipoOrigen,
                nombreEquipoDestino,
                solicitud.getAsunto(),
                solicitud.getEstado(),
                solicitud.getFechaRespuesta(),
                solicitud.getFechaRespuesta()
        );
    }

    @Transactional
    public void guardarDocumentoFirmado(Integer id, MultipartFile archivo) throws IOException {
        Traspaso solicitud = traspasoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        // Validar que sea un PDF
        if (!"application/pdf".equals(archivo.getContentType())) {
            throw new RuntimeException("Solo se permiten archivos PDF.");
        }

        // Sobreescribimos el PDF generado automáticamente con el PDF que tiene las firmas reales
        solicitud.setDocumentoFirmado(archivo.getBytes());

        // Opcional: Podrías cambiar un estado interno aquí si quieres saber que ya se subió el soporte
        traspasoRepository.save(solicitud);
    }

    public List<TraspasoResponseDTO> listarPorEstadoYOrganizador(String estado) {
        String cedulaOrg= (String) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        List<Traspaso> solicitudes= traspasoRepository.findByEstadoAndOrganizador_CedulaOrg(estado, cedulaOrg);

        return solicitudes.stream().map(s -> new TraspasoResponseDTO(
                s.getIdTraspaso(),
                s.getJugador().getNombreJugador() + " " + s.getJugador().getApellidosJugador(),
                s.getJugador().getCedulaJug(),
                s.getTorneoEquipoActual().getEquipo().getNombreEquipo(),
                s.getTorneoEquipoSolicita().getEquipo().getNombreEquipo(),
                s.getAsunto(),
                s.getEstado(),
                s.getFechaSolicitud(),
                s.getFechaRespuesta()
        )).toList();
    }

    @Transactional
    public byte[] crearSolicitud(TraspasoCreateDTO dto) {
        // 1. VALIDACIÓN DE DUPLICADOS
        // Verificamos si ya hay un proceso en curso para este jugador
        boolean yaTieneSolicitud = traspasoRepository.existsByJugador_IdJugadorAndEstado(dto.getIdJugador(), "PENDIENTE");

        if (yaTieneSolicitud) {
            throw new RuntimeException("Ya existe una solicitud de traspaso PENDIENTE para este jugador. " +
                    "Debe esperar a que el organizador la procese.");
        }

        // 2. Identificar dónde está el jugador actualmente en este torneo
        // Buscamos su inscripción activa
        TorneoEquipoJugador inscripcionActual = torneoEquipoJugadorRepository
                .findActiveInscripcionByJugadorIdJugador(dto.getIdJugador())
                .orElseThrow(() -> new RuntimeException("El jugador no tiene una inscripción activa para traspasar."));

        // 3. Crear la entidad de solicitud
        Traspaso nuevaSolicitud = new Traspaso();

        // Seteamos quién responde (deberías buscar el objeto Organizador por su cédula aquí)
        String cedulaOrganizador=inscripcionActual.getTorneoEquipo().getTorneo().getOrganizador().getCedulaOrg();
        nuevaSolicitud.setOrganizador(organizadorRepository.findByCedulaOrg(cedulaOrganizador)
                .orElseThrow(()-> new RuntimeException("Organizador No encontrado.")));

        // Mapeamos los datos de tu tabla
        nuevaSolicitud.setJugador(inscripcionActual.getJugador());
        nuevaSolicitud.setTorneoEquipoActual(inscripcionActual.getTorneoEquipo());

        // El equipo que solicita el fichaje
        TorneoEquipo equipoSolicita = torneoEquipoRepository.findById(dto.getIdTorneoEquipoSolicita())
                .orElseThrow(()-> new RuntimeException("El equipo de destino no existe."));
        // Validación extra: ¿El equipo nuevo pertenece al mismo torneo que el actual?
        if (!equipoSolicita.getTorneo().getIdTorneo().equals(inscripcionActual.getTorneoEquipo().getTorneo().getIdTorneo())) {
            throw new RuntimeException("El equipo de destino debe pertenecer al mismo torneo actual. No es necesario Realizar Traspaso.");
        }
        nuevaSolicitud.setTorneoEquipoSolicita(equipoSolicita);

        nuevaSolicitud.setAsunto(dto.getAsuntoTraspaso());
        nuevaSolicitud.setEstado("PENDIENTE");

        traspasoRepository.save(nuevaSolicitud);

        // 4. GENERACIÓN AUTOMÁTICA DEL PDF
        byte[] pdfGenerado = pdfGeneratorService.generarPdfTraspaso(nuevaSolicitud.getIdTraspaso());

        // Guardamos los bytes del PDF en la misma entidad
        nuevaSolicitud.setDocumentoFirmado(pdfGenerado);

        // 5. Segundo Guardado (Actualización con el PDF incluido)
        traspasoRepository.save(nuevaSolicitud);

        return pdfGenerado ;
    }

    @Transactional
    public String rechazarSolicitud(TraspasoUpdateDTO dto) {
        Traspaso solicitud = traspasoRepository.findByIdTraspaso(dto.getIdTraspaso())
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        // Sugerencia: Añadir validación de seguridad aquí también
        String cedulaOrg = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!solicitud.getOrganizador().getCedulaOrg().equals(cedulaOrg)) {
            throw new RuntimeException("No está autorizado para rechazar esta solicitud");
        }

        solicitud.setEstado("RECHAZADO");
        solicitud.setObservaciones(dto.getObservaciones());
        solicitud.setFechaRespuesta(LocalDateTime.now());

        traspasoRepository.save(solicitud);
        return "La solicitud de Traspaso ha sido Rechazado.";
    }
}
