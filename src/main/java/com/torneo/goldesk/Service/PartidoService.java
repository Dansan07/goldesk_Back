package com.torneo.goldesk.Service;

import com.torneo.goldesk.Entity.Partido;
import com.torneo.goldesk.Entity.TorneoEquipo;
import com.torneo.goldesk.Repository.PartidoRepository;
import com.torneo.goldesk.Repository.TorneoEquipoRepository;
import com.torneo.goldesk.dto.partido.PartidoCreateDTO;
import com.torneo.goldesk.dto.partido.PartidoResDuplicateDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PartidoService {

    private final PartidoRepository partidoRepository;
    private final TorneoEquipoRepository torneoEquipoRepository;


    public PartidoService(PartidoRepository partidoRepository, TorneoEquipoRepository torneoEquipoRepository) {
        this.partidoRepository = partidoRepository;
        this.torneoEquipoRepository = torneoEquipoRepository;
    }

    public PartidoResDuplicateDTO programarPartido(PartidoCreateDTO dto) {

        Optional<TorneoEquipo> localOpt= torneoEquipoRepository.findById(dto.getIdEquipoLocal());
        Optional<TorneoEquipo> visitantelOpt= torneoEquipoRepository.findById(dto.getIdEquipoVisitante());

        if (localOpt.isEmpty()){
            return new PartidoResDuplicateDTO("ERROR","Equipo local no encontrado");
        }
        if (visitantelOpt.isEmpty()){
            return new PartidoResDuplicateDTO("ERROR","Equipo visitante no encontrado");
        }

        TorneoEquipo local=localOpt.get();
        TorneoEquipo visitante=visitantelOpt.get();

        if (local.getIdTorneoEquipo().equals(visitante.getIdTorneoEquipo())) {
            return new PartidoResDuplicateDTO("ERROR", "Un equipo no puede jugar contra sí mismo");
        }

        // --- VALIDACIÓN DE ENFRENTAMIENTO PREVIO ---
        //enviar siempre false por defecto cuando se cree un partido
        boolean yaExiste = partidoRepository.existeEnfrentamiento(local, visitante, dto.getFase());
        if (yaExiste && !dto.isConfirmarDuplicado()) {
            return new PartidoResDuplicateDTO("WARNING_DUPLICATE",
    "Ya existe un partido entre estos equipos en la fase " + dto.getFase() + ". ¿Deseas duplicarlo?");
        }

        Partido nuevoPartido = new Partido();
        nuevoPartido.setLocal(local);
        nuevoPartido.setVisitante(visitante);
        nuevoPartido.setFechaPartido(dto.getFecha());
        nuevoPartido.setHoraPartido(dto.getHora());
        nuevoPartido.setNombreCancha(dto.getCancha());
        nuevoPartido.setFaseTorneo(dto.getFase());

        partidoRepository.save(nuevoPartido);
        return new PartidoResDuplicateDTO("SUCCESS", "Partido programado exitosamente.");
    }
}
