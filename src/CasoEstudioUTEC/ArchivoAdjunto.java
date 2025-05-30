package CasoEstudioUTEC;

public class ArchivoAdjunto {
    private String id;
    private Usuario usuario;
    private String ruta;
    private TipoArchivo tipoArchivo;
    private boolean estadoActivo = true;

    public enum TipoArchivo {
        HISTORIAL_ACADEMICO,
        INFORME_MEDICO
    }

    public ArchivoAdjunto(String id, Usuario usuario, String ruta, TipoArchivo tipoArchivo) {
        this.id = id;
        this.usuario = usuario;
        this.ruta = ruta;
        this.tipo = tipoArchivo;
    }

    public String getRuta() {
        return this.ruta;
    }

    public String getTipoArchivo() {
        return this.tipoArchivo;
    }

    public boolean isEstadoActivo() {
        return estadoActivo;
    }

    public void setEstadoActivo(boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Usuario getUsuario() {
        return usuario;
    }


    @Override
    public String toString() {
        return "ArchivoAdjunto{" +
                "id='" + id + '\'' +
                ", usuario=" + (usuario != null ? usuario.toString() : "null") +
                ", ruta='" + ruta + '\'' +
                ", tipo='" + tipoArchivo + '\'' +
                '}';
    }
}