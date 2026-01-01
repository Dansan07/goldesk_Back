package com.torneo.goldesk.dto.planillaDigital;

public class JugadorPlanillaDTO {

    // Si el partido no ha iniciado, este será el id_torneo_equipos_jugadores (idTEJ)
    // Si el partido ya inició, este será el id_participacion
    private Integer idReferencia;
    private String nombreJugador;
    private String dorsal;

    public JugadorPlanillaDTO(Integer idReferencia, String nombreJugador, String dorsal) {
        this.idReferencia = idReferencia;
        this.nombreJugador = nombreJugador;
        this.dorsal = dorsal;
    }

    public JugadorPlanillaDTO(Integer idReferencia, String nombreJugador) {
        this.idReferencia = idReferencia;
        this.nombreJugador = nombreJugador;
    }

    public Integer getIdReferencia() {
        return idReferencia;
    }

    public void setIdReferencia(Integer idReferencia) {
        this.idReferencia = idReferencia;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public String getDorsal() {
        return dorsal;
    }

    public void setDorsal(String dorsal) {
        this.dorsal = dorsal;
    }
}
