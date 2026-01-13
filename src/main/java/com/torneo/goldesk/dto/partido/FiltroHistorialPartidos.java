package com.torneo.goldesk.dto.partido;

import java.time.LocalDate;

public class FiltroHistorialPartidos {

    private Integer idTorneo;
    private String nombreEquipo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public FiltroHistorialPartidos() {
    }

    public void setIdTorneo(Integer idTorneo) {
        this.idTorneo = idTorneo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public Integer getIdTorneo() {
        return idTorneo;
    }
}
