package com.torneo.goldesk.dto.actores.organizador;

import com.torneo.goldesk.dto.actores.PersonaBaseDTO;

public class OrganizadorUpdateDTO extends PersonaBaseDTO {

    private String passHashOrg;
    private String codigoInvitado;
    private boolean activo;

    //getters and setters

    public String getPassHashOrg() {
        return passHashOrg;
    }

    public void setPassHashOrg(String passHashOrg) {
        this.passHashOrg = passHashOrg;
    }

    public String getCodigoInvitado() {
        return codigoInvitado;
    }

    public void setCodigoInvitado(String codigoInvitado) {
        this.codigoInvitado = codigoInvitado;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
