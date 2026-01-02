package com.torneo.goldesk.dto.registroPagos.arbitraje;

import java.time.LocalDateTime;

public class PagoArbitrajeResponseDTO {

    private Integer idPagoArbitraje;
    private Integer idPartido;
    private Integer idTorneoEquipo;
    private Double monto;
    private LocalDateTime fechaPago;
    private String observacion;

    public PagoArbitrajeResponseDTO(Integer idPagoArbitraje, Integer idPartido, Integer idTorneoEquipo, Double monto, LocalDateTime fechaPago, String observacion) {
        this.idPagoArbitraje = idPagoArbitraje;
        this.idPartido = idPartido;
        this.idTorneoEquipo = idTorneoEquipo;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.observacion = observacion;
    }

    public Integer getIdPagoArbitraje() {
        return idPagoArbitraje;
    }

    public void setIdPagoArbitraje(Integer idPagoArbitraje) {
        this.idPagoArbitraje = idPagoArbitraje;
    }

    public Integer getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(Integer idPartido) {
        this.idPartido = idPartido;
    }

    public Integer getIdTorneoEquipo() {
        return idTorneoEquipo;
    }

    public void setIdTorneoEquipo(Integer idTorneoEquipo) {
        this.idTorneoEquipo = idTorneoEquipo;
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
