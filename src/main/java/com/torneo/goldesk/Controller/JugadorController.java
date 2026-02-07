package com.torneo.goldesk.Controller;

import com.torneo.goldesk.Exception.PreconditionFailed;
import com.torneo.goldesk.Service.JugadorService;
import com.torneo.goldesk.dto.actores.jugador.EstadisticasJugadorDTO;
import com.torneo.goldesk.dto.actores.jugador.JugadorCarnetDTO;
import com.torneo.goldesk.dto.actores.jugador.JugadorCreateDTO;
import com.torneo.goldesk.dto.actores.jugador.JugadorUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/jugadores")
public class JugadorController {

    private final JugadorService jugadorService;

    public JugadorController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    @GetMapping("/estadisticas/equipo/{idTorneoEquipo}")
    public ResponseEntity<?> getEstadisticasPorEquipo(@PathVariable Integer idTorneoEquipo){
        List<EstadisticasJugadorDTO> estadisticas = jugadorService.obtenerEstadisticasPorEquipo(idTorneoEquipo);
        if (estadisticas.isEmpty()) {
            return ResponseEntity.noContent().build(); // Devuelve 204 si no hay datos
        }
        return ResponseEntity.ok(estadisticas);
    }

    @GetMapping("/listar-x-equipo/{idTorneoEquipo}")
    public ResponseEntity<?> buscarJugadoresPorEquipo(@PathVariable Integer idTorneoEquipo){
        return ResponseEntity.ok(jugadorService.buscarJugadoresPorEquipo(idTorneoEquipo));
    }

    @PatchMapping("/traspasar-delegado/{idTorneoEquipo}/{cedulaNuevo}")
    public ResponseEntity<String> traspasarDelegado(@PathVariable Integer idTorneoEquipo,@PathVariable String cedulaNuevo) {
        try {
            String msg = jugadorService.traspasarDelegacion(idTorneoEquipo, cedulaNuevo);
            return ResponseEntity.ok(msg);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/actualizar/{cedula}")
    public ResponseEntity<String> actualizar(@PathVariable String cedula, @RequestBody JugadorUpdateDTO dto) {
        return ResponseEntity.ok(jugadorService.actualizarDatosJugador(cedula, dto));
    }

    @PatchMapping("/dar-de-baja/{idInscripcion}")
    public ResponseEntity<?> darDeBaja(@PathVariable Integer idInscripcion) {
        try {
            String respuesta = jugadorService.eliminarJugadorDeEquipo(idInscripcion);
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/reincorporar/{idInscripcion}")
    public ResponseEntity<String> reincorporar(@PathVariable Integer idInscripcion) {
        try {
            String respuesta = jugadorService.recuperarJugadorEnEquipo(idInscripcion);
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/carnet/{idInscripcion}")
    public ResponseEntity<JugadorCarnetDTO> obtenerCarnet(@PathVariable Integer idInscripcion) {
        return ResponseEntity.ok(jugadorService.obtenerDatosCarnet(idInscripcion));
    }

    @PostMapping("/inscribir/{idTorneoEquipo}")
    public ResponseEntity<?> inscribirJugador(
            @RequestBody JugadorCreateDTO dto,
            @PathVariable Integer idTorneoEquipo) {
        try {
            // Llamamos al servicio que tiene la lógica de "buscar o crear"
            String mensaje = jugadorService.inscribirJugadorOptimizado(dto, idTorneoEquipo);
            return new ResponseEntity<>(mensaje, HttpStatus.CREATED);
        }catch (PreconditionFailed e){
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(e.getMessage());
        } catch (RuntimeException e) {
            // Si el jugador ya está en el torneo o no existe la relación, devolvemos el error
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno en el servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscar_info_jugador/{cc}")
    public ResponseEntity<?> buscarInfoJugador(@PathVariable String cc){
        return ResponseEntity.ok(jugadorService.buscarInfoJugador(cc));
    }
}
