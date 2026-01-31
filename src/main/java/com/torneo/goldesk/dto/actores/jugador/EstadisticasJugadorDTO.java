package com.torneo.goldesk.dto.actores.jugador;

public record EstadisticasJugadorDTO(
        Integer idTorneoEquipoJugador,
        String nombreCompleto,
        Long partidosJugados,
        Long golesAnotados,
        Long amarillas,
        Long azules,
        Long rojas
) {
}
