package com.torneo.goldesk.dto.traspaso;

import java.time.LocalDateTime;

public class TraspasoUpdateDTO {

    private Integer idTraspaso;
    private String estado;
    private String observaciones;

    public TraspasoUpdateDTO(Integer idTraspaso, String estado, String observaciones) {
        this.idTraspaso = idTraspaso;
        this.estado = estado;
        this.observaciones = observaciones;
    }

    public Integer getIdTraspaso() {
        return idTraspaso;
    }

    public String getEstado() {
        return estado;
    }

    public String getObservaciones() {
        return observaciones;
    }
}
