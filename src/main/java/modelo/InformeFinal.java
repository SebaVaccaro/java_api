package modelo;

import java.time.LocalDate;

public class InformeFinal {

    private int idInfFinal;
    private String contenido;
    private int valoracion;
    private LocalDate fecCreacion;

    // Constructor vacío
    public InformeFinal() {
    }

    // Constructor sin ID (para insertar en la BD)
    public InformeFinal(String contenido, int valoracion, LocalDate fecCreacion) {
        this.contenido = contenido;
        this.valoracion = valoracion;
        this.fecCreacion = fecCreacion;
    }

    // Constructor completo
    public InformeFinal(int idInfFinal, String contenido, int valoracion, LocalDate fecCreacion) {
        this.idInfFinal = idInfFinal;
        this.contenido = contenido;
        this.valoracion = valoracion;
        this.fecCreacion = fecCreacion;
    }

    // Getters y Setters
    public int getIdInfFinal() {
        return idInfFinal;
    }

    public void setIdInfFinal(int idInfFinal) {
        this.idInfFinal = idInfFinal;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public LocalDate getFecCreacion() {
        return fecCreacion;
    }

    public void setFecCreacion(LocalDate fecCreacion) {
        this.fecCreacion = fecCreacion;
    }

    // Representación en texto
    @Override
    public String toString() {
        return "InformeFinal{" +
                "idInfFinal=" + idInfFinal +
                ", contenido='" + contenido + '\'' +
                ", valoracion=" + valoracion +
                ", fecCreacion=" + fecCreacion +
                '}';
    }
}

