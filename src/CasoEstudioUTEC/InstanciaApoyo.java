package CasoEstudioUTEC;
import java.time.LocalDateTime;

public class InstanciaApoyo {
    private String id;
    private String tipo;
    private LocalDateTime fechaHora;
    private String descripcion;
    private List<Usuario> participantes;
    private List<ArchivoAdjunto> archivosAdjuntos;


    public InstanciaApoyo(String id, String tipo, LocalDateTime fechaHora, String descripcion, List<Usuario> participantes, List<ArchivoAdjunto> archivosAdjuntos) {
        this.id = id;
        this.tipo = tipo;
        this.fechaHora = fechaHora;
        this.descripcion = descripcion;
        this.participantes = participantes;
        this.archivosAdjuntos = archivosAdjuntos;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }


    public String getTipo() {
        return this.tipo:
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

    public void setParticipantes(List<Usuario> participantes) {
        this.participantes = participantes;
    }

    public void setArchivosAdjuntos(List<ArchivoAdjunto> archivosAdjuntos) {
        this.archivosAdjuntos = archivosAdjuntos;
    }

    public List<ArchivoAdjunto> getArchivosAdjuntos() {
        return this.archivosAdjuntos;
    }

    public void addAchivoAdjunto(ArchivoAdjunto archivoAdjunto){
        this.archivosAdjuntos.add(archivoAdjunto);
    }

    @Override
    public String toString() {
        return "InstanciaApoyo{" +
                "id='" + id + '\'' +
                ", tipo='" + tipo + '\'' +
                ", fechaHora=" + fechaHora +
                ", descripcion='" + descripcion + '\'' +
                ", participantes=" + participantes +
                ", archivosAdjuntos=" + archivosAdjuntos +
                '}';
    }
}

}
