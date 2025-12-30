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
    @JoinColumn(name = "id_participacion")
    private ParticipacionJugador participacionJugador; // El participacionJugador que hizo el gol y su equipo

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

    public ParticipacionJugador getParticipacionJugador() {
        return participacionJugador;
    }

    public void setParticipacionJugador(ParticipacionJugador participacionJugador) {
        this.participacionJugador = participacionJugador;
    }
}
