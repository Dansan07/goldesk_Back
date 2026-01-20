package com.torneo.goldesk.Service;

import com.torneo.goldesk.Entity.Equipo;
import com.torneo.goldesk.Entity.Torneo;
import com.torneo.goldesk.Entity.TorneoEquipo;
import com.torneo.goldesk.Repository.EquipoRepository;
import com.torneo.goldesk.Repository.TorneoEquipoRepository;
import com.torneo.goldesk.Repository.TorneoRepository;
import com.torneo.goldesk.dto.TorneoEquipo.TorneoEquipoCreateDTO;
import com.torneo.goldesk.dto.TorneoEquipo.TorneoEquipoResponseDTO;
import com.torneo.goldesk.dto.TorneoEquipo.TorneoEquipoUpdateDTO;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class TorneoEquipoService {

    private final TorneoRepository torneoRepository;
    private final EquipoRepository equipoRepository;
    private final TorneoEquipoRepository torneoEquipoRepository;


    public TorneoEquipoService(TorneoRepository torneoRepository, EquipoRepository equipoRepository, TorneoEquipoRepository torneoEquipoRepository) {
        this.torneoRepository = torneoRepository;
        this.equipoRepository = equipoRepository;
        this.torneoEquipoRepository = torneoEquipoRepository;
    }

    //lista Equipos según al torneo que pertenece si están activos
    public List<Map<String, Object>> listarNombresEquiposPorTorneo(Integer idTorneo) {
        return torneoEquipoRepository.findByTorneo_IdTorneoAndEquipoActivoTrue(idTorneo)
                .stream()
                .map(te -> Map.<String, Object>of(
                        "idTorneoEquipo",te.getIdTorneoEquipo(),
                        "nombreEquipo",te.getNombrePersonalizado()==null?
                                te.getEquipo().getNombreEquipo():
                                te.getNombrePersonalizado()))
                .toList();
    }

    //lista Equipos según al torneo que pertenece, lista todos los que existe
    public List<TorneoEquipoResponseDTO> listarEquiposPorTorneo(Integer idTorneo) {
        return torneoEquipoRepository.findByTorneo_IdTorneo(idTorneo) // Debes crear este metodo en el Repository
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public void actualizarNombrePersonalizado(TorneoEquipoUpdateDTO dto) {
        // Es buena práctica validar que los IDs vengan en el DTO para evitar NullPointerException
        if (dto.getTorneo() == null || dto.getEquipo() == null) {
            throw new RuntimeException("Los datos del Torneo y Equipo son obligatorios para la actualización");
        }
        // Buscamos la fila exacta que coincide con ambos IDs
        TorneoEquipo inscripcion = torneoEquipoRepository
                .findByTorneo_IdTorneoAndEquipo_IdEquipo(dto.getTorneo().getIdTorneo(), dto.getEquipo().getIdEquipo())
                .orElseThrow(() -> new RuntimeException("El equipo no está inscrito en este torneo"));

        // Actualizamos el dato
        inscripcion.setNombrePersonalizado(dto.getNombrePersonalizado());

        // Guardamos
        torneoEquipoRepository.save(inscripcion);
    }

    public void inscribirEquipo(TorneoEquipoCreateDTO dto) {
        Integer idTorneo=dto.getTorneo().getIdTorneo();
        Integer idEquipo=dto.getEquipo().getIdEquipo();
        // 1. Buscar el torneo
        Torneo torneo = torneoRepository.findById(idTorneo)
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado"));

        // 2. Buscar el equipo
        Equipo equipo = equipoRepository.findById(idEquipo)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        // Validar si ya existe la inscripción antes de hacer nada
        boolean yaInscrito = torneoEquipoRepository
                .findByTorneo_IdTorneoAndEquipo_IdEquipo(idTorneo, idEquipo)
                .isPresent();

        if (yaInscrito) {
            throw new RuntimeException("Este equipo ya se encuentra inscrito en el torneo");
        }

        // 3. Unirlos
        TorneoEquipo torneoEquipo = new TorneoEquipo();
        torneoEquipo.setTorneo(torneo);
        torneoEquipo.setEquipo(equipo);
        torneoEquipo.setNombrePersonalizado(dto.getNombrePersonalizado() != null ?
                dto.getNombrePersonalizado() : equipo.getNombreEquipo());
        torneoEquipo.setEquipoActivo(true);

        // 4. Guardar el torneo (esto actualiza la tabla intermedia automáticamente)
        torneoEquipoRepository.save(torneoEquipo);
    }
    private TorneoEquipoResponseDTO convertirADTO(TorneoEquipo te) {
        return new TorneoEquipoResponseDTO(
                te.getIdTorneoEquipo(),
                te.getTorneo().getIdTorneo(),
                te.getTorneo().getNombreTorneo(),
                te.getEquipo().getIdEquipo(),
                te.getEquipo().getNombreEquipo(),
                te.getNombrePersonalizado(),
                te.isEquipoActivo()
        );
    }
}
