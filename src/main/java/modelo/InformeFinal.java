package main.java.modelo;

import java.time.LocalDate;

public class InformeFinal {
    private Integer id;
    private Seguimiento seguimiento;
    private String contenido;
    private int valoracion;
    private LocalDate fechaCreacion;

    public InformeFinal(Integer id, Seguimiento seguimiento, String contenido,
                        int valoracion, LocalDate fechaCreacion) {
        this.id = id;
        this.seguimiento = seguimiento;
        this.contenido = contenido;
        this.valoracion = valoracion;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Seguimiento getSeguimiento() { return seguimiento; }
    public void setSeguimiento(Seguimiento seguimiento) { this.seguimiento = seguimiento; }
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    public int getValoracion() { return valoracion; }
    public void setValoracion(int valoracion) { this.valoracion = valoracion; }
    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    @Override
    public String toString() {
        return "InformeFinal{" +
                "id=" + id +
                ", seguimiento=" + seguimiento +
                ", contenido='" + contenido + '\'' +
                ", valoracion=" + valoracion +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}
