package com.torneo.goldesk.Controller;

import com.torneo.goldesk.Service.OrganizadorService;
import com.torneo.goldesk.Service.PanelOrganizadorService;
import com.torneo.goldesk.dto.PanelOrganizador.VistaTorneoEquiposDTO;
import com.torneo.goldesk.dto.login.LoginRequestDTO;
import com.torneo.goldesk.dto.actores.organizador.OrganizadorCreateDTO;
import com.torneo.goldesk.dto.actores.organizador.OrganizadorResponseDTO;
import com.torneo.goldesk.dto.actores.organizador.OrganizadorUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/organizadores")
public class OrganizadorController {

    private final OrganizadorService organizadorService;
    private final PanelOrganizadorService panelOrganizadorService;

    public OrganizadorController(OrganizadorService organizadorService, PanelOrganizadorService panelOrganizadorService) {
        this.organizadorService = organizadorService;
        this.panelOrganizadorService = panelOrganizadorService;
    }

    //busca los torneos que le perteneces a su respectivo organizador
    @GetMapping("/{cedula}/torneos")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ORGANIZADOR')")
    public ResponseEntity<?> obtenerTorneosDelOrganizador(@PathVariable String cedula) {
        // Extraer la cédula del contexto de seguridad (la que viene en el JWT)
        String cedulaToken =
                (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!cedulaToken.equals(cedula)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No tienes Permiso para acceder a los datos de otro organizador");
        }

        List<Map<String, Object>> torneos = panelOrganizadorService.listarTorneosPorOrganizador(cedula);

        // Si no tiene torneos, enviamos 204 (No Content), de lo contrario 200 (OK)
        return torneos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(torneos);
    }

    //muestra una vista de torneos con sus respectivos equipos asociados y activos
    @GetMapping("/{cedula}/panel-general")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ORGANIZADOR')")
    public ResponseEntity<List<VistaTorneoEquiposDTO>> obtenerPanel(@PathVariable String cedula) {
        return ResponseEntity.ok(panelOrganizadorService.obtenerVistaEquiposPorTorneo(cedula));
    }

    //habilita el organizador con Activo=true
    @PatchMapping("/{cedula}/recuperar")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> activarOrganizador(@PathVariable String cedula){

        organizadorService.activarOrganizador(cedula);
        return ResponseEntity.ok("Organizador Activado Exitosamente");
    }

    //inhabilita el organizador con Activo=false
    @DeleteMapping("/{cedula}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> inactivarOrganizador(@PathVariable String cedula){

        organizadorService.inactivarOrganizador(cedula);
        return ResponseEntity.ok("Organizador se ha inhabilitado");
    }

    //actualiza la información del organizador
    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ORGANIZADOR')")
    public ResponseEntity<String> actualizarOrganizador(@RequestBody OrganizadorUpdateDTO dto){
        organizadorService.actualizarOrganizador(dto);
        return ResponseEntity.ok("Datos de Organizador actualizados Correctamente");
    }

    //crea un nuevo Organizador
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<OrganizadorResponseDTO> crearOrganizador(@RequestBody OrganizadorCreateDTO dto){
        OrganizadorResponseDTO organizadorCreado = organizadorService.crearOrganizador(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(organizadorCreado);
    }

    //muestra una lista de todos los organizadores
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<OrganizadorResponseDTO> listarTodos(){
        return organizadorService.obtenerTodos();
    }

    //muestra una lista de todos los organizadores ACTIVOS
    @GetMapping("/activos")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<OrganizadorResponseDTO> listarActivos(){
        return organizadorService.obtenerActivos();
    }

    //muestra una lista de todos los organizadores INACTIVOS
    @GetMapping("/inactivos")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<OrganizadorResponseDTO> listaInactivos(){
        return organizadorService.obtenerInactivos();
    }
}
