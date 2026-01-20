package com.torneo.goldesk.dto.tarjeta;

public class TarjetaCreateDTO {

    private Integer idParticipacion;
    private Double valorTarjeta;
    private String tipoTarjeta;
    private String motivoTarjeta;
    private String periodoPartido;
    private String tiempoEvento;

    public Integer getIdParticipacion() {
        return idParticipacion;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public Double getValorTarjeta() {
        return valorTarjeta;
    }

    public String getMotivoTarjeta() {
        return motivoTarjeta;
    }

    public String getPeriodoPartido() {
        return periodoPartido;
    }

    public String getTiempoEvento() {
        return tiempoEvento;
    }
}
