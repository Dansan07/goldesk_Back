package com.torneo.goldesk.dto.partido;

import java.time.LocalDate;
import java.time.LocalTime;

public class PartidoHistorialResponseDTO {

    private Integer idPartido;
    private Integer idLocal;
    private String nombreLocal;
    private Integer idVisitante;
    private String nombreVisitante;
    private LocalDate fechaPartido;
    private LocalTime horaPartido;
    private String nombreCancha;
    private Integer golesLocal = 0;
    private Integer golesVisitante = 0;
    private Integer penalesLocal = 0;
    private Integer penalesVisitante = 0;
    private String faseTorneo;
    private String estado;

    public PartidoHistorialResponseDTO(Integer idPartido, Integer idLocal, String nombreLocal, Integer idVisitante, String nombreVisitante, LocalDate fechaPartido, LocalTime horaPartido, String nombreCancha, Integer golesLocal, Integer golesVisitante, Integer penalesLocal, Integer penalesVisitante, String faseTorneo, String estado) {
        this.idPartido = idPartido;
        this.idLocal = idLocal;
        this.nombreLocal = nombreLocal;
        this.idVisitante = idVisitante;
        this.nombreVisitante = nombreVisitante;
        this.fechaPartido = fechaPartido;
        this.horaPartido = horaPartido;
        this.nombreCancha = nombreCancha;
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.penalesLocal = penalesLocal;
        this.penalesVisitante = penalesVisitante;
        this.faseTorneo = faseTorneo;
        this.estado = estado;
    }

    public Integer getIdPartido() {
        return idPartido;
    }

    public Integer getIdLocal() {
        return idLocal;
    }

    public String getNombreLocal() {
        return nombreLocal;
    }

    public Integer getIdVisitante() {
        return idVisitante;
    }

    public String getNombreVisitante() {
        return nombreVisitante;
    }

    public LocalDate getFechaPartido() {
        return fechaPartido;
    }

    public LocalTime getHoraPartido() {
        return horaPartido;
    }

    public String getNombreCancha() {
        return nombreCancha;
    }

    public Integer getGolesLocal() {
        return golesLocal;
    }

    public Integer getGolesVisitante() {
        return golesVisitante;
    }

    public Integer getPenalesLocal() {
        return penalesLocal;
    }

    public Integer getPenalesVisitante() {
        return penalesVisitante;
    }

    public String getFaseTorneo() {
        return faseTorneo;
    }

    public String getEstado() {
        return estado;
    }
}
