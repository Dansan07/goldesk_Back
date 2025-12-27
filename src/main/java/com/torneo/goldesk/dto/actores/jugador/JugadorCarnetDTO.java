package com.torneo.goldesk.dto.actores.jugador;

import com.torneo.goldesk.dto.actores.PersonaBaseDTO;

public class JugadorCarnetDTO extends PersonaBaseDTO {

    private String urlFoto;
    private Boolean esDelegado;

    // Datos del Contexto (De las relaciones)
    private String nombreEquipo;
    private String nombreTorneo;
    private String categoriaTorneo; // Ej: Libre, Veteranos, etc.
    private Integer idInscripcion;

    public JugadorCarnetDTO(String cedula, String nombre, String apellidos, String telefono, String email, String urlFoto, Boolean esDelegado, String nombreEquipo, String nombreTorneo, String categoriaTorneo, Integer idInscripcion) {
        super(cedula, nombre, apellidos, telefono, email);
        this.urlFoto = urlFoto;
        this.esDelegado = esDelegado;
        this.nombreEquipo = nombreEquipo;
        this.nombreTorneo = nombreTorneo;
        this.categoriaTorneo = categoriaTorneo;
        this.idInscripcion = idInscripcion;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public Boolean getEsDelegado() {
        return esDelegado;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public String getNombreTorneo() {
        return nombreTorneo;
    }

    public String getCategoriaTorneo() {
        return categoriaTorneo;
    }

    public Integer getIdInscripcion() {
        return idInscripcion;
    }
}
