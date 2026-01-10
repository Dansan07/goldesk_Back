package com.torneo.goldesk.dto.actores.organizador;

import com.torneo.goldesk.dto.actores.PersonaBaseDTO;

public class OrganizadorResponseDTO extends PersonaBaseDTO {

    private String codigoInvitado;
    private String rol;
    private boolean activo;
    private String passwordTemporal;

    public OrganizadorResponseDTO(String cedula, String nombre, String apellidos, String telefono, String email, String codigoInvitado, String idRol, boolean activo) {
        super(cedula, nombre, apellidos, telefono, email);
        this.codigoInvitado = codigoInvitado;
        this.rol = idRol;
        this.activo = activo;
    }

    public String getCodigoInvitado() {
        return codigoInvitado;
    }

    public String getRol() {
        return rol;
    }

    public boolean getActivo() {
        return activo;
    }
}
