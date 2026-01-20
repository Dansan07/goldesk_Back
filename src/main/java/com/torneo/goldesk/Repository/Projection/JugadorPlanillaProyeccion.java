package com.torneo.goldesk.Repository.Projection;

public interface JugadorPlanillaProyeccion {
    Integer getIdReferencia(); // Debe coincidir con el alias en el SQL
    String getDorsal();
    String getNombreJugador();
    Integer getCantGoles();
    Integer getCantAmarillas();
    Integer getCantAzules();
    Integer getCantRojas();
}
