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

    @Column(name = "periodo_partido")
    private String periodoPartido;

    @Column(name = "tiempo_evento")
    private String tiempoEvento;

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

    public String getPeriodoPartido() {
        return periodoPartido;
    }

    public void setPeriodoPartido(String periodoPartido) {
        this.periodoPartido = periodoPartido;
    }

    public String getTiempoEvento() {
        return tiempoEvento;
    }

    public void setTiempoEvento(String tiempoEvento) {
        this.tiempoEvento = tiempoEvento;
    }
}
