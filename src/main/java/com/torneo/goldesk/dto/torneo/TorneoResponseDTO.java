package com.torneo.goldesk.dto.torneo;

public class TorneoResponseDTO {

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
    private String categoriaTorneo;
    private Boolean activo;

    public TorneoResponseDTO(Integer idTorneo, String cedulaOrganizador, String nombreTorneo, double valorAmarilla, double valorAzul, double valorRoja, double valorArbitraje, double valorInscripcion, double valorBalonPetos, Integer partidosInicial, String categoriaTorneo, Boolean activo) {
        this.idTorneo = idTorneo;
        this.cedulaOrganizador = cedulaOrganizador;
        this.nombreTorneo = nombreTorneo;
        this.valorAmarilla = valorAmarilla;
        this.valorAzul = valorAzul;
        this.valorRoja = valorRoja;
        this.valorArbitraje = valorArbitraje;
        this.valorInscripcion = valorInscripcion;
        this.valorBalonPetos = valorBalonPetos;
        this.partidosInicial = partidosInicial;
        this.categoriaTorneo = categoriaTorneo;
        this.activo = activo;
    }

    public String getCategoriaTorneo() {
        return categoriaTorneo;
    }

    public Integer getIdTorneo() {
        return idTorneo;
    }

    public String getCedulaOrganizador() {
        return cedulaOrganizador;
    }

    public String getNombreTorneo() {
        return nombreTorneo;
    }

    public double getValorAmarilla() {
        return valorAmarilla;
    }

    public double getValorAzul() {
        return valorAzul;
    }

    public double getValorRoja() {
        return valorRoja;
    }

    public double getValorArbitraje() {
        return valorArbitraje;
    }

    public double getValorInscripcion() {
        return valorInscripcion;
    }

    public double getValorBalonPetos() {
        return valorBalonPetos;
    }

    public Integer getPartidosInicial() {
        return partidosInicial;
    }

    public boolean getActivo() {
        return activo;
    }
}
