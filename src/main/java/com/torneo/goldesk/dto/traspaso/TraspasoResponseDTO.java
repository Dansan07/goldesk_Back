package com.torneo.goldesk.dto.traspaso;

import java.time.LocalDateTime;

public class TraspasoResponseDTO {

    private Integer idTraspaso;
    private String nombreJugador;
    private String cedulaJugador;
    private String equipoOrigen;
    private String equipoDestino;
    private String asunto;
    private String estado;
    private LocalDateTime fechaSolicitud;

    //constructor

    public TraspasoResponseDTO(Integer idTraspaso, String nombreJugador, String cedulaJugador, String equipoOrigen, String equipoDestino, String asunto, String estado, LocalDateTime fechaSolicitud) {
        this.idTraspaso = idTraspaso;
        this.nombreJugador = nombreJugador;
        this.cedulaJugador = cedulaJugador;
        this.equipoOrigen = equipoOrigen;
        this.equipoDestino = equipoDestino;
        this.asunto = asunto;
        this.estado = estado;
        this.fechaSolicitud = fechaSolicitud;
    }

    //getters and setters

    public Integer getIdTraspaso() {
        return idTraspaso;
    }

    public void setIdTraspaso(Integer idTraspaso) {
        this.idTraspaso = idTraspaso;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public String getCedulaJugador() {
        return cedulaJugador;
    }

    public void setCedulaJugador(String cedulaJugador) {
        this.cedulaJugador = cedulaJugador;
    }

    public String getEquipoOrigen() {
        return equipoOrigen;
    }

    public void setEquipoOrigen(String equipoOrigen) {
        this.equipoOrigen = equipoOrigen;
    }

    public String getEquipoDestino() {
        return equipoDestino;
    }

    public void setEquipoDestino(String equipoDestino) {
        this.equipoDestino = equipoDestino;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
}
