package com.torneo.goldesk.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "accesos_externos")
public class AccesosExternos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_acceso")
    private Integer idAcceso;

    @Column(name = "codigo_acceso", unique = true, nullable = false)
    private String codigoAcceso;

    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol; // Aquí vendrá el objeto Rol (ID y Nombre como 'DELEGADO')

    @ManyToOne
    @JoinColumn(name = "id_equipo")
    private Equipo equipo;

    @ManyToOne
    @JoinColumn(name = "cedula_organizador")
    private Organizador organizador;       // "ORGANIZADOR", "EQUIPO", "INVITADO"

    @Column(name = "activo")
    private Boolean activo = true;

    //constructor
    public AccesosExternos() {
    }

    //getters and setter

    public Integer getIdAcceso() {
        return idAcceso;
    }

    public void setIdAcceso(Integer idAcceso) {
        this.idAcceso = idAcceso;
    }

    public String getCodigoAcceso() {
        return codigoAcceso;
    }

    public void setCodigoAcceso(String codigoAcceso) {
        this.codigoAcceso = codigoAcceso;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Organizador getOrganizador() {
        return organizador;
    }

    public void setOrganizador(Organizador organizador) {
        this.organizador = organizador;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
