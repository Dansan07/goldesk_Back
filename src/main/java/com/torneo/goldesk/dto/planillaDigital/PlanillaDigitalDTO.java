package com.torneo.goldesk.dto.planillaDigital;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class PlanillaDigitalDTO{

    // Campos básicos del partido
    private Integer idPartido;
    private LocalDate fechaPartido;
    private LocalTime horaPartido;
    private String nombreCancha;
    private Integer golesLocal;
    private Integer golesVisitante;
    private Integer penalesLocal;
    private Integer penalesVisitante;
    private String faseTorneo;
    private String estado;

    // Información del equipo local
    private Integer idEquipoLocal;
    private String nombreEquipoLocal;
    private List<JugadorPlanillaDTO> jugadoresLocal;
    private Double pagoArbitrajeLocal;
    private Boolean arbPagadoLocal;

    // Información del equipo visitante
    private Integer idEquipoVisitante;
    private String nombreEquipoVisitante;
    private List<JugadorPlanillaDTO> jugadoresVisitante;
    private Double pagoArbitrajeVisitante;
    private Boolean arbPagadoVisitante;

    //constructor
    public PlanillaDigitalDTO(Integer idPartido, LocalDate fechaPartido, LocalTime horaPartido, String nombreCancha,
                              Integer golesLocal, Integer golesVisitante, Integer penalesLocal, Integer penalesVisitante,
                              String faseTorneo, String estado, Integer idEquipoLocal, String nombreEquipoLocal,
                              List<JugadorPlanillaDTO> jugadoresLocal, Double pagoArbitrajeLocal, Boolean arbPagadoLocal, Integer idEquipoVisitante,
                              String nombreEquipoVisitante, List<JugadorPlanillaDTO> jugadoresVisitante, Double pagoArbitrajeVisitante, Boolean arbPagadoVisitante) {
        this.idPartido = idPartido;
        this.fechaPartido = fechaPartido;
        this.horaPartido = horaPartido;
        this.nombreCancha = nombreCancha;
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.penalesLocal = penalesLocal;
        this.penalesVisitante = penalesVisitante;
        this.faseTorneo = faseTorneo;
        this.estado = estado;
        this.idEquipoLocal = idEquipoLocal;
        this.nombreEquipoLocal = nombreEquipoLocal;
        this.jugadoresLocal = jugadoresLocal;
        this.pagoArbitrajeLocal = pagoArbitrajeLocal;
        this.arbPagadoLocal = arbPagadoLocal;
        this.idEquipoVisitante = idEquipoVisitante;
        this.nombreEquipoVisitante = nombreEquipoVisitante;
        this.jugadoresVisitante = jugadoresVisitante;
        this.pagoArbitrajeVisitante = pagoArbitrajeVisitante;
        this.arbPagadoVisitante = arbPagadoVisitante;
    }

    //getters and setters


    public Boolean getArbPagadoLocal() {
        return arbPagadoLocal;
    }

    public void setArbPagadoLocal(Boolean arbPagadoLocal) {
        this.arbPagadoLocal = arbPagadoLocal;
    }

    public Boolean getArbPagadoVisitante() {
        return arbPagadoVisitante;
    }

    public void setArbPagadoVisitante(Boolean arbPagadoVisitante) {
        this.arbPagadoVisitante = arbPagadoVisitante;
    }

    public Double getPagoArbitrajeLocal() {
        return pagoArbitrajeLocal;
    }

    public void setPagoArbitrajeLocal(Double pagoArbitrajeLocal) {
        this.pagoArbitrajeLocal = pagoArbitrajeLocal;
    }

    public Double getPagoArbitrajeVisitante() {
        return pagoArbitrajeVisitante;
    }

    public void setPagoArbitrajeVisitante(Double pagoArbitrajeVisitante) {
        this.pagoArbitrajeVisitante = pagoArbitrajeVisitante;
    }

    public Integer getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(Integer idPartido) {
        this.idPartido = idPartido;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getIdEquipoLocal() {
        return idEquipoLocal;
    }

    public void setIdEquipoLocal(Integer idEquipoLocal) {
        this.idEquipoLocal = idEquipoLocal;
    }

    public String getNombreEquipoLocal() {
        return nombreEquipoLocal;
    }

    public void setNombreEquipoLocal(String nombreEquipoLocal) {
        this.nombreEquipoLocal = nombreEquipoLocal;
    }

    public List<JugadorPlanillaDTO> getJugadoresLocal() {
        return jugadoresLocal;
    }

    public void setJugadoresLocal(List<JugadorPlanillaDTO> jugadoresLocal) {
        this.jugadoresLocal = jugadoresLocal;
    }

    public Integer getIdEquipoVisitante() {
        return idEquipoVisitante;
    }

    public void setIdEquipoVisitante(Integer idEquipoVisitante) {
        this.idEquipoVisitante = idEquipoVisitante;
    }

    public String getNombreEquipoVisitante() {
        return nombreEquipoVisitante;
    }

    public void setNombreEquipoVisitante(String nombreEquipoVisitante) {
        this.nombreEquipoVisitante = nombreEquipoVisitante;
    }

    public List<JugadorPlanillaDTO> getJugadoresVisitante() {
        return jugadoresVisitante;
    }

    public void setJugadoresVisitante(List<JugadorPlanillaDTO> jugadoresVisitante) {
        this.jugadoresVisitante = jugadoresVisitante;
    }
}
