package com.torneo.goldesk.dto.torneo;

public class TorneoUpdateDTO extends TorneoDTO {

    public TorneoUpdateDTO(Integer idTorneo,String cedulaOrganizador, String nombreTorneo, double valorAmarilla, double valorAzul, double valorRoja, double valorArbitraje, double valorInscripcion, double valorBalonPetos, Integer partidosInicial, String categoriaTorneo) {
        super(idTorneo, cedulaOrganizador, nombreTorneo, valorAmarilla, valorAzul, valorRoja, valorArbitraje, valorInscripcion, valorBalonPetos, partidosInicial, categoriaTorneo);
    }
}
