package com.torneo.goldesk.dto.tarjeta;

public class TarjetaResponseDTO {

    private Integer idTarjeta;
    private String tipoTarjeta;
    private String nombreJugador;
    private Double valorTarjeta;
    private String motivo;

    public TarjetaResponseDTO(Integer idTarjeta, String tipoTarjeta, String nombreJugador, Double valorTarjeta, String motivo) {
        this.idTarjeta = idTarjeta;
        this.tipoTarjeta = tipoTarjeta;
        this.nombreJugador = nombreJugador;
        this.valorTarjeta = valorTarjeta;
        this.motivo = motivo;
    }

    public Integer getIdTarjeta() {
        return idTarjeta;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public Double getValorTarjeta() {
        return valorTarjeta;
    }

    public String getMotivo() {
        return motivo;
    }
}
