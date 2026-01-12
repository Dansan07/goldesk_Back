package com.torneo.goldesk.dto.partido;

import java.time.LocalDate;
import java.time.LocalTime;

public class PartidoCreateDTO {
    private Integer idTorneo;
    private Integer idEquipoLocal;
    private Integer idEquipoVisitante;
    private LocalDate fecha;
    private LocalTime hora;
    private String cancha;
    private String fase;
    private boolean confirmarDuplicado;

    //getters and setters

    public boolean isConfirmarDuplicado() {
        return confirmarDuplicado;
    }

    public Integer getIdTorneo() {
        return idTorneo;
    }

    public Integer getIdEquipoLocal() {
        return idEquipoLocal;
    }

    public Integer getIdEquipoVisitante() {
        return idEquipoVisitante;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public String getCancha() {
        return cancha;
    }

    public String getFase() {
        return fase;
    }
}
