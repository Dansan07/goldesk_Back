package com.torneo.goldesk.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pagos_inscripcion")
public class PagoInscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago_inscripcion")
    private Integer idPagoInscripcion;

    @ManyToOne
    @JoinColumn(name = "id_torneo_equipos")
    private TorneoEquipo torneoEquipo;

    @Column(name = "monto")
    private Double monto;

    @Column(name = "fecha_pago", insertable = false, updatable = false)
    private LocalDateTime fechaPago;

    //constructor

    public PagoInscripcion() {
    }

    //getters and setters

    public Integer getIdPagoInscripcion() {
        return idPagoInscripcion;
    }

    public void setIdPagoInscripcion(Integer idPagoInscripcion) {
        this.idPagoInscripcion = idPagoInscripcion;
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
}
