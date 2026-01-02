package com.torneo.goldesk.Controller;

import com.torneo.goldesk.Service.PagoInscripcionService;
import com.torneo.goldesk.dto.registroPagos.inscripcion.EstadoCuentaInscripcionDTO;
import com.torneo.goldesk.dto.registroPagos.inscripcion.PagoInscripcionCreateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/pagos-inscripcion")
public class PagoInscripcionController {

    private final PagoInscripcionService pagoInscripcionService;

    public PagoInscripcionController(PagoInscripcionService pagoInscripcionService) {
        this.pagoInscripcionService = pagoInscripcionService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> registrar(@RequestBody PagoInscripcionCreateDTO dto) {
        pagoInscripcionService.registrarPagoInscripcion(dto);
        return ResponseEntity.ok("Pago de inscripción registrado");
    }

    @GetMapping("/estado-cuenta/{idTorneoEquipo}")
    public ResponseEntity<EstadoCuentaInscripcionDTO> verEstadoCuenta(@PathVariable Integer idTorneoEquipo) {
        return ResponseEntity.ok(pagoInscripcionService.obtenerEstadoCuenta(idTorneoEquipo));
    }
}
