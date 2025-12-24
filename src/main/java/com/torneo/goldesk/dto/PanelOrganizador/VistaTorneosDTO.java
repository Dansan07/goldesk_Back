package com.torneo.goldesk.dto.PanelOrganizador;

public class VistaTorneosDTO {
    private Integer idTorneo;
    private String nombreTorneo;

    public VistaTorneosDTO(Integer idTorneo, String nombreTorneo) {
        this.idTorneo = idTorneo;
        this.nombreTorneo = nombreTorneo;
    }

    public Integer getIdTorneo() {
        return idTorneo;
    }

    public String getNombreTorneo() {
        return nombreTorneo;
    }
}
