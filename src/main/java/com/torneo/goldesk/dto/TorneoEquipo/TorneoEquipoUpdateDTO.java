package com.torneo.goldesk.dto.TorneoEquipo;

import com.torneo.goldesk.Entity.Equipo;
import com.torneo.goldesk.Entity.Torneo;

public class TorneoEquipoUpdateDTO {

    private Integer idTorneoEquipo;
    private String nombrePersonalizado;

    public Integer getIdTorneoEquipo() {
        return idTorneoEquipo;
    }

    public void setIdTorneoEquipo(Integer idTorneoEquipo) {
        this.idTorneoEquipo = idTorneoEquipo;
    }

    public String getNombrePersonalizado() {
        return nombrePersonalizado;
    }

    public void setNombrePersonalizado(String nombrePersonalizado) {
        this.nombrePersonalizado = nombrePersonalizado;
    }
}
