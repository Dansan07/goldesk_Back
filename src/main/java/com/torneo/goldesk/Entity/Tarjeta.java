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
    private Double valorTarjeta; // Para el costo de la multa

    @Column(name = "motivo_tarjeta")
    private String motivoTarjeta;

    @ManyToOne
    @JoinColumn(name = "id_participacion")
    private ParticipacionJugador participacionJugador;

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

    public Double getValorTarjeta() {
        return valorTarjeta;
    }

    public void setValorTarjeta(Double valorTarjeta) {
        this.valorTarjeta = valorTarjeta;
    }

    public String getMotivoTarjeta() {
        return motivoTarjeta;
    }

    public void setMotivoTarjeta(String motivoTarjeta) {
        this.motivoTarjeta = motivoTarjeta;
    }

    public ParticipacionJugador getParticipacionJugador() {
        return participacionJugador;
    }

    public void setParticipacionJugador(ParticipacionJugador participacionJugador) {
        this.participacionJugador = participacionJugador;
    }
}
