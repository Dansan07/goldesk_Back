package com.torneo.goldesk.dto.TorneoEquipo;

import com.torneo.goldesk.Entity.Equipo;
import com.torneo.goldesk.Entity.Torneo;

public class TorneoEquipoUpdateDTO {

    private Integer idTorneoEquipo;
    private Torneo torneo;
    private Equipo equipo;
    private String nombrePersonalizado;
    private boolean equipoActivo;

    public Integer getIdTorneoEquipo() {
        return idTorneoEquipo;
    }

    public void setIdTorneoEquipo(Integer idTorneoEquipo) {
        this.idTorneoEquipo = idTorneoEquipo;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public String getNombrePersonalizado() {
        return nombrePersonalizado;
    }

    public void setNombrePersonalizado(String nombrePersonalizado) {
        this.nombrePersonalizado = nombrePersonalizado;
    }

    public boolean isEquipoActivo() {
        return equipoActivo;
    }

    public void setEquipoActivo(boolean equipoActivo) {
        this.equipoActivo = equipoActivo;
    }
}
