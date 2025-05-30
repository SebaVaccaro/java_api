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

    public void addParticipante(Usuario participantes){
        this.participantes.add(participantes);
    }

    public boolean isEstadoActivo() {
        return estadoActivo;
    }

    public void setEstadoActivo(boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
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

