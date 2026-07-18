package com.torneo.goldesk.dto.registroPagos.inscripcion;

import java.time.LocalDateTime;

public class AbonoDetalleInscripcionDTO {

    private Integer idPagoInscripcion;
    private Double monto;
    private LocalDateTime fecha;


    public AbonoDetalleInscripcionDTO(Integer idPagoInscripcion, Double monto, LocalDateTime fecha) {
        this.idPagoInscripcion = idPagoInscripcion;
        this.monto = monto;
        this.fecha = fecha;
    }

    public Integer getIdPagoInscripcion() {
        return idPagoInscripcion;
    }

    public void setIdPagoInscripcion(Integer idPagoInscripcion) {
        this.idPagoInscripcion = idPagoInscripcion;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
