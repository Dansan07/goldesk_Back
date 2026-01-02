package com.torneo.goldesk.dto.registroPagos.inscripcion;

import java.time.LocalDateTime;

public class AbonoDetalleDTO {

    private Integer idAbonoInscripcion;
    private Double monto;
    private LocalDateTime fecha;


    public AbonoDetalleDTO(Integer idAbonoInscripcion, Double monto, LocalDateTime fecha) {
        this.idAbonoInscripcion = idAbonoInscripcion;
        this.monto = monto;
        this.fecha = fecha;
    }

    public Integer getIdAbonoInscripcion() {
        return idAbonoInscripcion;
    }

    public void setIdAbonoInscripcion(Integer idAbonoInscripcion) {
        this.idAbonoInscripcion = idAbonoInscripcion;
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
