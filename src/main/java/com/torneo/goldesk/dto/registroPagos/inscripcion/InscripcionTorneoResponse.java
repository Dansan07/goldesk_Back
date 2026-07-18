package com.torneo.goldesk.dto.registroPagos.inscripcion;

public class InscripcionTorneoResponse {

    public interface Vista {
        // Los nombres deben coincidir con las columnas devuelvas por la función
        Integer getIdTorneoEquipos();
        String getNombreEquipo();
        Double getValorInscripcion();
        Double getMontoAbonado();
        Double getSaldoPendiente();
        String getEstadoPago(); // Recibe 'DEBE', 'PAGADO' o 'ABONO'
    }

    private Integer idTorneoEquipos;
    private String nombreEquipo;
    private Double valorInscripcion;
    private Double montoAbonado;
    private Double saldoPendiente;
    private String estadoPago;

    public InscripcionTorneoResponse() {
    }

    public InscripcionTorneoResponse(InscripcionTorneoResponse.Vista vista) {
        this.idTorneoEquipos = vista.getIdTorneoEquipos();
        this.nombreEquipo = vista.getNombreEquipo();
        this.valorInscripcion = vista.getValorInscripcion();
        this.montoAbonado = vista.getMontoAbonado();
        this.saldoPendiente = vista.getSaldoPendiente();
        this.estadoPago = vista.getEstadoPago();
    }

    public Integer getIdTorneoEquipos() {
        return idTorneoEquipos;
    }

    public void setIdTorneoEquipos(Integer idTorneoEquipos) {
        this.idTorneoEquipos = idTorneoEquipos;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public Double getValorInscripcion() {
        return valorInscripcion;
    }

    public void setValorInscripcion(Double valorInscripcion) {
        this.valorInscripcion = valorInscripcion;
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
