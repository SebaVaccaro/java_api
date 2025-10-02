package main.java.modelo;

import java.time.LocalDateTime;

public class Notificacion {
    private String id;
    private Usuario destinatario;
    private String asunto;
    private String mensaje;
    private LocalDateTime fechaEnvio;
    private boolean estadoActivo = true; // hace referencia a que la notificacion está activa

    //Constructor
    public Notificacion(String id,
                        Usuario destinatario,
                        String asunto,
                        String mensaje,
                        LocalDateTime fechaEnvio) {
        this.id = id;
        this.destinatario = destinatario;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.fechaEnvio = fechaEnvio;
    }

    // Métodos
    public String getId() {
        return id;
    }

    public Usuario getDestinatario() {
        return destinatario;
    }

    public String getAsunto() {
        return asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public boolean isEstadoActivo() {
        return estadoActivo;
    }

    public void setEstadoActivo(boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    @Override
    public String toString() {
        return "Notificacion: " +
                "id= '" + id + '\'' +
                ", destinatario= " + destinatario +
                ", asunto= '" + asunto + '\'' +
                ", mensaje= '" + mensaje + '\'' +
                ", fechaEnvio= " + fechaEnvio;
    }

}
