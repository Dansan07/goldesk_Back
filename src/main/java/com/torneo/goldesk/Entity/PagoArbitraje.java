package com.torneo.goldesk.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pagos_arbitraje")
public class PagoArbitraje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago_arbitraje")
    private Integer idPagoArbitraje;

    @ManyToOne
    @JoinColumn(name = "id_partido")
    private Partido partido;

    @ManyToOne
    @JoinColumn(name = "id_torneo_equipos")
    private TorneoEquipo torneoEquipo;

    @Column(name = "monto")
    private Double monto; // numeric en Postgres

    @Column(name = "fecha_pago", insertable = false)
    private LocalDateTime fechaPago; // timestamp without time zone

    @Column(name = "observacion")
    private String observacion; // text en Postgres

    //constructor
    public PagoArbitraje() {
    }

    //getters and setters

    public Integer getIdPagoArbitraje() {
        return idPagoArbitraje;
    }

    public void setIdPagoArbitraje(Integer idPagoArbitraje) {
        this.idPagoArbitraje = idPagoArbitraje;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public TorneoEquipo getTorneoEquipo() {
        return torneoEquipo;
    }

    public void setTorneoEquipo(TorneoEquipo torneoEquipo) {
        this.torneoEquipo = torneoEquipo;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
