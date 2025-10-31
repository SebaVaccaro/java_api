package modelo;

import java.time.LocalDate;

public class Notificacion {

    private int idNotificacion;
    private int idInstancia;
    private String asunto;
    private String mensaje;
    private String destinatario;
    private LocalDate fecEnvio;
    private boolean estActivo;

    // Constructor vacío
    public Notificacion() {
    }

    // Constructor sin ID (para insertar en la BD)
    public Notificacion(int idInstancia, String asunto, String mensaje, String destinatario, LocalDate fecEnvio, boolean estActivo) {
        this.idInstancia = idInstancia;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.destinatario = destinatario;
        this.fecEnvio = fecEnvio;
        this.estActivo = estActivo;
    }

    // Constructor completo
    public Notificacion(int idNotificacion, int idInstancia, String asunto, String mensaje, String destinatario, LocalDate fecEnvio, boolean estActivo) {
        this.idNotificacion = idNotificacion;
        this.idInstancia = idInstancia;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.destinatario = destinatario;
        this.fecEnvio = fecEnvio;
        this.estActivo = estActivo;
    }

    // Getters y Setters
    public int getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(int idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public int getIdInstancia() {
        return idInstancia;
    }

    public void setIdInstancia(int idInstancia) {
        this.idInstancia = idInstancia;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public LocalDate getFecEnvio() {
        return fecEnvio;
    }

    public void setFecEnvio(LocalDate fecEnvio) {
        this.fecEnvio = fecEnvio;
    }

    public boolean isEstActivo() {
        return estActivo;
    }

    public void setEstActivo(boolean estActivo) {
        this.estActivo = estActivo;
    }

    // Representación en texto
    @Override
    public String toString() {
        return "Notificacion{" +
                "idNotificacion=" + idNotificacion +
                ", idInstancia=" + idInstancia +
                ", asunto='" + asunto + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", destinatario='" + destinatario + '\'' +
                ", fecEnvio=" + fecEnvio +
                ", estActivo=" + estActivo +
                '}';
    }
}
