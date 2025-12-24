package com.torneo.goldesk.dto.actores.organizador;

import com.torneo.goldesk.dto.actores.PersonaBaseDTO;

public class OrganizadorCreateDTO extends PersonaBaseDTO {

    private String passHashOrg;
    private String codigoInvitado;
    private Integer idRol;
    private boolean activo;

    public String getPassHashOrg() {
        return passHashOrg;
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
