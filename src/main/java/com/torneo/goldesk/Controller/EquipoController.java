package com.torneo.goldesk.Controller;

import com.torneo.goldesk.Service.EquipoService;
import com.torneo.goldesk.dto.equipo.EquipoCreateDTO;
import com.torneo.goldesk.dto.equipo.EquipoResponseDTO;
import com.torneo.goldesk.dto.equipo.EquipoUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/equipos")
public class EquipoController {

    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService){
        this.equipoService=equipoService;
    }

    @PatchMapping("/{id}/recuperar")
    public ResponseEntity<String> recuperarEquipo(@PathVariable Integer id){

        equipoService.recuperarEquipo(id);
        return ResponseEntity.ok("Equipo Restaurado Exitosamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEquipo(@PathVariable Integer id){

        equipoService.eliminarEquipo(id);
        return ResponseEntity.ok("Equipo Eliminado Correctamente");
    }

    @PutMapping
    public ResponseEntity<String> actualizarEquipo(@RequestBody EquipoUpdateDTO dto){
        equipoService.actualizarEquipo(dto);
        return ResponseEntity.ok("Equipo actualizado Correctamente");
    }

    @PostMapping
    public ResponseEntity<EquipoResponseDTO> crearEquipo(@RequestBody EquipoCreateDTO dto){
        EquipoResponseDTO equipoCreado = equipoService.crearEquipo(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(equipoCreado);
    }

    @GetMapping
    public List<EquipoResponseDTO>listarTodos(){
        return equipoService.obtenerTodos();
    }

    @GetMapping("/activos")
    public List<EquipoResponseDTO> listarActivos(){
        return equipoService.obtenerActivos();
    }
    @GetMapping("/inactivos")
    public List<EquipoResponseDTO> listaInactivos(){
        return equipoService.obtenerInactivos();
    }
}
