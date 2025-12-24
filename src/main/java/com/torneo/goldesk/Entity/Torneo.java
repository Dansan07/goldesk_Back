package com.torneo.goldesk.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "torneos")
public class Torneo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_torneo")
    private Integer idTorneo;

    @Column(name = "nombre_torneo")
    private String nombreTorneo;

    @Column(name = "valor_amarilla")
    private double valorAmarilla;

    @Column(name = "valor_azul")
    private double valorAzul;

    @Column(name = "valor_roja")
    private double valorRoja;

    @Column(name = "valor_arbitraje")
    private double valorArbitraje;

    @Column(name = "valor_inscripcion")
    private double valorInscripcion;

    @Column(name = "valor_balon_petos")
    private double valorBalonPetos;

    @Column(name = "partidos_inicial")
    private Integer partidosInicial;

    @Column(name = "activo")
    private boolean activo;

    //Relaciones con otras tablas
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cedula_organizador")
    private Organizador organizador;

    @ManyToMany
    @JoinTable(name = "torneo_equipos", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "id_torneo"),
            inverseJoinColumns = @JoinColumn(name = "id_equipo")
    )
    private List<Equipo> equipos = new ArrayList<>();

    public Torneo() {
    }

    //getters and setters


    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    public Organizador getOrganizador() {
        return organizador;
    }

    public void setOrganizador(Organizador organizador) {
        this.organizador = organizador;
    }

    public Integer getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(Integer idTorneo) {
        this.idTorneo = idTorneo;
    }

    public String getNombreTorneo() {
        return nombreTorneo;
    }

    public void setNombreTorneo(String nombreTorneo) {
        this.nombreTorneo = nombreTorneo;
    }

    public double getValorAmarilla() {
        return valorAmarilla;
    }

    public void setValorAmarilla(double valorAmarilla) {
        this.valorAmarilla = valorAmarilla;
    }

    public double getValorAzul() {
        return valorAzul;
    }

    public void setValorAzul(double valorAzul) {
        this.valorAzul = valorAzul;
    }

    public double getValorRoja() {
        return valorRoja;
    }

    public void setValorRoja(double valorRoja) {
        this.valorRoja = valorRoja;
    }

    public double getValorArbitraje() {
        return valorArbitraje;
    }

    public void setValorArbitraje(double valorArbitraje) {
        this.valorArbitraje = valorArbitraje;
    }

    public double getValorInscripcion() {
        return valorInscripcion;
    }

    public void setValorInscripcion(double valorInscripcion) {
        this.valorInscripcion = valorInscripcion;
    }

    public double getValorBalonPetos() {
        return valorBalonPetos;
    }

    public void setValorBalonPetos(double valorBalonPetos) {
        this.valorBalonPetos = valorBalonPetos;
    }

    public Integer getPartidosInicial() {
        return partidosInicial;
    }

    public void setPartidosInicial(Integer partidosInicial) {
        this.partidosInicial = partidosInicial;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
