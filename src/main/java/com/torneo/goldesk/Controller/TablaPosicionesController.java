package com.torneo.goldesk.Controller;

import com.torneo.goldesk.Service.TablaPosicionesService;
import com.torneo.goldesk.dto.tablaPosiciones.TablaPosicionesDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/tabla-posiciones")
public class TablaPosicionesController {

    private final TablaPosicionesService tablaService;

    public TablaPosicionesController(TablaPosicionesService tablaService) {
        this.tablaService = tablaService;
    }

    @GetMapping("/{idTorneo}")
    public List<TablaPosicionesDTO> verTabla(@PathVariable Integer idTorneo) {
        return tablaService.obtenerTabla(idTorneo);
    }
}
