package com.torneo.goldesk.Controller;

import com.torneo.goldesk.Service.PartidoService;
import com.torneo.goldesk.dto.partido.PartidoCreateDTO;
import com.torneo.goldesk.dto.partido.PartidoResDuplicateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/partidos")
public class PartidoController {

    private final PartidoService partidoService;


    public PartidoController(PartidoService partidoService) {
        this.partidoService = partidoService;
    }

    @PostMapping
    public ResponseEntity<PartidoResDuplicateDTO> programarPartido(@RequestBody PartidoCreateDTO dto){
        PartidoResDuplicateDTO mensaje = partidoService.programarPartido(dto);
        return ResponseEntity.ok(mensaje);
    }
}
