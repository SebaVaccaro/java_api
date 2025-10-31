package modelo;

import java.time.OffsetDateTime;

public class Observacion {

    private int idObservacion;
    private int idFuncionario;
    private int idEstudiante;
    private String titulo;
    private String contenido;
    private OffsetDateTime fecHora;
    private boolean estActivo;

    // Constructor vacío
    public Observacion() {
    }

    // Constructor sin ID (para insertar en la BD)
    public Observacion(int idFuncionario, int idEstudiante, String titulo, String contenido, OffsetDateTime fecHora, boolean estActivo) {
        this.idFuncionario = idFuncionario;
        this.idEstudiante = idEstudiante;
        this.titulo = titulo;
        this.contenido = contenido;
        this.fecHora = fecHora;
        this.estActivo = estActivo;
    }

    // Constructor completo
    public Observacion(int idObservacion, int idFuncionario, int idEstudiante, String titulo, String contenido, OffsetDateTime fecHora, boolean estActivo) {
        this.idObservacion = idObservacion;
        this.idFuncionario = idFuncionario;
        this.idEstudiante = idEstudiante;
        this.titulo = titulo;
        this.contenido = contenido;
        this.fecHora = fecHora;
        this.estActivo = estActivo;
    }

    // Getters y Setters
    public int getIdObservacion() {
        return idObservacion;
    }

    public void setIdObservacion(int idObservacion) {
        this.idObservacion = idObservacion;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
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

    public OffsetDateTime getFecHora() {
        return fecHora;
    }

    public void setFecHora(OffsetDateTime fecHora) {
        this.fecHora = fecHora;
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
        return "Observacion{" +
                "idObservacion=" + idObservacion +
                ", idFuncionario=" + idFuncionario +
                ", idEstudiante=" + idEstudiante +
                ", titulo='" + titulo + '\'' +
                ", contenido='" + contenido + '\'' +
                ", fecHora=" + fecHora +
                ", estActivo=" + estActivo +
                '}';
    }
}
