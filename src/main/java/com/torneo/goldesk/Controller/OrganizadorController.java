package com.torneo.goldesk.Controller;

import com.torneo.goldesk.Service.OrganizadorService;
import com.torneo.goldesk.Service.PanelOrganizadorService;
import com.torneo.goldesk.dto.PanelOrganizador.VistaTorneoEquiposDTO;
import com.torneo.goldesk.dto.actores.organizador.LoginRequestDTO;
import com.torneo.goldesk.dto.actores.organizador.OrganizadorCreateDTO;
import com.torneo.goldesk.dto.actores.organizador.OrganizadorResponseDTO;
import com.torneo.goldesk.dto.actores.organizador.OrganizadorUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/organizadores")
public class OrganizadorController {

    private final OrganizadorService organizadorService;
    private final PanelOrganizadorService panelOrganizadorService;

    public OrganizadorController(OrganizadorService organizadorService, PanelOrganizadorService panelOrganizadorService) {
        this.organizadorService = organizadorService;
        this.panelOrganizadorService = panelOrganizadorService;
    }

    //valida el inicio de sesión del organizador
    @PostMapping("/login")
    public ResponseEntity<?> iniciarSesion(@Valid @RequestBody LoginRequestDTO loginDto) {
        try {
            OrganizadorResponseDTO perfil = organizadorService.login(loginDto);
            return ResponseEntity.ok(perfil);
        } catch (RuntimeException e) {
            // Retornamos 401 si falla la autenticación
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    //busca los torneos que le perteneces a su respectivo organizador
    @GetMapping("/{cedula}/torneos")
    public ResponseEntity<List<Map<String, Object>>> obtenerTorneosDelOrganizador(@PathVariable String cedula) {
        List<Map<String, Object>> torneos = panelOrganizadorService.listarTorneosPorOrganizador(cedula);

        // Si no tiene torneos, enviamos 204 (No Content), de lo contrario 200 (OK)
        return torneos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(torneos);
    }

    //muestra una vista de torneos con sus respectivos equipos asociados y activos
    @GetMapping("/{cedula}/panel-general")
    public ResponseEntity<List<VistaTorneoEquiposDTO>> obtenerPanel(@PathVariable String cedula) {
        return ResponseEntity.ok(panelOrganizadorService.obtenerVistaEquiposPorTorneo(cedula));
    }

    //habilita el organizador con Activo=true
    @PatchMapping("/{cedula}/recuperar")
    public ResponseEntity<String> activarOrganizador(@PathVariable String cedula){

        organizadorService.activarOrganizador(cedula);
        return ResponseEntity.ok("Organizador Activado Exitosamente");
    }

    //inhabilita el organizador con Activo=false
    @DeleteMapping("/{cedula}")
    public ResponseEntity<String> inactivarOrganizador(@PathVariable String cedula){

        organizadorService.inactivarOrganizador(cedula);
        return ResponseEntity.ok("Organizador se ha inhabilitado");
    }

    //actualiza la información del organizador
    @PutMapping
    public ResponseEntity<String> actualizarOrganizador(@RequestBody OrganizadorUpdateDTO dto){
        organizadorService.actualizarOrganizador(dto);
        return ResponseEntity.ok("Datos de Organizador actualizados Correctamente");
    }

    //crea un nuevo Organizador
    @PostMapping
    public ResponseEntity<OrganizadorResponseDTO> crearOrganizador(@RequestBody OrganizadorCreateDTO dto){
        OrganizadorResponseDTO organizadorCreado = organizadorService.crearOrganizador(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(organizadorCreado);
    }

    //muestra una lista de todos los organizadores
    @GetMapping
    public List<OrganizadorResponseDTO> listarTodos(){
        return organizadorService.obtenerTodos();
    }

    //muestra una lista de todos los organizadores ACTIVOS
    @GetMapping("/activos")
    public List<OrganizadorResponseDTO> listarActivos(){
        return organizadorService.obtenerActivos();
    }

    //muestra una lista de todos los organizadores INACTIVOS
    @GetMapping("/inactivos")
    public List<OrganizadorResponseDTO> listaInactivos(){
        return organizadorService.obtenerInactivos();
    }
}
