package com.torneo.goldesk.Service;

import com.torneo.goldesk.Entity.TorneoEquipo;
import com.torneo.goldesk.Entity.TorneoEquipoJugador;
import com.torneo.goldesk.Entity.Traspaso;
import com.torneo.goldesk.Repository.OrganizadorRepository;
import com.torneo.goldesk.Repository.TorneoEquipoJugadorRepository;
import com.torneo.goldesk.Repository.TorneoEquipoRepository;
import com.torneo.goldesk.Repository.TraspasoRepository;
import com.torneo.goldesk.dto.traspaso.TraspasoCreateDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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

    public List<Traspaso> listarPorEstadoYOrganizador(String estado, String cedulaOrg) {
        if (estado.equalsIgnoreCase("PENDIENTE")) {
            // Buscamos las que están esperando aprobación en SUS torneos
            return traspasoRepository.findPendientesPorOrganizador(estado, cedulaOrg);
        } else {
            // Buscamos las que él mismo ya aprobó o rechazó (historial personal)
            return traspasoRepository.findByEstadoAndOrganizador_CedulaOrg(estado, cedulaOrg);
        }
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
            throw new RuntimeException("El equipo de destino debe pertenecer al mismo torneo actual.");
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
    public String responderSolicitud(Integer idTraspaso, String estadoNuevo, String obs, String cedulaOrg) {
        Traspaso solicitud = traspasoRepository.findByIdTraspaso(idTraspaso)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        solicitud.setEstado(estadoNuevo);
        solicitud.setObservaciones(obs);
        solicitud.setFechaRespuesta(LocalDateTime.now());

        if (estadoNuevo.equalsIgnoreCase("APROBADO")) {
            // 1. Desactivar en equipo actual
            TorneoEquipoJugador antigua = torneoEquipoJugadorRepository
                    .findByJugadorAndTorneoEquipo(solicitud.getJugador(), solicitud.getTorneoEquipoActual())
                    .orElseThrow(() -> new RuntimeException("No se encontró la inscripción original del jugador."));
            antigua.setActivo(false);

            // 2. Inscribir en nuevo equipo
            TorneoEquipoJugador nueva = new TorneoEquipoJugador();
            nueva.setJugador(solicitud.getJugador());
            nueva.setTorneoEquipo(solicitud.getTorneoEquipoSolicita());
            nueva.setActivo(true);

            torneoEquipoJugadorRepository.save(antigua);
            torneoEquipoJugadorRepository.save(nueva);
        }

        traspasoRepository.save(solicitud);
        return "Traspaso " + estadoNuevo + " correctamente.";
    }
}
