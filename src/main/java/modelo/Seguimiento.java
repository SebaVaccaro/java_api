package main.java.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Seguimiento {
    private String id;
    private Estudiante estudiante;
    private EstadoSeguimiento estado;
    private List<Instancia> instancias = new ArrayList<Instancia>();
    private LocalDate fechaInicio;
    private LocalDate fechaCierre;
    private List<Funcionario> funcionarios = new ArrayList<Funcionario>();

    // Constructor
    public Seguimiento(String id,
                       Estudiante estudiante,
                       EstadoSeguimiento estado,
                       LocalDate fechaInicio,
                       List<Funcionario> funcionarios
                       ) {
        this.id = id;
        this.estudiante = estudiante;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.instancias = new ArrayList<>();
        this.fechaCierre = null; // opcional, puede establecerse luego
        this.funcionarios = funcionarios;
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

    public List<Instancia> getInstancias() {
        return instancias;
    }

    public void addInstancia(Instancia instancia) {
        this.instancias.add(instancia);
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