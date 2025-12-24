package com.torneo.goldesk.dto.PanelOrganizador;

import com.torneo.goldesk.dto.TorneoEquipo.TorneoEquipoResponseDTO;

import java.util.List;

public class VistaTorneoEquiposDTO {

    private Integer idTorneo;
    private String nombreTorneo;
    private List<String> nombresEquipos;
    //private List<TorneoEquipoResponseDTO> equiposActivos;

    public VistaTorneoEquiposDTO(Integer idTorneo, String nombreTorneo, List<String> nombresEquipos) {
        this.idTorneo = idTorneo;
        this.nombreTorneo = nombreTorneo;
        this.nombresEquipos= nombresEquipos;
    }

    //GETTERS AND SETTERS

    public Integer getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(Integer idTorneo) {
        this.idTorneo = idTorneo;
    }

    public String getNombreTorneo() {
        return nombreTorneo;
    }

    public void setNombreTorneo(String nombreTorneo) {
        this.nombreTorneo = nombreTorneo;
    }

    public List<String> getNombresEquipos() {
        return nombresEquipos;
    }

    public void setNombresEquipos(List<String> nombresEquipos) {
        this.nombresEquipos = nombresEquipos;
    }
}
