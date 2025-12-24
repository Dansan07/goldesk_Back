package com.torneo.goldesk.dto.equipo;

public class EquipoResponseDTO {

    private Integer idEquipo;
    private String nombreEquipo;
    private String codigoEquipo;
    private Boolean activo;

    public EquipoResponseDTO(Integer idEquipo, String nombreEquipo, String codigoEquipo, Boolean activo) {
        this.idEquipo = idEquipo;
        this.nombreEquipo = nombreEquipo;
        this.codigoEquipo = codigoEquipo;
        this.activo = activo;
    }

    public Integer getIdEquipo() {
        return idEquipo;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public String getCodigoEquipo() {
        return codigoEquipo;
    }

    public Boolean getActivo() {
        return activo;
    }
}
