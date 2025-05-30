package CasoEstudioUTEC;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        this.archivosAdjuntos = new ArrayList<>();
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

    public List<ArchivoAdjunto> getArchivosAdjuntos() {
        return archivosAdjuntos;
    }


    public void setArchivosAdjuntos(List<ArchivoAdjunto> archivosAdjuntos) {
        this.archivosAdjuntos = archivosAdjuntos;
    }

    @Override
    public String toString() {
        return "InformeFinal{" +
                "id='" + id + '\'' +
                ", seguimiento=" + seguimiento +
                ", valoracion=" + valoracion +
                ", contenido='" + contenido + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", archivosAdjuntos=" + archivosAdjuntos.size() + " attachments" +
                '}';
    }
}
