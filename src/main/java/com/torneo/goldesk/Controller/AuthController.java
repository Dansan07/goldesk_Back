package com.torneo.goldesk.Controller;

import com.torneo.goldesk.Service.Authenticator.AuthService;
import com.torneo.goldesk.Service.OrganizadorService;
import com.torneo.goldesk.dto.login.LoginRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;
    private final OrganizadorService organizadorService;

    public AuthController(AuthService authService, OrganizadorService organizadorService) {
        this.authService = authService;
        this.organizadorService = organizadorService;
    }

    @PostMapping("/acceso-codigo")
    public ResponseEntity<?> loginPorCodigo(@RequestBody Map<String, String> request) {
        String codigo = request.get("codigo");
        try {
            Map<String, Object> response = authService.loginPorCodigo(codigo);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Código no válido");
        }
    }

    //Valida el inicio de sesión del organizador
    @PostMapping("/login-organizador")
    public ResponseEntity<?> loginOrganizador(@Valid @RequestBody LoginRequestDTO loginDto) {
        try {
            return ResponseEntity.ok(authService.loginOrganizador(loginDto));
        } catch (RuntimeException e) {
            // Retornamos 401 si falla la autenticación
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PatchMapping("/actualizar-password/{email}")
    public ResponseEntity<String> actualizarPasswordOrg(@PathVariable String email){
        authService.recuperarPasswordOrg(email);
        return ResponseEntity.ok("Contraseña Actualizada Correctamente");
    }
}
