package main.java.modelo;

import java.time.LocalDate;

public class InformeFinal {
    private String id;
    private Seguimiento seguimiento;
    private int valoracion;
    private String contenido;
    private LocalDate fechaCreacion;

    public InformeFinal(String id, Seguimiento seguimiento, int valoracion, String contenido, LocalDate fechaCreacion) {
        this.id = id;
        this.seguimiento = seguimiento;
        this.valoracion = valoracion;
        this.contenido = contenido;
        this.fechaCreacion = fechaCreacion;

    }

    public String getId() {
        return id;
    }

    public Seguimiento getSeguimiento() {
        return seguimiento;
    }

    public int getValoracion() {
        return valoracion;
    }

    public String getContenido() {
        return contenido;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    @Override
    public String toString() {
        return "InformeFinal{" +
                "id='" + id + '\'' +
                ", seguimiento=" + seguimiento +
                ", valoracion=" + valoracion +
                ", contenido='" + contenido + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}
