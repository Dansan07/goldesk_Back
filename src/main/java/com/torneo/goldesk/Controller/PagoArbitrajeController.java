package com.torneo.goldesk.Controller;

import com.torneo.goldesk.Service.PagoArbitrajeService;
import com.torneo.goldesk.dto.registroPagos.arbitraje.PagoArbitrajeCreateDTO;
import com.torneo.goldesk.dto.registroPagos.arbitraje.PagoArbitrajeResponseDTO;
import com.torneo.goldesk.dto.registroPagos.arbitraje.PagoArbitrajeUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/pagos-arbitraje")
public class PagoArbitrajeController {

    private final PagoArbitrajeService pagoArbitrajeService;

    public PagoArbitrajeController(PagoArbitrajeService pagoArbitrajeService) {
        this.pagoArbitrajeService = pagoArbitrajeService;
    }

    // Registrar un nuevo pago (POST)
    @PostMapping("/registrar")
    public ResponseEntity<String> registrarPago(@RequestBody PagoArbitrajeCreateDTO dto) {
        try {
            pagoArbitrajeService.registrarPagoArbitraje(dto);
            return new ResponseEntity<>("Pago de arbitraje registrado exitosamente", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Actualizar monto u observación (PUT o PATCH)
    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarPago(@RequestBody PagoArbitrajeUpdateDTO dto) {
        try {
            pagoArbitrajeService.actualizarPagoArbitraje(dto);
            return ResponseEntity.ok("Registro de pago actualizado correctamente");
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Obtener información de un pago específico (GET)
    @GetMapping("/{idPagoArbitraje}")
    public ResponseEntity<?> obtenerPago(@PathVariable Integer idPagoArbitraje) {
        try {
            PagoArbitrajeResponseDTO response = pagoArbitrajeService.mostrarPagoArbitraje(idPagoArbitraje);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
