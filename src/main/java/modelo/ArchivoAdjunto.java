package modelo;

public class ArchivoAdjunto {

    private int idArchivoAdjunto;
    private int idUsuario;
    private int idEstudiante;
    private String ruta;
    private String categoria;
    private boolean estActivo;

    // Constructor vacío
    public ArchivoAdjunto() {
    }

    // Constructor sin ID (para insertar en la BD)
    public ArchivoAdjunto(int idUsuario, int idEstudiante, String ruta, String categoria, boolean estActivo) {
        this.idUsuario = idUsuario;
        this.idEstudiante = idEstudiante;
        this.ruta = ruta;
        this.categoria = categoria;
        this.estActivo = estActivo;
    }

    // Constructor completo
    public ArchivoAdjunto(int idArchivoAdjunto, int idUsuario, int idEstudiante, String ruta, String categoria, boolean estActivo) {
        this.idArchivoAdjunto = idArchivoAdjunto;
        this.idUsuario = idUsuario;
        this.idEstudiante = idEstudiante;
        this.ruta = ruta;
        this.categoria = categoria;
        this.estActivo = estActivo;
    }

    // Getters y Setters
    public int getIdArchivoAdjunto() {
        return idArchivoAdjunto;
    }

    public void setIdArchivoAdjunto(int idArchivoAdjunto) {
        this.idArchivoAdjunto = idArchivoAdjunto;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isEstActivo() {
        return estActivo;
    }

    public void setEstActivo(boolean estActivo) {
        this.estActivo = estActivo;
    }

    // Representación del objeto en texto
    @Override
    public String toString() {
        return "ArchivoAdjunto{" +
                "idArchivoAdjunto=" + idArchivoAdjunto +
                ", idUsuario=" + idUsuario +
                ", idEstudiante=" + idEstudiante +
                ", ruta='" + ruta + '\'' +
                ", categoria='" + categoria + '\'' +
                ", estActivo=" + estActivo +
                '}';
    }
}
