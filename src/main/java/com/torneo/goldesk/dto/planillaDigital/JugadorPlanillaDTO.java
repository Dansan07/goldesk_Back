package com.torneo.goldesk.dto.planillaDigital;

public class JugadorPlanillaDTO {

    public interface vista {
        Integer getIdReferencia(); // Debe coincidir con el alias en el SQL
        String getDorsal();
        String getNombreJugador();
        Integer getCantGoles();
        Integer getCantAmarillas();
        Integer getCantAzules();
        Integer getCantRojas();
    }

    // Si el partido no ha iniciado, este será el id_torneo_equipos_jugadores (idTEJ)
    // Si el partido ya inició, este será el id_participacion
    private Integer idReferencia;
    private String nombreJugador;
    private String dorsal;
    private Integer cantGoles=0;
    private Integer cantAmarillas=0;
    private Integer cantRojas=0;
    private Integer cantAzules=0;

    public JugadorPlanillaDTO(Integer idReferencia, String nombreJugador, String dorsal, Integer cantGoles, Integer cantAmarillas, Integer cantRojas, Integer cantAzules) {
        this.idReferencia = idReferencia;
        this.nombreJugador = nombreJugador;
        this.dorsal = dorsal;
        this.cantGoles = cantGoles;
        this.cantAmarillas = cantAmarillas;
        this.cantRojas = cantRojas;
        this.cantAzules = cantAzules;
    }

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

    public Integer getCantGoles() {
        return cantGoles;
    }

    public void setCantGoles(Integer cantGoles) {
        this.cantGoles = cantGoles;
    }

    public Integer getCantAmarillas() {
        return cantAmarillas;
    }

    public void setCantAmarillas(Integer cantAmarillas) {
        this.cantAmarillas = cantAmarillas;
    }

    public Integer getCantRojas() {
        return cantRojas;
    }

    public void setCantRojas(Integer cantRojas) {
        this.cantRojas = cantRojas;
    }

    public Integer getCantAzules() {
        return cantAzules;
    }

    public void setCantAzules(Integer cantAzules) {
        this.cantAzules = cantAzules;
    }
}
