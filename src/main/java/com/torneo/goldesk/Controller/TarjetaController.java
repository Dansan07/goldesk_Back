package com.torneo.goldesk.Controller;

import com.torneo.goldesk.Service.TarjetaService;
import com.torneo.goldesk.dto.tarjeta.TarjetaCreateDTO;
import com.torneo.goldesk.dto.tarjeta.TarjetaResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tarjetas")
public class TarjetaController {

    private final TarjetaService tarjetaService;

    public TarjetaController(TarjetaService tarjetaService) {
        this.tarjetaService = tarjetaService;
    }

    // NUEVO: Listado de tarjetas por Torneo
    @GetMapping("/buscar-x-torneo/{idTorneo}")
    public ResponseEntity<List<TarjetaResponseDTO>> listarPorTorneo(@PathVariable Integer idTorneo) {
        List<TarjetaResponseDTO> tarjetas = tarjetaService.obtenerHistorialTarjetas(idTorneo);
        return ResponseEntity.ok(tarjetas);
    }

    // Registrar una nueva tarjeta
    @PostMapping("/registrar-tarjeta")
    public ResponseEntity<?> registrar(@RequestBody TarjetaCreateDTO dto) {
        tarjetaService.registrarTarjeta(dto);
        return ResponseEntity.ok("Tarjeta registrada con éxito");
    }

    // Buscar tarjetas de un jugador específico en un partido (Para la gestión)
    @GetMapping("/buscar-x-jugador")
    public List<TarjetaResponseDTO> buscarTarjetasPorParticipacion(@RequestParam Integer idParticipacion,
                                                                   @RequestParam String tipoTarjeta) {
        return tarjetaService.obtenerTarjetasPorParticipacion(idParticipacion, tipoTarjeta);
    }

    // Eliminar la tarjeta específica
    @DeleteMapping("/{idTarjeta}")
    public ResponseEntity<String> eliminar(@PathVariable Integer idTarjeta) {
        tarjetaService.eliminarTarjeta(idTarjeta);
        return ResponseEntity.ok("Tarjeta eliminada correctamente");
    }
}
