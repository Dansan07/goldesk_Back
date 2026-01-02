package com.torneo.goldesk.dto.registroPagos.arbitraje;

public class PagoArbitrajeUpdateDTO {

    private Integer idPagoArbitraje;
    private Double monto;
    private String observacion;

    public Integer getIdPagoArbitraje() {
        return idPagoArbitraje;
    }

    public Double getMonto() {
        return monto;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setIdPagoArbitraje(Integer idPagoArbitraje) {
        this.idPagoArbitraje = idPagoArbitraje;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
