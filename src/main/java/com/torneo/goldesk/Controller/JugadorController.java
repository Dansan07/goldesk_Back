package com.torneo.goldesk.Controller;

import com.torneo.goldesk.Service.JugadorService;
import com.torneo.goldesk.dto.actores.jugador.JugadorCreateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/jugadores")
public class JugadorController {

    private final JugadorService jugadorService;

    public JugadorController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    @PostMapping("inscribir/{idTorneoEquipo}")
    public ResponseEntity<String> inscribirJugador(
            @RequestBody JugadorCreateDTO dto,
            @PathVariable Integer idTorneoEquipo) {
        try {
            // Llamamos al servicio que tiene la lógica de "buscar o crear"
            String mensaje = jugadorService.inscribirJugadorOptimizado(dto, idTorneoEquipo);
            return new ResponseEntity<>(mensaje, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Si el jugador ya está en el torneo o no existe la relación, devolvemos el error
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno en el servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
