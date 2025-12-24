package com.torneo.goldesk.dto.actores.jugador;

import com.torneo.goldesk.dto.actores.PersonaBaseDTO;

public class JugadorUpdateDTO extends PersonaBaseDTO {

    private Integer idJugador;
    private String urlFoto;
    private boolean esDelegado;


    public Integer getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(Integer idJugador) {
        this.idJugador = idJugador;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public boolean isEsDelegado() {
        return esDelegado;
    }

    public void setEsDelegado(boolean esDelegado) {
        this.esDelegado = esDelegado;
    }
}
