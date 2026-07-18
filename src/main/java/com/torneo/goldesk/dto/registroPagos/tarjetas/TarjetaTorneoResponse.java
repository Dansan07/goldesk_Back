package com.torneo.goldesk.dto.registroPagos.tarjetas;

public class TarjetaTorneoResponse {

    private Integer idTarjeta;
    private String tipoTarjeta;
    private Double valorTarjeta;
    private String motivoTarjeta;
    private String nombreJugador;
    private String apellidosJugador;
    private String nombreEquipo;
    private Double montoAbonado;
    private Double saldoPendiente;
    private String estadoPago; // Recibe 'DEBE', 'PAGADO' o 'ABONO'

    public interface Vista {
        Integer getIdTarjeta();
        String getTipoTarjeta();
        Double getValorTarjeta();
        String getMotivoTarjeta();
        String getNombreJugador();
        String getApellidosJugador();
        String getNombreEquipo();
        Double getMontoAbonado();
        Double getSaldoPendiente();
        String getEstadoPago();
    }

    public TarjetaTorneoResponse() {
    }



    public TarjetaTorneoResponse(TarjetaTorneoResponse.Vista vista) {
        this.idTarjeta = vista.getIdTarjeta();
        this.tipoTarjeta = vista.getTipoTarjeta();
        this.valorTarjeta = vista.getValorTarjeta();
        this.motivoTarjeta = vista.getMotivoTarjeta();
        this.nombreJugador = vista.getNombreJugador();
        this.apellidosJugador = vista.getApellidosJugador();
        this.nombreEquipo = vista.getNombreEquipo();
        this.montoAbonado = vista.getMontoAbonado();
        this.saldoPendiente = vista.getSaldoPendiente();
        this.estadoPago = vista.getEstadoPago();
    }

    @Override
    public String toString() {
        return "TarjetaTorneoResponse{" +
                "idTarjeta=" + idTarjeta +
                ", tipoTarjeta='" + tipoTarjeta + '\'' +
                ", valorTarjeta=" + valorTarjeta +
                ", motivoTarjeta='" + motivoTarjeta + '\'' +
                ", nombreJugador='" + nombreJugador + '\'' +
                ", apellidosJugador='" + apellidosJugador + '\'' +
                ", nombreEquipo='" + nombreEquipo + '\'' +
                ", montoAbonado=" + montoAbonado +
                ", saldoPendiente=" + saldoPendiente +
                ", estadoPago='" + estadoPago + '\'' +
                '}';
    }

    public Integer getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(Integer idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public Double getValorTarjeta() {
        return valorTarjeta;
    }

    public void setValorTarjeta(Double valorTarjeta) {
        this.valorTarjeta = valorTarjeta;
    }

    public String getMotivoTarjeta() {
        return motivoTarjeta;
    }

    public void setMotivoTarjeta(String motivoTarjeta) {
        this.motivoTarjeta = motivoTarjeta;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public String getNombreCompletoJugador() {
        return this.nombreJugador + " " + this.apellidosJugador;
    }

    public String getApellidosJugador() {
        return apellidosJugador;
    }

    public void setApellidosJugador(String apellidosJugador) {
        this.apellidosJugador = apellidosJugador;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public Double getMontoAbonado() {
        return montoAbonado;
    }

    public void setMontoAbonado(Double montoAbonado) {
        this.montoAbonado = montoAbonado;
    }

    public Double getSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(Double saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }
}
