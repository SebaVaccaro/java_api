package modelo;

import modelo.Funcionario;
import modelo.InstanciaComun;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Seguimiento {
    private int id; // id de tabla seguimientos
    private Estudiante estudiante;
    private LocalDate fechaInicio;
    private LocalDate fechaCierre;
    private List<Funcionario> participantes = new ArrayList<>();
    private List<InstanciaComun> instancias = new ArrayList<>();
    //  private boolean estadoActivo = true; //
    private boolean activo = true;


    public Seguimiento(Integer id, Estudiante estudiante, LocalDate fechaInicio, List<Funcionario> participantes) {
        this.id = id;
        this.estudiante = estudiante;
        this.fechaInicio = fechaInicio;
        this.participantes = participantes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDate fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public List<Funcionario> getParticipantes() {
        return participantes;
    }

    public void addParticipante(Funcionario funcionario) {
        participantes.add(funcionario);
    }

    public List<InstanciaComun> getInstancias() {
        return instancias;
    }

    public void addInstancia(InstanciaComun instancia) {
        instancias.add(instancia);
    }

    // public boolean isEstadoActivo() { return estadoActivo; }
//
    public boolean isActivo() {
        return activo;
    }


    // public void setEstadoActivo(boolean estadoActivo) { this.estadoActivo = estadoActivo; }
//
    public void setActivo(boolean activo) {
        this.activo = activo;
    }


    @Override
    public String toString() {
        return "Seguimiento{" +
                "id=" + id +
                ", estudiante=" + estudiante +
                ", fechaInicio=" + fechaInicio +
                ", fechaCierre=" + fechaCierre +
                ", participantes=" + participantes +
                ", instancias=" + instancias +
                ", activo=" + activo +
                '}';
    }
}