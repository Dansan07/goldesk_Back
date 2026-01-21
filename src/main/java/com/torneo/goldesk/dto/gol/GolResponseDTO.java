package com.torneo.goldesk.dto.gol;

public class GolResponseDTO {

    private Integer idGol;
    private String periodoPartido;
    private String tiempoEvento;

    public GolResponseDTO(Integer idGol, String periodoPartido, String tiempoEvento) {
        this.idGol = idGol;
        this.periodoPartido = periodoPartido;
        this.tiempoEvento = tiempoEvento;
    }

    public Integer getIdGol() {
        return idGol;
    }

    public String getPeriodoPartido() {
        return periodoPartido;
    }

    public String getTiempoEvento() {
        return tiempoEvento;
    }
}
