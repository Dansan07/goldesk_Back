package com.torneo.goldesk.dto.registroPagos.arbitraje;

public class PagoArbitrajeCreateDTO {

    private Integer idPartido;
    private Integer idTorneoEquipo;
    private Double monto;
    private String observacion;

    public Integer getIdPartido() {
        return idPartido;
    }

    public Integer getIdTorneoEquipo() {
        return idTorneoEquipo;
    }

    public Double getMonto() {
        return monto;
    }

    public String getObservacion() {
        return observacion;
    }
}
