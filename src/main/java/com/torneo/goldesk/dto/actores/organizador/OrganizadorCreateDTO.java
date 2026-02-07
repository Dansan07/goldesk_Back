package com.torneo.goldesk.dto.actores.organizador;

import com.torneo.goldesk.dto.actores.PersonaBaseDTO;

public class OrganizadorCreateDTO extends PersonaBaseDTO {

    private Integer idRol;
    private boolean activo;

    public Integer getIdRol() {
        return idRol;
    }

    public boolean getActivo() {
        return activo;
    }
}
