package com.torneo.goldesk.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "partidos")
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_partido")
    private Integer idPartido;

    @ManyToOne
    @JoinColumn(name = "id_equipo_local")
    private TorneoEquipo local;

    @ManyToOne
    @JoinColumn(name = "id_equipo_visitante")
    private TorneoEquipo visitante;

    @Column(name = "fecha_partido")
    private LocalDate fechaPartido;

    @Column(name = "hora_partido")
    private LocalTime horaPartido;

    @Column(name = "nombre_cancha")
    private String nombreCancha;

    @Column(name = "goles_local")
    private Integer golesLocal = 0;

    @Column(name = "goles_visitante")
    private Integer golesVisitante = 0;

    @Column(name = "penales_local")
    private Integer penalesLocal = 0;

    @Column(name = "penales_visitante")
    private Integer penalesVisitante = 0;

    @Column(name = "fase_torneo")
    private String faseTorneo;

    //contructor

    public Partido() {
    }

    //getters and setters

    public Integer getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(Integer idPartido) {
        this.idPartido = idPartido;
    }

    public TorneoEquipo getLocal() {
        return local;
    }

    public void setLocal(TorneoEquipo local) {
        this.local = local;
    }

    public TorneoEquipo getVisitante() {
        return visitante;
    }

    public void setVisitante(TorneoEquipo visitante) {
        this.visitante = visitante;
    }

    public LocalDate getFechaPartido() {
        return fechaPartido;
    }

    public void setFechaPartido(LocalDate fechaPartido) {
        this.fechaPartido = fechaPartido;
    }

    public LocalTime getHoraPartido() {
        return horaPartido;
    }

    public void setHoraPartido(LocalTime horaPartido) {
        this.horaPartido = horaPartido;
    }

    public String getNombreCancha() {
        return nombreCancha;
    }

    public void setNombreCancha(String nombreCancha) {
        this.nombreCancha = nombreCancha;
    }

    public Integer getGolesLocal() {
        return golesLocal;
    }

    public void setGolesLocal(Integer golesLocal) {
        this.golesLocal = golesLocal;
    }

    public Integer getGolesVisitante() {
        return golesVisitante;
    }

    public void setGolesVisitante(Integer golesVisitante) {
        this.golesVisitante = golesVisitante;
    }

    public Integer getPenalesLocal() {
        return penalesLocal;
    }

    public void setPenalesLocal(Integer penalesLocal) {
        this.penalesLocal = penalesLocal;
    }

    public Integer getPenalesVisitante() {
        return penalesVisitante;
    }

    public void setPenalesVisitante(Integer penalesVisitante) {
        this.penalesVisitante = penalesVisitante;
    }

    public String getFaseTorneo() {
        return faseTorneo;
    }

    public void setFaseTorneo(String faseTorneo) {
        this.faseTorneo = faseTorneo;
    }
}
