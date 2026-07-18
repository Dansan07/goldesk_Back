package com.torneo.goldesk.Controller;

import com.torneo.goldesk.Service.TorneoService;
import com.torneo.goldesk.dto.torneo.ResumenInscripcion;
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

    @GetMapping("/resumen-inscripcion/{idTorneo}")
    public ResponseEntity<ResumenInscripcion> resumenInscripcionTorneo(@PathVariable Integer idTorneo){
        return ResponseEntity.ok(torneoService.obtenerResumenInscripcionTorneo(idTorneo));
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
    public ResponseEntity<?> recuperarTorneo(@PathVariable Integer id){
        torneoService.recuperarTorneo(id);
        Map<String, String> map = new HashMap<>();
        map.put("message","Torneo Restaurado Exitosamente" );
        return ResponseEntity.ok(map);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> eliminarTorneo(@PathVariable Integer id){
        torneoService.eliminarTorneo(id);
        Map<String, String> map = new HashMap<>();
        map.put("message", "Torneo Eliminado Correctamente");
        return ResponseEntity.ok(map);
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
