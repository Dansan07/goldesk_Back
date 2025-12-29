package com.torneo.goldesk.dto.tablaPosiciones;

public class TablaPosicionesDTO {

    private String nombreEquipo;
    private Long pj, pg, pe, pp;
    private Long gf, gc, dg, pts;
    private Boolean enVivo;

    public TablaPosicionesDTO(String nombreEquipo, Long pj, Long pg, Long pe, Long pp, Long gf, Long gc, Long dg, Long pts, Boolean enVivo) {
        this.nombreEquipo = nombreEquipo;
        this.pj = pj;
        this.pg = pg;
        this.pe = pe;
        this.pp = pp;
        this.gf = gf;
        this.gc = gc;
        this.dg = dg;
        this.pts = pts;
        this.enVivo = enVivo;
    }

    //getters and setters

    public Boolean getEnVivo() {
        return enVivo;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public Long getPj() {
        return pj;
    }

    public Long getPg() {
        return pg;
    }

    public Long getPe() {
        return pe;
    }

    public Long getPp() {
        return pp;
    }

    public Long getGf() {
        return gf;
    }

    public Long getGc() {
        return gc;
    }

    public Long getDg() {
        return dg;
    }

    public Long getPts() {
        return pts;
    }
}
