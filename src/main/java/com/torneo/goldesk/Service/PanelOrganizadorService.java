package com.torneo.goldesk.Service;

import com.torneo.goldesk.Repository.TorneoEquipoRepository;
import com.torneo.goldesk.Repository.TorneoRepository;
import com.torneo.goldesk.dto.PanelOrganizador.VistaTorneoEquiposDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PanelOrganizadorService {

    private final TorneoRepository torneoRepository;
    private final TorneoEquipoRepository torneoEquipoRepository;

    public PanelOrganizadorService(TorneoRepository torneoRepository, TorneoEquipoRepository torneoEquipoRepository) {
        this.torneoRepository = torneoRepository;
        this.torneoEquipoRepository = torneoEquipoRepository;
    }

    public List<VistaTorneoEquiposDTO> obtenerVistaEquiposPorTorneo(String cedulaOrg) {
        // 1. Buscamos torneos activos del organizador
        return torneoRepository.findByOrganizador_CedulaOrgAndActivoTrue(cedulaOrg)
                .stream()
                .map(torneo -> {
                    // 2. Buscamos equipos activos para CADA torneo
                    List<String> nombresEquipos = torneoEquipoRepository
                            .findByTorneo_IdTorneoAndEquipoActivoTrue(torneo.getIdTorneo())
                            .stream()
                            .map(te -> te.getNombrePersonalizado()==null ?
                                                    te.getEquipo().getNombreEquipo():
                                                    te.getNombrePersonalizado()
                            ).toList();

                    return new VistaTorneoEquiposDTO(
                            torneo.getIdTorneo(),
                            torneo.getNombreTorneo(),
                            nombresEquipos
                    );
                }).toList();
    }

    public List<Map<String, Object>> listarTorneosPorOrganizador(String cedulaOrg) {
        return torneoRepository.findByOrganizador_CedulaOrgAndActivoTrue(cedulaOrg)
                .stream()
                .map(t -> Map.<String, Object>of(
                        "idTorneo", t.getIdTorneo(),
                        "nombreTorneo", t.getNombreTorneo(),
                        "partidosInicial", t.getPartidosInicial()
                ))
                .toList();
    }
}
