package com.torneo.goldesk.dto.registroPagos.inscripcion;

import java.util.List;

public class EstadoCuentaInscripcionDTO {

    private Double valorInscripcionTorneo; // Lo traes del objeto Torneo
    private Double totalAbonado;
    private Double saldoPendiente;
    private List<AbonoDetalleDTO> historialAbonos;

    public EstadoCuentaInscripcionDTO(Double valorInscripcionTorneo, Double totalAbonado, Double saldoPendiente, List<AbonoDetalleDTO> historialAbonos) {
        this.valorInscripcionTorneo = valorInscripcionTorneo;
        this.totalAbonado = totalAbonado;
        this.saldoPendiente = saldoPendiente;
        this.historialAbonos = historialAbonos;
    }

    public Double getValorInscripcionTorneo() {
        return valorInscripcionTorneo;
    }

    public void setValorInscripcionTorneo(Double valorInscripcionTorneo) {
        this.valorInscripcionTorneo = valorInscripcionTorneo;
    }

    public Double getTotalAbonado() {
        return totalAbonado;
    }

    public void setTotalAbonado(Double totalAbonado) {
        this.totalAbonado = totalAbonado;
    }

    public Double getSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(Double saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    public List<AbonoDetalleDTO> getHistorialAbonos() {
        return historialAbonos;
    }

    public void setHistorialAbonos(List<AbonoDetalleDTO> historialAbonos) {
        this.historialAbonos = historialAbonos;
    }
}
