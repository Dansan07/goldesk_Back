package com.torneo.goldesk.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tarjetas")
public class Tarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarjeta")
    private Integer idTarjeta;

    @Column(name = "tipo_tarjeta")
    private String tipoTarjeta; // AMARILLA o ROJA

    @Column(name = "valor_tarjeta")
    private Integer valorTarjeta; // Para el costo de la multa

    @Column(name = "motivo_tarjeta")
    private String motivoTarjeta;

    @ManyToOne
    @JoinColumn(name = "id_partido")
    private Partido partido;

    @ManyToOne
    @JoinColumn(name = "id_torneo_equipos_jugadores")
    private TorneoEquipoJugador jugador;

    //contructor

    public Tarjeta() {
    }

    //getters and setters

    public Integer getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(Integer idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public Integer getValorTarjeta() {
        return valorTarjeta;
    }

    public void setValorTarjeta(Integer valorTarjeta) {
        this.valorTarjeta = valorTarjeta;
    }

    public String getMotivoTarjeta() {
        return motivoTarjeta;
    }

    public void setMotivoTarjeta(String motivoTarjeta) {
        this.motivoTarjeta = motivoTarjeta;
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
