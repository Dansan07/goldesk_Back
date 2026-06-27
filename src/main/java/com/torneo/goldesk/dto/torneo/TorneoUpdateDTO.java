package com.torneo.goldesk.dto.torneo;

public class TorneoUpdateDTO extends TorneoDTO {

    public TorneoUpdateDTO(Integer idTorneo, String nombreTorneo, double valorAmarilla, double valorAzul, double valorRoja, double valorArbitraje, double valorInscripcion, double valorBalonPetos, Integer partidosInicial, String categoriaTorneo) {
        super(idTorneo, nombreTorneo, valorAmarilla, valorAzul, valorRoja, valorArbitraje, valorInscripcion, valorBalonPetos, partidosInicial, categoriaTorneo);
    }
}
