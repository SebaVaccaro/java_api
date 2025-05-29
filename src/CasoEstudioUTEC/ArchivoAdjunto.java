package CasoEstudioUTEC;

public class ArchivoAdjunto {
    private String id;
    private Usuario usuario;
    private String ruta;
    private String tipo;

    public ArchivoAdjunto(String id, Usuario usuario, String ruta, String tipo) {
        this.id = id;
        this.usuario = usuario;
        this.ruta = ruta;
        this.tipo = tipo;
    }

    public String getRuta() {
        return this.ruta;
    }

    public String getTipo() {
        return this.tipo;
    }

    @Override
    public String toString() {
        return "ArchivoAdjunto{" +
                "id='" + id + '\'' +
                ", usuario=" + (usuario != null ? usuario.toString() : "null") +
                ", ruta='" + ruta + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}