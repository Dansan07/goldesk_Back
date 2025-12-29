package com.torneo.goldesk.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "goles")
public class Gol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gol")
    private Integer idGol;

    @ManyToOne
    @JoinColumn(name = "id_torneo_equipos_jugadores")
    private TorneoEquipoJugador jugador; // El jugador que hizo el gol y su equipo

    @ManyToOne
    @JoinColumn(name = "id_partido")
    private Partido partido;

    //constructor
    public Gol() {
    }

    //getters and setters

    public Integer getIdGol() {
        return idGol;
    }

    public void setIdGol(Integer idGol) {
        this.idGol = idGol;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public TorneoEquipoJugador getJugador() {
        return jugador;
    }

    public void setJugador(TorneoEquipoJugador jugador) {
        this.jugador = jugador;
    }
}
