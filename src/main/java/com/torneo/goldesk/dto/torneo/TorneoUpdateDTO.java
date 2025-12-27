package com.torneo.goldesk.dto.torneo;

public class TorneoUpdateDTO {

    private Integer idTorneo;
    private String cedulaOrganizador;
    private String nombreTorneo;
    private double valorAmarilla;
    private double valorAzul;
    private double valorRoja;
    private double valorArbitraje;
    private double valorInscripcion;
    private double valorBalonPetos;
    private Integer partidosInicial;
    private Boolean activo;
    private String categoriaTorneo;


    public String getCategoriaTorneo() {
        return categoriaTorneo;
    }

    public void setCategoriaTorneo(String categoriaTorneo) {
        this.categoriaTorneo = categoriaTorneo;
    }

    public Integer getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(Integer idTorneo) {
        this.idTorneo = idTorneo;
    }

    public String getCedulaOrganizador() {
        return cedulaOrganizador;
    }

    public void setCedulaOrganizador(String cedulaOrganizador) {
        this.cedulaOrganizador = cedulaOrganizador;
    }

    public String getNombreTorneo() {
        return nombreTorneo;
    }

    public void setNombreTorneo(String nombreTorneo) {
        this.nombreTorneo = nombreTorneo;
    }

    public double getValorAmarilla() {
        return valorAmarilla;
    }

    public void setValorAmarilla(double valorAmarilla) {
        this.valorAmarilla = valorAmarilla;
    }

    public double getValorAzul() {
        return valorAzul;
    }

    public void setValorAzul(double valorAzul) {
        this.valorAzul = valorAzul;
    }

    public double getValorRoja() {
        return valorRoja;
    }

    public void setValorRoja(double valorRoja) {
        this.valorRoja = valorRoja;
    }

    public double getValorArbitraje() {
        return valorArbitraje;
    }

    public void setValorArbitraje(double valorArbitraje) {
        this.valorArbitraje = valorArbitraje;
    }

    public double getValorInscripcion() {
        return valorInscripcion;
    }

    public void setValorInscripcion(double valorInscripcion) {
        this.valorInscripcion = valorInscripcion;
    }

    public double getValorBalonPetos() {
        return valorBalonPetos;
    }

    public void setValorBalonPetos(double valorBalonPetos) {
        this.valorBalonPetos = valorBalonPetos;
    }

    public Integer getPartidosInicial() {
        return partidosInicial;
    }

    public void setPartidosInicial(Integer partidosInicial) {
        this.partidosInicial = partidosInicial;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
