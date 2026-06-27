package com.torneo.goldesk.Controller;

import com.torneo.goldesk.Service.TorneoService;
import com.torneo.goldesk.dto.torneo.TorneoCreateDTO;
import com.torneo.goldesk.dto.torneo.TorneoResponseDTO;
import com.torneo.goldesk.dto.torneo.TorneoUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<String> actualizarTorneo(@RequestBody TorneoUpdateDTO dto){
        torneoService.actualizarTorneo(dto);
        return ResponseEntity.ok("Torneo actualizado Correctamente");
    }

    @PostMapping("/guardar_torneo")
    public ResponseEntity<TorneoResponseDTO> crearTorneo(@RequestBody TorneoCreateDTO dto){
        TorneoResponseDTO torneoCreado = torneoService.crearTorneo(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(torneoCreado);
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
