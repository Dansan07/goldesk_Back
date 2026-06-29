package com.torneo.goldesk.Service;

import com.torneo.goldesk.Entity.Organizador;
import com.torneo.goldesk.Entity.Torneo;
import com.torneo.goldesk.Exception.ResourceNotFoundException;
import com.torneo.goldesk.Repository.OrganizadorRepository;
import com.torneo.goldesk.Repository.TorneoRepository;
import com.torneo.goldesk.dto.torneo.TorneoCreateDTO;
import com.torneo.goldesk.dto.torneo.TorneoResponseDTO;
import com.torneo.goldesk.dto.torneo.TorneoUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TorneoService {

    private final TorneoRepository torneoRepository;
    private final OrganizadorRepository organizadorRepository;

    public TorneoService(TorneoRepository torneoRepository, OrganizadorRepository organizadorRepository) {
        this.torneoRepository = torneoRepository;
        this.organizadorRepository = organizadorRepository;
    }

    public List<String> buscarCategoriasExistentes(String cedulaOrg){
        if (cedulaOrg == null || cedulaOrg.trim().isEmpty()) {
            throw new IllegalArgumentException("La cédula del organizador no se encontró.");
        }
        return torneoRepository
                .findCategorias(cedulaOrg);
    }

    public TorneoResponseDTO obtenerTorneoUnico(Integer idTorneo){
        Torneo torneo = torneoRepository.findById(idTorneo)
                .orElseThrow(() -> new ResourceNotFoundException("Torneo no encontrado"));
        return convertirADTO(torneo);
    }

    public void recuperarTorneo(Integer id){
        Torneo torneo = torneoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado"));

        torneo.setActivo(true);

        torneoRepository.save(torneo);
    }

    public void eliminarTorneo(Integer id){
        Torneo torneo = torneoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Torneo no encontrado"));

        torneo.setActivo(false);

        torneoRepository.save(torneo);
    }

    public void actualizarTorneo(TorneoUpdateDTO dto){

        if (torneoRepository.existsByOrganizador_CedulaOrgAndNombreTorneo(dto.getCedulaOrganizador(),dto.getNombreTorneo())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya tienes un torneo registrado con ese nombre");
        }

        Torneo torneo = torneoRepository.findById(dto.getIdTorneo())
                .orElseThrow(()-> new RuntimeException("Torneo no encontrado"));

        torneo.setNombreTorneo(dto.getNombreTorneo());
        torneo.setValorAmarilla(dto.getValorAmarilla());
        torneo.setValorAzul(dto.getValorAzul());
        torneo.setValorRoja(dto.getValorRoja());
        torneo.setValorArbitraje(dto.getValorArbitraje());
        torneo.setValorInscripcion(dto.getValorInscripcion());
        torneo.setValorBalonPetos(dto.getValorBalonPetos());
        torneo.setPartidosInicial(dto.getPartidosInicial());
        torneo.setCategoriaTorneo(dto.getCategoriaTorneo());

        torneoRepository.save(torneo);
    }

    public void crearTorneo(TorneoCreateDTO dto){

        if (torneoRepository.existsByOrganizador_CedulaOrgAndNombreTorneo(dto.getCedulaOrganizador(),dto.getNombreTorneo())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya tienes un torneo registrado con ese nombre");
        }

        Organizador organizador = organizadorRepository.findById(dto.getCedulaOrganizador())
                .orElseThrow(()-> new RuntimeException("Organizador no Encontrado"));

        Torneo torneo = new Torneo();
        torneo.setNombreTorneo(dto.getNombreTorneo());
        torneo.setValorAmarilla(dto.getValorAmarilla());
        torneo.setValorAzul(dto.getValorAzul());
        torneo.setValorRoja(dto.getValorRoja());
        torneo.setValorArbitraje(dto.getValorArbitraje());
        torneo.setValorInscripcion(dto.getValorInscripcion());
        torneo.setValorBalonPetos(dto.getValorBalonPetos());
        torneo.setPartidosInicial(dto.getPartidosInicial());
        torneo.setActivo(true);
        torneo.setOrganizador(organizador);
        torneo.setCategoriaTorneo(dto.getCategoriaTorneo());

        torneoRepository.save(torneo);
    }

    public List<TorneoResponseDTO> obtenerTodos(){
        return torneoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public List<TorneoResponseDTO> obtenerActivos(){
        return torneoRepository.findByActivoTrue()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }
    public List<TorneoResponseDTO> obtenerInactivos(){
        return torneoRepository.findByActivoFalse()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }
    private TorneoResponseDTO convertirADTO(Torneo t) {
        return new TorneoResponseDTO(
                t.getIdTorneo(),
                t.getOrganizador().getCedulaOrg(), // Es mejor sacarlo de la relación
                t.getNombreTorneo(),
                t.getValorAmarilla(),
                t.getValorAzul(),
                t.getValorRoja(),
                t.getValorArbitraje(),
                t.getValorInscripcion(),
                t.getValorBalonPetos(),
                t.getPartidosInicial(),
                t.getCategoriaTorneo(),
                t.getActivo()
        );
    }
}
