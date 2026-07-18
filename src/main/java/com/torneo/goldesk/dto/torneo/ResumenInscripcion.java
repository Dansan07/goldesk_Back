package com.torneo.goldesk.dto.torneo;

public class ResumenInscripcion {

    public interface Vista{
        Double getValorInscripcionPorEquipo();
        Long getCantidadEquipos();
        Double getTotalARecoger();
        Double getTotalAbonado();
        Double getSaldoPendienteGeneral();
    }

    private Double valorInscripcionPorEquipo;
    private Long cantidadEquipos;
    private Double totalARecoger;
    private Double totalAbonado;
    private Double saldoPendienteGeneral;

    public ResumenInscripcion(Vista vista) {
        this.valorInscripcionPorEquipo = vista.getValorInscripcionPorEquipo();
        this.cantidadEquipos = vista.getCantidadEquipos();
        this.totalARecoger = vista.getTotalARecoger();
        this.totalAbonado = vista.getTotalAbonado();
        this.saldoPendienteGeneral = vista.getSaldoPendienteGeneral();
    }

    public Double getValorInscripcionPorEquipo() {
        return valorInscripcionPorEquipo;
    }

    public Long getCantidadEquipos() {
        return cantidadEquipos;
    }

    public Double getTotalARecoger() {
        return totalARecoger;
    }

    public Double getTotalAbonado() {
        return totalAbonado;
    }

    public Double getSaldoPendienteGeneral() {
        return saldoPendienteGeneral;
    }
}
