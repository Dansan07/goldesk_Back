package com.torneo.goldesk.dto.actores.organizador;

public class ActualizaDatosOrgDTO {

    private String cedula;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;

    public ActualizaDatosOrgDTO(String cedula, String nombre, String apellidos, String telefono, String email) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
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
}
