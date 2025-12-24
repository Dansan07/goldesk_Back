package com.torneo.goldesk.dto.TorneoEquipo;

import com.torneo.goldesk.Entity.Equipo;
import com.torneo.goldesk.Entity.Torneo;

public class TorneoEquipoCreateDTO {

    private Integer idTorneoEquipo;
    private Torneo torneo;
    private Equipo equipo;
    private String nombrePersonalizado;
    private boolean equipoActivo;

    public Integer getIdTorneoEquipo() {
        return idTorneoEquipo;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public String getNombrePersonalizado() {
        return nombrePersonalizado;
    }

    public boolean isEquipoActivo() {
        return equipoActivo;
    }
}
