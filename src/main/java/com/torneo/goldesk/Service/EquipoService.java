package com.torneo.goldesk.Service;

import com.torneo.goldesk.Entity.Equipo;
import com.torneo.goldesk.Repository.EquipoRepository;
import com.torneo.goldesk.dto.equipo.EquipoCreateDTO;
import com.torneo.goldesk.dto.equipo.EquipoResponseDTO;
import com.torneo.goldesk.dto.equipo.EquipoUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipoService {

    private final EquipoRepository equipoRepository;

    public EquipoService (EquipoRepository equipoRepository){
        this.equipoRepository=equipoRepository;
    }

    public void recuperarEquipo(Integer id){
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Equipo no encontrado"));

        equipo.setActivo(true);

        equipoRepository.save(equipo);
    }

    public void eliminarEquipo(Integer id){
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Equipo no encontrado"));

        equipo.setActivo(false);

        equipoRepository.save(equipo);
    }

    public void actualizarEquipo(EquipoUpdateDTO dto){

        Equipo equipo = equipoRepository.findById(dto.getId())
                .orElseThrow(()-> new RuntimeException("Equipo no encontrado"));

        equipo.setNombreEquipo(dto.getNombreEquipo());
        equipo.setActivo(dto.getActivo());

        equipoRepository.save(equipo);
    }

    public EquipoResponseDTO crearEquipo(EquipoCreateDTO dto){

        Equipo equipo = new Equipo();
        equipo.setNombreEquipo(dto.getNombreEquipo());
        equipo.setCodigoEquipo(dto.getCodigoEquipo());
        equipo.setActivo(true);

        Equipo equipoGuardado = equipoRepository.save(equipo);

        return convertirADTO(equipoGuardado);
    }

    public List<EquipoResponseDTO> obtenerTodos(){
        return equipoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public List<EquipoResponseDTO> obtenerActivos(){
        return equipoRepository.findByActivoTrue()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }
    public List<EquipoResponseDTO> obtenerInactivos(){
        return equipoRepository.findByActivoFalse()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    private EquipoResponseDTO convertirADTO(Equipo e) {
        return new EquipoResponseDTO(
                e.getIdEquipo(),
                e.getNombreEquipo(),
                e.getCodigoEquipo(),
                e.getActivo()
        );
    }
}
