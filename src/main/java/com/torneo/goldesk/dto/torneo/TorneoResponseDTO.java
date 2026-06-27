package com.torneo.goldesk.dto.torneo;

public class TorneoResponseDTO extends TorneoDTO{

    public TorneoResponseDTO(Integer idTorneo, String cedulaOrganizador, String nombreTorneo, double valorAmarilla, double valorAzul, double valorRoja, double valorArbitraje, double valorInscripcion, double valorBalonPetos, Integer partidosInicial, String categoriaTorneo, Boolean activo) {
        super(idTorneo, cedulaOrganizador, nombreTorneo, valorAmarilla, valorAzul, valorRoja, valorArbitraje, valorInscripcion, valorBalonPetos, partidosInicial, categoriaTorneo, activo);
    }
}
