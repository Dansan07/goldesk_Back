package com.torneo.goldesk.dto.gol;

public class GolResponseDTO {

    private Integer idGol;
    private String nombreJugador;

    public GolResponseDTO(Integer idGol, String nombreJugador) {
        this.idGol = idGol;
        this.nombreJugador = nombreJugador;
    }

    public Integer getIdGol() {
        return idGol;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }
}
