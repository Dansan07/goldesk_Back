package com.torneo.goldesk.dto.tarjeta;

public class TarjetaCreateDTO {

    private String tipoTarjeta;
    private Double valorTarjeta;
    private String motivoTarjeta;
    private Integer idPartido;
    private Integer idJugador;

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public Double getValorTarjeta() {
        return valorTarjeta;
    }

    public String getMotivoTarjeta() {
        return motivoTarjeta;
    }

    public Integer getIdPartido() {
        return idPartido;
    }

    public Integer getIdJugador() {
        return idJugador;
    }
}
