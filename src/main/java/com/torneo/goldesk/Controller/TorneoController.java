package com.torneo.goldesk.Controller;

import com.torneo.goldesk.Service.TorneoService;
import com.torneo.goldesk.dto.torneo.TorneoCreateDTO;
import com.torneo.goldesk.dto.torneo.TorneoResponseDTO;
import com.torneo.goldesk.dto.torneo.TorneoUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/torneos")
public class TorneoController {

    private final TorneoService torneoService;

    public TorneoController(TorneoService torneoService) {
        this.torneoService = torneoService;
    }

    @GetMapping("/categorias/{cedulaOrg}")
    public ResponseEntity<?> buscarCategorias(@PathVariable String cedulaOrg){
        return ResponseEntity.ok(torneoService.buscarCategoriasExistentes(cedulaOrg));
    }

    @GetMapping("/{idTorneo}")
    public ResponseEntity<?> obtenerTorneoUnico(@PathVariable Integer idTorneo){
        TorneoResponseDTO torneo = torneoService.obtenerTorneoUnico(idTorneo);
        return ResponseEntity.status(HttpStatus.OK).body(torneo);
    }

    @PutMapping("/recuperar/{id}")
    public ResponseEntity<String> recuperarTorneo(@PathVariable Integer id){
        torneoService.recuperarTorneo(id);
        return ResponseEntity.ok("Torneo Restaurado Exitosamente");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> eliminarTorneo(@PathVariable Integer id){
        torneoService.eliminarTorneo(id);
        return ResponseEntity.ok("Torneo Eliminado Correctamente");
    }

    @PutMapping("/actualizar_torneo")
    public ResponseEntity<?> actualizarTorneo(@RequestBody TorneoUpdateDTO dto){
        torneoService.actualizarTorneo(dto);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje","Torneo actualizado Correctamente");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/guardar_torneo")
    public ResponseEntity<?> crearTorneo(@RequestBody TorneoCreateDTO dto){
        torneoService.crearTorneo(dto);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Nuevo Torneo Creado con éxito.");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public List<TorneoResponseDTO> listarTodos(){
        return torneoService.obtenerTodos();
    }

    @GetMapping("/activos")
    public List<TorneoResponseDTO> listarActivos(){
        return torneoService.obtenerActivos();
    }
    @GetMapping("/inactivos")
    public List<TorneoResponseDTO> listaInactivos(){
        return torneoService.obtenerInactivos();
    }
}
