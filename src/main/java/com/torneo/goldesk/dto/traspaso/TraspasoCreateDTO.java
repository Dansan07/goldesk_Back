package com.torneo.goldesk.dto.traspaso;

public class TraspasoCreateDTO {

    private Integer idJugador;
    private Integer idTorneoEquipoSolicita; // El equipo que quiere al jugador
    private String asuntoTraspaso; // La razón o motivo

    //getters and setters

    public Integer getIdJugador() {
        return idJugador;
    }

    public Integer getIdTorneoEquipoSolicita() {
        return idTorneoEquipoSolicita;
    }

    public String getAsuntoTraspaso() {
        return asuntoTraspaso;
    }
}
