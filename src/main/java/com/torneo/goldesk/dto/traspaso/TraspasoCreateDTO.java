package com.torneo.goldesk.dto.traspaso;

public class TraspasoCreateDTO {

    private Integer idJugador;
    private Integer idTorneoEquipoJugador;
    private Integer idTorneoEquipoSolicita; // El equipo que quiere al jugador
    private String asuntoTraspaso; // La razón o motivo

    public TraspasoCreateDTO(Integer idJugador, Integer idTorneoEquipoJugador, Integer idTorneoEquipoSolicita, String asuntoTraspaso) {
        this.idJugador = idJugador;
        this.idTorneoEquipoJugador = idTorneoEquipoJugador;
        this.idTorneoEquipoSolicita = idTorneoEquipoSolicita;
        this.asuntoTraspaso = asuntoTraspaso;
    }

    //getters and setters

    public Integer getIdJugador() {
        return idJugador;
    }

    public Integer getIdTorneoEquipoJugador() {
        return idTorneoEquipoJugador;
    }

    public Integer getIdTorneoEquipoSolicita() {
        return idTorneoEquipoSolicita;
    }

    public String getAsuntoTraspaso() {
        return asuntoTraspaso;
    }
}
