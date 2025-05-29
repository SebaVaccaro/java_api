package CasoEstudioUTEC;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Seguimiento {
    private String id;
    private Estudiante estudiante;
    private EstadoSeguimiento estado;
    private List<InstanciaApoyo> instanciasApoyo;
    private LocalDate fechaInicio;
    private LocalDate fechaCierre;

    // Constructor
    public Seguimiento(String id,
                       Estudiante estudiante,
                       EstadoSeguimiento estado,
                       LocalDate fechaInicio) {
        this.id = id;
        this.estudiante = estudiante;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.instanciasApoyo = new ArrayList<>();
        this.fechaCierre = null; // opcional, puede establecerse luego
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public EstadoSeguimiento getEstado() {
        return estado;
    }

    public void setEstado(EstadoSeguimiento estado) {
        this.estado = estado;
    }

    public List<InstanciaApoyo> getInstanciasApoyo() {
        return instanciasApoyo;
    }

    public void addInstanciaApoyo(InstanciaApoyo instancia) {
        this.instanciasApoyo.add(instancia);
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDate fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    @Override
    public String toString() {
        return "Seguimiento de " + estudiante.getNombre() + " [" + estado + "] desde " + fechaInicio +
                (fechaCierre != null ? " hasta " + fechaCierre : "");
    }

    public enum EstadoSeguimiento {
        EN_PROCESO,
        FINALIZADO,
        PENDIENTE
    }
}