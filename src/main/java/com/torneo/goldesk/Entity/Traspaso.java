package com.torneo.goldesk.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "solicitud_traspaso")
public class Traspaso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTraspaso;

    // Cédula del organizador que aprueba/rechaza
    @ManyToOne
    @JoinColumn(name = "cedula_organizador_responsable")
    private Organizador organizador;

    @ManyToOne
    @JoinColumn(name = "id_jugador")
    private Jugador jugador;

    // Relación con torneo_equipos (Equipo donde está ahora)
    @ManyToOne
    @JoinColumn(name = "id_torneo_equipos_actual")
    private TorneoEquipo torneoEquipoActual;

    // Relación con torneo_equipos (Equipo al que quiere ir)
    @ManyToOne
    @JoinColumn(name = "id_torneo_equipos_solicita")
    private TorneoEquipo torneoEquipoSolicita;

    @Column(name = "asunto_traspaso")
    private String asunto;

    // Aquí guardaremos la URL o el Path del PDF generado/escaneado
    @JsonIgnore
    @Column(name = "documento_firmado")
    private byte[] documentoFirmado;

    @Column(name = "estado_solicitud")
    private String estado; // PENDIENTE, APROBADO, RECHAZADO

    @Column(name = "fecha_solicitud", insertable = false, updatable = false)
    private LocalDateTime fechaSolicitud;

    @Column(name = "fecha_respuesta")
    private LocalDateTime fechaRespuesta;

    @Column(columnDefinition = "text")
    private String observaciones;

    public Traspaso() {
    }

    public Integer getIdTraspaso() {
        return idTraspaso;
    }

    public void setIdTraspaso(Integer idTraspaso) {
        this.idTraspaso = idTraspaso;
    }

    public Organizador getOrganizador() {
        return organizador;
    }

    public void setOrganizador(Organizador organizador) {
        this.organizador = organizador;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public TorneoEquipo getTorneoEquipoActual() {
        return torneoEquipoActual;
    }

    public void setTorneoEquipoActual(TorneoEquipo torneoEquipoActual) {
        this.torneoEquipoActual = torneoEquipoActual;
    }

    public TorneoEquipo getTorneoEquipoSolicita() {
        return torneoEquipoSolicita;
    }

    public void setTorneoEquipoSolicita(TorneoEquipo torneoEquipoSolicita) {
        this.torneoEquipoSolicita = torneoEquipoSolicita;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public byte[] getDocumentoFirmado() {
        return documentoFirmado;
    }

    public void setDocumentoFirmado(byte[] documentoFirmado) {
        this.documentoFirmado = documentoFirmado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public LocalDateTime getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta(LocalDateTime fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
