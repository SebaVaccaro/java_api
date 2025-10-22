package modelo;

import java.time.OffsetDateTime;

public abstract class Instancia {
    private int idInstancia;
    private String titulo;
    private OffsetDateTime fecHora;
    private String descripcion;
    private boolean estActivo;
    private int idFuncionario;

    // Constructor vacío
    public Instancia() {}

    // Constructor sin id (para insertar)
    public Instancia(String titulo, OffsetDateTime fecHora, String descripcion, boolean estActivo, int idFuncionario) {
        this.titulo = titulo;
        this.fecHora = fecHora;
        this.descripcion = descripcion;
        this.estActivo = estActivo;
        this.idFuncionario = idFuncionario;
    }

    // Constructor completo
    public Instancia(int idInstancia, String titulo, OffsetDateTime fecHora, String descripcion, boolean estActivo, int idFuncionario) {
        this.idInstancia = idInstancia;
        this.titulo = titulo;
        this.fecHora = fecHora;
        this.descripcion = descripcion;
        this.estActivo = estActivo;
        this.idFuncionario = idFuncionario;
    }

    // Getters y Setters
    public int getIdInstancia() {
        return idInstancia;
    }

    public void setIdInstancia(int idInstancia) {
        this.idInstancia = idInstancia;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public OffsetDateTime getFecHora() {
        return fecHora;
    }

    public void setFecHora(OffsetDateTime fecHora) {
        this.fecHora = fecHora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEstActivo() {
        return estActivo;
    }

    public void setEstActivo(boolean estActivo) {
        this.estActivo = estActivo;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    @Override
    public String toString() {
        return "Instancia{" +
                "idInstancia=" + idInstancia +
                ", titulo='" + titulo + '\'' +
                ", fecHora=" + fecHora +
                ", descripcion='" + descripcion + '\'' +
                ", estActivo=" + estActivo +
                ", idFuncionario=" + idFuncionario +
                '}';
    }
}


