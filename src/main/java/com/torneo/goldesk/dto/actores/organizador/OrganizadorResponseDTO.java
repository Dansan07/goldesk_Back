package com.torneo.goldesk.dto.actores.organizador;

import com.torneo.goldesk.dto.actores.PersonaBaseDTO;

public class OrganizadorResponseDTO extends PersonaBaseDTO {

    private String codigoInvitado;
    private Integer idRol;
    private boolean activo;
    private String passwordTemporal;

    public OrganizadorResponseDTO(String cedula, String nombre, String apellidos, String telefono, String email, String codigoInvitado, Integer idRol, boolean activo) {
        super(cedula, nombre, apellidos, telefono, email);
        this.codigoInvitado = codigoInvitado;
        this.idRol = idRol;
        this.activo = activo;
    }

    public String getCodigoInvitado() {
        return codigoInvitado;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public boolean getActivo() {
        return activo;
    }
}
