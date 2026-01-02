package com.torneo.goldesk.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "participaciones_jugadores")
public class ParticipacionJugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idParticipacion;

    @ManyToOne
    @JoinColumn(name = "id_partido")
    private Partido partido;

    @ManyToOne
    @JoinColumn(name = "id_torneo_equipos_jugadores")
    private TorneoEquipoJugador torneoEquipoJugador;

    @Column(name = "dorsal")
    private String dorsalJugador;

    //contructor
    public ParticipacionJugador() {
    }

    //getters and setters
    public Integer getIdParticipacion() {
        return idParticipacion;
    }

    public void setIdParticipacion(Integer idParticipacion) {
        this.idParticipacion = idParticipacion;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public TorneoEquipoJugador getTorneoEquipoJugador() {
        return torneoEquipoJugador;
    }

    public void setTorneoEquipoJugador(TorneoEquipoJugador torneoEquipoJugador) {
        this.torneoEquipoJugador = torneoEquipoJugador;
    }

    public String getDorsalJugador() {
        return dorsalJugador;
    }

    public void setDorsalJugador(String dorsalJugador) {
        this.dorsalJugador = dorsalJugador;
    }
}
