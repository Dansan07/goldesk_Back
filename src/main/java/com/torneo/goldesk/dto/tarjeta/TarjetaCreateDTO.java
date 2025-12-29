package com.torneo.goldesk.dto.tarjeta;

public class TarjetaCreateDTO {

    private String tipoTarjeta;
    private Integer valorTarjeta;
    private String motivoTarjeta;
    private Integer idPartido;
    private Integer idJugador;

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public Integer getValorTarjeta() {
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
