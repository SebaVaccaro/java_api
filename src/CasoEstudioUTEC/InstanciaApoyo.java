package CasoEstudioUTEC;

import java.time.LocalDateTime;

import java.util.List;


public class InstanciaApoyo {
    private String id;
    private String tipo;
    private LocalDateTime fechaHora;
    private String descripcion;
    private List<Usuario> participantes;
    private boolean estadoActivo = true;


    public InstanciaApoyo(String id, String tipo, LocalDateTime fechaHora, String descripcion, List<Usuario> participantes) {
        this.id = id;
        this.tipo = tipo;
        this.fechaHora = fechaHora;
        this.descripcion = descripcion;
        this.participantes = participantes;
        LocalDateTime fechaHoy = LocalDateTime.now();
        for (Usuario usuario : participantes) {
            generarNotificacion(id, usuario, tipo, descripcion, fechaHoy);
            /*
            Este id no puede ser el mismo para ambos objetos, esto es un simple ejemplo forzado para que el código no presente errores,
            ya que el constructor de Notificación pide un parámetro "id", le pasamos el mismo de InstanciaApoyo pero sabemos que no pueden repetirse.
            Es la manera que encontramos de representar este proceso donde se genera una notificación automáticamente para cada Usuario participante de
            dicha Instancia de Apoyo.
             */
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }


    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public LocalDateTime getFechaHora() {
        return this.fechaHora;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Usuario> getParticipantes() {
        return this.participantes;
    }

    public void addParticipante(Usuario participantes) {
        this.participantes.add(participantes);
    }

    public boolean isEstadoActivo() {
        return estadoActivo;
    }

    public void setEstadoActivo(boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    public void generarNotificacion(String id, Usuario destinatario, String asunto, String mensaje, LocalDateTime fechaEnvio) {
        Notificacion notificacion = new Notificacion(id, destinatario, asunto, mensaje, fechaEnvio);
        destinatario.addNotificacion(notificacion);
    }

    @Override
    public String toString() {
        return "InstanciaApoyo{" +
                "id='" + id + '\'' +
                ", tipo='" + tipo + '\'' +
                ", fechaHora=" + fechaHora +
                ", descripcion='" + descripcion + '\'' +
                ", participantes=" + participantes +
                '}';
    }
}

