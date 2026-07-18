package com.torneo.goldesk.Service;

import com.torneo.goldesk.Entity.Jugador;
import com.torneo.goldesk.Entity.TorneoEquipo;
import com.torneo.goldesk.Entity.TorneoEquipoJugador;
import com.torneo.goldesk.Exception.PreconditionFailed;
import com.torneo.goldesk.Exception.ResourceNotFoundException;
import com.torneo.goldesk.Repository.JugadorRepository;
import com.torneo.goldesk.Repository.TorneoEquipoJugadorRepository;
import com.torneo.goldesk.Repository.TorneoEquipoRepository;
import com.torneo.goldesk.dto.actores.jugador.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JugadorService {

    private final JugadorRepository jugadorRepository;
    private final TorneoEquipoJugadorRepository torneoEquipoJugadorRepository;
    private final TorneoEquipoRepository torneoEquipoRepository;


    public JugadorService(JugadorRepository jugadorRepository, TorneoEquipoJugadorRepository torneoEquipoJugadorRepository, TorneoEquipoRepository torneoEquipoRepository) {
        this.jugadorRepository = jugadorRepository;
        this.torneoEquipoJugadorRepository = torneoEquipoJugadorRepository;
        this.torneoEquipoRepository = torneoEquipoRepository;
    }
    @Transactional(readOnly = true)
    public List<EstadisticasJugadorDTO> obtenerEstadisticasPorEquipo(Integer idTorneoEquipo){
        return torneoEquipoJugadorRepository.obtenerEstadisticasPorEquipo(idTorneoEquipo);
    }

    @Transactional(readOnly = true)
    public List<JugadorResponseDTO> buscarJugadoresPorEquipo(Integer idTorneoEquipo){
        List<TorneoEquipoJugador> jugadores = torneoEquipoJugadorRepository
                .findByTorneoEquipo_IdTorneoEquipoAndActivoTrue(idTorneoEquipo);

        if (jugadores.isEmpty()) return Collections.emptyList();

        return jugadores
                .stream()
                .map(this::convertToDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Transactional
    public String traspasarDelegacion(Integer idTorneoEquipo, String cedulaNuevoDelegado) {
        // 1. Obtener todos los jugadores de ese equipo en ese torneo
        List<TorneoEquipoJugador> nomina = torneoEquipoJugadorRepository.findByTorneoEquipo_IdTorneoEquipo(idTorneoEquipo);

        if (nomina.isEmpty()) {
            throw new RuntimeException("El equipo no tiene jugadores inscritos.");
        }

        boolean encontrado = false;
        String nombreJugador="";

        for (TorneoEquipoJugador relacion : nomina) {
            Jugador jugador = relacion.getJugador();

            // 2. Si es el nuevo delegado, lo activamos
            if (jugador.getCedulaJug().equals(cedulaNuevoDelegado)) {
                jugador.setEsDelegado(true);
                nombreJugador= jugador.getNombreJugador();
                encontrado = true;
            } else {
                // 3. A todos los demás (incluyendo al anterior) los ponemos en false
                // Esto asegura que solo quede un delegado activo.
                jugador.setEsDelegado(false);
            }
            jugadorRepository.save(jugador);
        }

        if (!encontrado) {
            throw new RuntimeException("El jugador : "+nombreJugador+"\n" +
                                    " con cédula " + cedulaNuevoDelegado +
                                    " no pertenece a este equipo.");
        }

        return "Traspaso de delegación exitoso. El nuevo responsable es el jugador :" +
                " "+nombreJugador+"\n" +
                " con cédula: " + cedulaNuevoDelegado;
    }

    @Transactional
    public String actualizarDatosJugador(String cedula, JugadorUpdateDTO dto) {
        Jugador jugador = jugadorRepository.findByCedulaJug(cedula)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));

        // Actualizamos solo lo permitido
        jugador.setNombreJugador(dto.getNombre());
        jugador.setApellidosJugador(dto.getApellidos());
        jugador.setTelJugador(dto.getTelefono());
        jugador.setEmailJugador(dto.getEmail());
        jugador.setUrlFotoJugador(dto.getUrlFoto());

        jugadorRepository.save(jugador);
        return "Perfil de " + jugador.getNombreJugador() + " actualizado correctamente.";
    }

    @Transactional
    public String eliminarJugadorDeEquipo(Integer idTorneoEquipoJugador) {
        TorneoEquipoJugador relacion = torneoEquipoJugadorRepository.findByIdTorneoEquipoJugador(idTorneoEquipoJugador)
                .orElseThrow(() -> new RuntimeException("No se encontró la inscripción del jugador."));

        if (!relacion.isActivo()) {
            throw new RuntimeException("El jugador ya se encuentra inactivo en este equipo.");
        }

        relacion.setActivo(false); // Soft Delete
        torneoEquipoJugadorRepository.save(relacion);

        return "El jugador " + relacion.getJugador().getNombreJugador() + " ha sido dado de baja de la nómina.";
    }

    @Transactional
    public String recuperarJugadorEnEquipo(Integer idTorneoEquipoJugador) {
        TorneoEquipoJugador relacion = torneoEquipoJugadorRepository.findByIdTorneoEquipoJugador(idTorneoEquipoJugador)
                .orElseThrow(() -> new RuntimeException("No se encontró la inscripción del jugador."));

        relacion.setActivo(true); // Restaurar
        torneoEquipoJugadorRepository.save(relacion);

        return "El jugador " + relacion.getJugador().getNombreJugador() + " ha sido reincorporado a la nómina.";
    }

    public JugadorCarnetDTO obtenerDatosCarnet(Integer idInscripcion) {
        // Buscamos la relación en la tabla intermedia de tu imagen
        TorneoEquipoJugador tej = torneoEquipoJugadorRepository.findById(idInscripcion)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));

        Jugador j = tej.getJugador();
        TorneoEquipo te = tej.getTorneoEquipo();

        return new JugadorCarnetDTO(
                j.getCedulaJug(),
                j.getNombreJugador(),
                j.getApellidosJugador(),
                j.getTelJugador(),
                j.getEmailJugador(),
                j.getUrlFotoJugador(),
                j.isEsDelegado(),
                te.getNombrePersonalizado()==null?
                te.getEquipo().getNombreEquipo():te.getNombrePersonalizado(),
                te.getTorneo().getNombreTorneo(),
                te.getTorneo().getCategoriaTorneo(),
                tej.getIdTorneoEquipoJugador(),
                tej.getFechaInscripcion()
        );
    }

    @Transactional
    public String inscribirJugadorOptimizado(JugadorCreateDTO dto, Integer idTorneoEquipo) {

        // 1. Intentar buscar al jugador
        Optional<Jugador> jugadorOpt = jugadorRepository.findByCedulaJug(dto.getCedula());
        Jugador jugador;

        if (jugadorOpt.isPresent()) {
            // El jugador ya existe, lo usamos directamente
            jugador = jugadorOpt.get();
        } else {
            // El jugador NO existe, verificamos si el DTO trae los datos para crearlo
            if (dto.getNombre() == null || dto.getNombre().isEmpty()) {
                throw new RuntimeException("Por favor, complete el formulario de registro.");
            }

            String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+\\.[A-Za-z]{2,}$";
            if (dto.getEmail() == null || !dto.getEmail().matches(EMAIL_REGEX)){
                throw new RuntimeException("Formato de Email no válido");
            }

            jugador = new Jugador();
            jugador.setCedulaJug(dto.getCedula());
            jugador.setNombreJugador(dto.getNombre());
            jugador.setApellidosJugador(dto.getApellidos());
            jugador.setEmailJugador(dto.getEmail());
            jugador.setTelJugador(dto.getTelefono());
            jugador.setUrlFotoJugador(dto.getUrlFoto());
            jugador.setEsDelegado(dto.isEsDelegado());
            jugador = jugadorRepository.save(jugador);
        }

        // 2. Obtener la relación Torneo-Equipo
        TorneoEquipo te = torneoEquipoRepository.findById(idTorneoEquipo)
                .orElseThrow(() -> new RuntimeException("Relación Torneo-Equipo no encontrada."));

        // 3. Validar que no juegue ya en ESTE torneo (tu regla de oro)
        if (torneoEquipoJugadorRepository.existsByJugadorAndTorneo(jugador.getCedulaJug(), te.getTorneo().getIdTorneo())) {
            throw new PreconditionFailed("El jugador ya está inscrito en este torneo con otro equipo.");
        }

        // 4. Crear la relación final
        TorneoEquipoJugador relacion = new TorneoEquipoJugador();
        relacion.setJugador(jugador);
        relacion.setTorneoEquipo(te);
        relacion.setActivo(true);
        torneoEquipoJugadorRepository.save(relacion);

        return "Inscripción exitosa: " + jugador.getNombreJugador() + " ha sido añadido a " + te.getEquipo().getNombreEquipo();
    }

    @Transactional
    public JugadorResponseDTO buscarInfoJugador(String cedula){
        Jugador j = jugadorRepository.findByCedulaJug(cedula)
                .orElseThrow(()-> new ResourceNotFoundException("Jugador aún no resgitrado"));

        TorneoEquipoJugador tej = torneoEquipoJugadorRepository
                .findByJugador_IdJugadorAndActivoTrue(j.getIdJugador())
                .orElse(null);

        Integer idTorneoEquipoJugador = tej != null?
                tej.getIdTorneoEquipoJugador():null;

        return new JugadorResponseDTO(
                j.getCedulaJug(),
                j.getNombreJugador(),
                j.getApellidosJugador(),
                j.getTelJugador(),
                j.getEmailJugador(),
                j.getIdJugador(),
                idTorneoEquipoJugador,
                j.getUrlFotoJugador(),
                j.isEsDelegado()
        );

    }

    private JugadorResponseDTO convertToDto(TorneoEquipoJugador tej) {
        // Obtenemos la referencia al jugador para evitar múltiples llamadas al objeto padre
        Jugador j = tej.getJugador();
        if (j == null) return null;

        return new JugadorResponseDTO(
                j.getCedulaJug(),
                j.getNombreJugador(),
                j.getApellidosJugador(),
                j.getTelJugador(),
                j.getEmailJugador(),
                j.getIdJugador(),
                j.getUrlFotoJugador(),
                j.isEsDelegado()
        );
    }
}
