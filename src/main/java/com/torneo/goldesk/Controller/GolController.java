package com.torneo.goldesk.Controller;

import com.torneo.goldesk.Service.GolService;
import com.torneo.goldesk.dto.gol.GolCreateDTO;
import com.torneo.goldesk.dto.gol.GolResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/goles")
public class GolController {

    private final GolService golService;

    public GolController(GolService golService) {
        this.golService = golService;
    }

    @PostMapping
    public void registrarGol(@RequestBody GolCreateDTO dto){
        golService.registrarGol(dto);
    }

    @GetMapping("/buscar-x-jugador")
    public ResponseEntity<List<GolResponseDTO>> obtenerGolesJugadorPartido(
            @RequestParam Integer idParticipacion) {
        return ResponseEntity.ok(golService.listarGolesPorParticipacion(idParticipacion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarGol(@PathVariable Integer id) {
        try {
            golService.eliminarGol(id);
            return ResponseEntity.ok("Gol eliminado y marcador actualizado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el gol: " + e.getMessage());
        }
    }
}
