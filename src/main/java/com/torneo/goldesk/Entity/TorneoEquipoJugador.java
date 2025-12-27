package com.torneo.goldesk.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "torneo_equipos_jugadores")
public class TorneoEquipoJugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_torneo_equipos_jugadores")
    private Integer idTorneoEquipoJugador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_torneo_equipos", nullable = false)
    private TorneoEquipo torneoEquipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jugador",nullable = false)
    private Jugador jugador;

    @Column(name = "jugador_activo")
    private Boolean activo=true;

    @Column(name = "fecha_inscripcion", insertable = false, updatable = false)
    private LocalDateTime fechaInscripcion;

    public TorneoEquipoJugador() {
    }

    public LocalDateTime getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDateTime fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public Boolean isActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public TorneoEquipo getTorneoEquipo() {
        return torneoEquipo;
    }

    public void setTorneoEquipo(TorneoEquipo torneoEquipo) {
        this.torneoEquipo = torneoEquipo;
    }

    public Integer getIdTorneoEquipoJugador() {
        return idTorneoEquipoJugador;
    }

    public void setIdTorneoEquipoJugador(Integer idTorneoEquipoJugador) {
        this.idTorneoEquipoJugador = idTorneoEquipoJugador;
    }
}
