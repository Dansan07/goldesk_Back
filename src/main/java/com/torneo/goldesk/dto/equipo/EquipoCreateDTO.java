package com.torneo.goldesk.dto.equipo;

public class EquipoCreateDTO {

    private String nombreEquipo;
    private String codigoEquipo;
    private Boolean activo;

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
