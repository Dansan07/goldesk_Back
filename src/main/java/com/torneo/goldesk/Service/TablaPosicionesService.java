package com.torneo.goldesk.Service;

import com.torneo.goldesk.Repository.PartidoRepository;
import com.torneo.goldesk.dto.tablaPosiciones.TablaPosicionesDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TablaPosicionesService {

    private final PartidoRepository partidoRepository;

    public TablaPosicionesService(PartidoRepository partidoRepository) {
        this.partidoRepository = partidoRepository;
    }

    public List<TablaPosicionesDTO> obtenerTabla(Integer idTorneo) {
        return partidoRepository.generarTablaPosiciones(idTorneo);
    }
}
