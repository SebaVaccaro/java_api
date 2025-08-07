package CasoEstudioUTEC;
import java.time.LocalDateTime;

public class Observaciones {

    private String id;
    private String titulo;
    private String contenido;
    private LocalDateTime fechaHora;
    private Funcionario autor;
    private Estudiante estudiante;
    private boolean estadoActivo = true;

    public Observaciones(String id, String titulo, String contenido, LocalDateTime fechaHora, Funcionario autor, Estudiante estudiante) {
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.fechaHora = fechaHora;
        this.autor = autor;
        this.estudiante = estudiante;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public boolean isEstadoActivo() {
        return estadoActivo;
    }

    public void setEstadoActivo(boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    public Funcionario getAutor() {
        return autor;
    }

    @Override
    public String toString() {
        return "Observaciones{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", contenido='" + contenido + '\'' +
                ", fechaHora=" + fechaHora +
                ", estudiante=" + estudiante +
                '}';
    }
}