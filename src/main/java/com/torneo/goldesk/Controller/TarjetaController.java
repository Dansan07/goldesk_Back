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

    // Registrar una nueva tarjeta
    @PostMapping
    public void registrar(@RequestBody TarjetaCreateDTO dto) {
        tarjetaService.registrarTarjeta(dto);
    }

    // Buscar tarjetas de un jugador específico en un partido (Para la gestión)
    @GetMapping("/buscar-x-jugador")
    public List<TarjetaResponseDTO> buscar(@RequestParam Integer idParticipacion) {
        return tarjetaService.obtenerTarjetasPorParticipacion(idParticipacion);
    }

    // Eliminar la tarjeta específica
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        tarjetaService.eliminarTarjeta(id);
        return ResponseEntity.ok("Tarjeta eliminada correctamente");
    }
}
