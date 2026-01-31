package com.torneo.goldesk.Controller;

import com.torneo.goldesk.Service.TorneoEquipoService;
import com.torneo.goldesk.dto.TorneoEquipo.TorneoEquipoCreateDTO;
import com.torneo.goldesk.dto.TorneoEquipo.TorneoEquipoResponseDTO;
import com.torneo.goldesk.dto.TorneoEquipo.TorneoEquipoUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/torneoEquipos")
public class TorneoEquipoController {

    private final TorneoEquipoService torneoEquipoService;


    public TorneoEquipoController(TorneoEquipoService torneoEquipoService) {
        this.torneoEquipoService = torneoEquipoService;
    }

    //consulta una lista de Equipos según el equipos al que pertenece
    @GetMapping("/torneos/{idTorneo}/equipos")
    public ResponseEntity<List<Map<String, Object>>> obtenerEquiposDeUnTorneo(@PathVariable Integer idTorneo) {
        List<Map<String, Object>> equipos = torneoEquipoService.listarNombresEquiposPorTorneo(idTorneo);

        // Condición en una sola línea para responder 204 si está vacía o 200 si hay datos
        return equipos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(equipos);
    }

    @GetMapping("/torneo/{idTorneo}")
    public ResponseEntity<List<TorneoEquipoResponseDTO>> listarPorTorneo(@PathVariable Integer idTorneo) {
        return ResponseEntity.ok(torneoEquipoService.listarEquiposPorTorneo(idTorneo));
    }

    @PatchMapping("/actualizar-nombre")
    public ResponseEntity<?> editarNombreEquipo(@RequestBody TorneoEquipoUpdateDTO dto) {
        torneoEquipoService.actualizarNombrePersonalizado(dto);
        return ResponseEntity.ok("Nombre personalizado actualizado correctamente");
    }

    @PostMapping("/inscribir")
    public ResponseEntity<String> inscribirEquipo(@RequestBody TorneoEquipoCreateDTO dto){

        torneoEquipoService.inscribirEquipo(dto);
        return ResponseEntity.ok("Equipo inscrito en el torneo exitosamente");
    }
}
