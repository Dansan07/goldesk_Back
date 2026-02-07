package com.torneo.goldesk.dto.actores.organizador;

public class OrganizadorResponseDTO{

    private String cedula;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;
    private String codigoInvitado;
    private String rol;
    private boolean activo;
    private String urlLogoOrg;

    public OrganizadorResponseDTO(String cedula, String nombre, String apellidos, String telefono, String email, String codigoInvitado, String rol, boolean activo, String urlLogoOrg) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
        this.codigoInvitado = codigoInvitado;
        this.rol = rol;
        this.activo = activo;
        this.urlLogoOrg = urlLogoOrg;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getCodigoInvitado() {
        return codigoInvitado;
    }

    public String getRol() {
        return rol;
    }

    public boolean isActivo() {
        return activo;
    }

    public String getUrlLogoOrg() {
        return urlLogoOrg;
    }
}
