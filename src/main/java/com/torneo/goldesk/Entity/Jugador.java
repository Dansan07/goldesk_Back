package com.torneo.goldesk.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "jugadores")
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jugador")
    private Integer idJugador;

    @Column(name = "cedula_jugador")
    private String cedulaJug;

    @Column(name = "nombre_jugador")
    private String nombreJugador;

    @Column(name = "apellidos_jugador")
    private String apellidosJugador;

    @Column(name = "telefono_jugador")
    private String telJugador;

    @Column(name = "email_jugador")
    private String emailJugador;

    @Column(name = "url_foto")
    private String urlFotoJugador;

    @Column(name = "es_delegado")
    private Boolean esDelegado;


    public Jugador() {
    }

    //getters and setters
    public Integer getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(Integer idJugador) {
        this.idJugador = idJugador;
    }

    public String getCedulaJug() {
        return cedulaJug;
    }

    public void setCedulaJug(String cedulaDelegado) {
        this.cedulaJug = cedulaDelegado;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public String getApellidosJugador() {
        return apellidosJugador;
    }

    public void setApellidosJugador(String apellidosJugador) {
        this.apellidosJugador = apellidosJugador;
    }

    public String getTelJugador() {
        return telJugador;
    }

    public void setTelJugador(String telJugador) {
        this.telJugador = telJugador;
    }

    public String getEmailJugador() {
        return emailJugador;
    }

    public void setEmailJugador(String emailJugador) {
        this.emailJugador = emailJugador;
    }

    public String getUrlFotoJugador() {
        return urlFotoJugador;
    }

    public void setUrlFotoJugador(String urlFotoJugador) {
        this.urlFotoJugador = urlFotoJugador;
    }

    public Boolean isEsDelegado() {
        return esDelegado;
    }

    public void setEsDelegado(Boolean esDelegado) {
        this.esDelegado = esDelegado;
    }
}
