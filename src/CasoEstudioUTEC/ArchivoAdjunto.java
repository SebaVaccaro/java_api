package CasoEstudioUTEC;
import java.time.LocalDateTime;

public class ArchivoAdjunto {
    private Integer id; // id de tabla arch_adjuntos
    private Estudiante estudiante;
    private Usuario usuario;
    private String ruta;
    private String categoria;
    private boolean estadoActivo = true;

    public ArchivoAdjunto(Integer id, Estudiante estudiante, Usuario usuario, String ruta, String categoria) {
        this.id = id;
        this.estudiante = estudiante;
        this.usuario = usuario;
        this.ruta = ruta;
        this.categoria = categoria;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Estudiante getEstudiante() { return estudiante; }
    public Usuario getUsuario() { return usuario; }
    public String getRuta() { return ruta; }
    public void setRuta(String ruta) { this.ruta = ruta; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public boolean isEstadoActivo() { return estadoActivo; }
    public void setEstadoActivo(boolean estadoActivo) { this.estadoActivo = estadoActivo; }

    @Override
    public String toString() {
        return "ArchivoAdjunto{" +
                "id=" + id +
                ", estudiante=" + estudiante +
                ", usuario=" + usuario +
                ", ruta='" + ruta + '\'' +
                ", categoria='" + categoria + '\'' +
                ", estadoActivo=" + estadoActivo +
                '}';
    }
}