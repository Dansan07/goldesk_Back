package com.torneo.goldesk.Controller;

import com.torneo.goldesk.Service.PagoInscripcionService;
import com.torneo.goldesk.dto.registroPagos.inscripcion.AbonoDetalleInscripcionDTO;
import com.torneo.goldesk.dto.registroPagos.inscripcion.InscripcionTorneoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/pagos-inscripcion")
public class PagoInscripcionController {

    private final PagoInscripcionService pagoInscripcionService;

    public PagoInscripcionController(PagoInscripcionService pagoInscripcionService) {
        this.pagoInscripcionService = pagoInscripcionService;
    }

    @GetMapping("/listado/{idTorneo}")
    public ResponseEntity<?> obtenerListadoInscripcion(@PathVariable Integer idTorneo,
                                                       @RequestParam(required = false) String estadoPago){
        List<InscripcionTorneoResponse> inscripcion = pagoInscripcionService.obtenerListadoInscripcion(idTorneo, estadoPago);
        if (inscripcion.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(inscripcion);
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody Map<String, Object> datos) {
        pagoInscripcionService.registrarPagoInscripcion(datos);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Pago de inscripción registrado");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/estado-cuenta/{idTorneoEquipo}")
    public ResponseEntity<?> verEstadoCuenta(@PathVariable Integer idTorneoEquipo) {
        List<AbonoDetalleInscripcionDTO> response = pagoInscripcionService.obtenerEstadoCuenta(idTorneoEquipo);
        if (response.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }
}
