package com.torneo.goldesk.Service;

import com.torneo.goldesk.Entity.Jugador;
import com.torneo.goldesk.Entity.Torneo;
import com.torneo.goldesk.Entity.TorneoEquipo;
import com.torneo.goldesk.Entity.TorneoEquipoJugador;
import com.torneo.goldesk.Repository.JugadorRepositoty;
import com.torneo.goldesk.Repository.TorneoEquipoJugadorRepository;
import com.torneo.goldesk.Repository.TorneoEquipoRepository;
import com.torneo.goldesk.dto.actores.jugador.JugadorCarnetDTO;
import com.torneo.goldesk.dto.actores.jugador.JugadorCreateDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JugadorService {

    private final JugadorRepositoty jugadorRepositoty;
    private final TorneoEquipoJugadorRepository torneoEquipoJugadorRepository;
    private final TorneoEquipoRepository torneoEquipoRepository;


    public JugadorService(JugadorRepositoty jugadorRepositoty, TorneoEquipoJugadorRepository torneoEquipoJugadorRepository, TorneoEquipoRepository torneoEquipoRepository) {
        this.jugadorRepositoty = jugadorRepositoty;
        this.torneoEquipoJugadorRepository = torneoEquipoJugadorRepository;
        this.torneoEquipoRepository = torneoEquipoRepository;
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
                te.getEquipo().getNombreEquipo(),
                te.getTorneo().getNombreTorneo(),
                te.getTorneo().getCategoriaTorneo(),
                tej.getIdTorneoEquipoJugador()
        );
    }

    @Transactional
    public String inscribirJugadorOptimizado(JugadorCreateDTO dto, Integer idTorneoEquipo) {

        // 1. Intentar buscar al jugador
        Optional<Jugador> jugadorOpt = jugadorRepositoty.findByCedulaJug(dto.getCedula());
        Jugador jugador;

        if (jugadorOpt.isPresent()) {
            // El jugador ya existe, lo usamos directamente
            jugador = jugadorOpt.get();
        } else {
            // El jugador NO existe, verificamos si el DTO trae los datos para crearlo
            if (dto.getNombre() == null || dto.getNombre().isEmpty()) {
                throw new RuntimeException("Jugador no encontrado. Por favor, complete el formulario de registro.");
            }

            jugador = new Jugador();
            jugador.setCedulaJug(dto.getCedula());
            jugador.setNombreJugador(dto.getNombre());
            jugador.setApellidosJugador(dto.getApellidos());
            jugador.setEmailJugador(dto.getEmail());
            jugador.setTelJugador(dto.getTelefono());
            jugador.setUrlFotoJugador(dto.getUrlFoto());
            jugador.setEsDelegado(false);
            jugador = jugadorRepositoty.save(jugador);
        }

        // 2. Obtener la relación Torneo-Equipo
        TorneoEquipo te = torneoEquipoRepository.findById(idTorneoEquipo)
                .orElseThrow(() -> new RuntimeException("Relación Torneo-Equipo no encontrada."));

        // 3. Validar que no juegue ya en ESTE torneo (tu regla de oro)
        if (torneoEquipoJugadorRepository.existsByJugadorAndTorneo(jugador.getCedulaJug(), te.getTorneo().getIdTorneo())) {
            throw new RuntimeException("El jugador ya está inscrito en este torneo con otro equipo.");
        }

        // 4. Crear la relación final
        TorneoEquipoJugador relacion = new TorneoEquipoJugador();
        relacion.setJugador(jugador);
        relacion.setTorneoEquipo(te);
        relacion.setActivo(true);
        torneoEquipoJugadorRepository.save(relacion);

        return "Inscripción exitosa: " + jugador.getNombreJugador() + " ha sido añadido a " + te.getEquipo().getNombreEquipo();
    }




}
