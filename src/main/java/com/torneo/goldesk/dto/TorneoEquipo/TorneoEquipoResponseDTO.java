package com.torneo.goldesk.dto.TorneoEquipo;

public class TorneoEquipoResponseDTO {
    private Integer idTorneoEquipo;
    private Integer idTorneo;      // Solo el ID
    private String nombreTorneo;   // Solo el nombre
    private Integer idEquipo;      // Solo el ID
    private String nombreOriginal; // Nombre base del equipo
    private String nombrePersonalizado;
    private boolean equipoActivo;

    public TorneoEquipoResponseDTO(Integer idTorneoEquipo, Integer idTorneo, String nombreTorneo, Integer idEquipo, String nombreOriginal, String nombrePersonalizado, boolean equipoActivo) {
        this.idTorneoEquipo = idTorneoEquipo;
        this.idTorneo = idTorneo;
        this.nombreTorneo = nombreTorneo;
        this.idEquipo = idEquipo;
        this.nombreOriginal = nombreOriginal;
        this.nombrePersonalizado = nombrePersonalizado;
        this.equipoActivo = equipoActivo;
    }

    public Integer getIdTorneoEquipo() {
        return idTorneoEquipo;
    }

    public Integer getIdTorneo() {
        return idTorneo;
    }

    public String getNombreTorneo() {
        return nombreTorneo;
    }

    public Integer getIdEquipo() {
        return idEquipo;
    }

    public String getNombreOriginal() {
        return nombreOriginal;
    }

    public String getNombrePersonalizado() {
        return nombrePersonalizado;
    }

    public boolean isEquipoActivo() {
        return equipoActivo;
    }
}
