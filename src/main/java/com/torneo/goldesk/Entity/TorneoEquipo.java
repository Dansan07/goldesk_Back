package com.torneo.goldesk.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "torneo_equipos")
public class TorneoEquipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_torneo_equipos")
    private Integer idTorneoEquipo;

    @ManyToOne
    @JoinColumn(name = "id_torneo")
    private Torneo torneo;

    @ManyToOne
    @JoinColumn(name = "id_equipo")
    private Equipo equipo;

    @Column(name = "nombre_equipo_personalizado")
    private String nombrePersonalizado;

    @Column(name = "equipo_activo")
    private boolean equipoActivo;

    public TorneoEquipo() {
    }

    //getters and setters

    public Integer getIdTorneoEquipo() {
        return idTorneoEquipo;
    }

    public void setIdTorneoEquipo(Integer idTorneoEquipo) {
        this.idTorneoEquipo = idTorneoEquipo;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public String getNombrePersonalizado() {
        return nombrePersonalizado;
    }

    public void setNombrePersonalizado(String nombrePersonalizado) {
        this.nombrePersonalizado = nombrePersonalizado;
    }

    public boolean isEquipoActivo() {
        return equipoActivo;
    }

    public void setEquipoActivo(boolean equipoActivo) {
        this.equipoActivo = equipoActivo;
    }
}
