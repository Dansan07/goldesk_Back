package com.torneo.goldesk.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "organizadores")
public class Organizador {

    @Id
    @Column(name = "cedula_organizador")
    private String cedulaOrg;

    @Column(name = "telefono_organizador")
    private String telefonoOrg;

    @Column(name = "nombre_organizador")
    private String nombreOrg;

    @Column(name = "apellidos_organizador")
    private String apellidoOrg;

    @Column(name = "email_organizador")
    private String emailOrg;

    @Column(name = "password_hash_organizador")
    private String passHashOrg;

    @Column(name = "codigo_invitado")
    private String codigoInvitado;

    @Column(name = "id_rol")
    private Integer idRol;

    @Column(name = "activo")
    private boolean activo;

    @Column(name = "logo_organizador")
    private String urlLogoOrg;

    //contructor

    public Organizador() {
    }

    //getters and setters


    public String getUrlLogoOrg() {
        return urlLogoOrg;
    }

    public void setUrlLogoOrg(String urlLogoOrg) {
        this.urlLogoOrg = urlLogoOrg;
    }

    public String getCedulaOrg() {
        return cedulaOrg;
    }

    public void setCedulaOrg(String cedulaOrg) {
        this.cedulaOrg = cedulaOrg;
    }

    public String getTelefonoOrg() {
        return telefonoOrg;
    }

    public void setTelefonoOrg(String telefonoOrg) {
        this.telefonoOrg = telefonoOrg;
    }

    public String getNombreOrg() {
        return nombreOrg;
    }

    public void setNombreOrg(String nombreOrg) {
        this.nombreOrg = nombreOrg;
    }

    public String getApellidoOrg() {
        return apellidoOrg;
    }

    public void setApellidoOrg(String apellidoOrg) {
        this.apellidoOrg = apellidoOrg;
    }

    public String getEmailOrg() {
        return emailOrg;
    }

    public void setEmailOrg(String emailOrg) {
        this.emailOrg = emailOrg;
    }

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

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
