package com.torneo.goldesk.Controller;

import com.torneo.goldesk.Service.PartidoService;
import com.torneo.goldesk.dto.partido.PartidoCreateDTO;
import com.torneo.goldesk.dto.partido.PartidoResDuplicateDTO;
import com.torneo.goldesk.dto.planillaDigital.PlanillaDigitalDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/partidos")
public class PartidoController {

    private final PartidoService partidoService;


    public PartidoController(PartidoService partidoService) {
        this.partidoService = partidoService;
    }

    @PatchMapping("/participacion/{idParticipacion}/dorsal")
    public ResponseEntity<String> actualizarDorsal(@PathVariable Integer idParticipacion, @RequestBody String dorsal) {
        partidoService.actualizarDorsalManual(idParticipacion, dorsal);
        return ResponseEntity.ok("Dorsal actualizado correctamente");
    }

    // 1. Este se llama al dar click al item del partido
    @GetMapping("/{idPartido}/ver-planilla")
    public ResponseEntity<PlanillaDigitalDTO> abrirPlanillaDigital(@PathVariable Integer idPartido) {
        return ResponseEntity.ok(partidoService.abrirPlanillaDigital(idPartido));
    }

    @PostMapping("/{id}/iniciar")
    public ResponseEntity<String> iniciarPartido(@PathVariable Integer idPartido) {
        try {
            partidoService.iniciarPartido(idPartido);
            return ResponseEntity.ok("Partido iniciado correctamente. Las participaciones han sido generadas.");        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<PartidoResDuplicateDTO> programarPartido(@RequestBody PartidoCreateDTO dto){
        PartidoResDuplicateDTO mensaje = partidoService.programarPartido(dto);
        return ResponseEntity.ok(mensaje);
    }
}
