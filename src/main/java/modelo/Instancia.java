package modelo;

import modelo.Funcionario;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Instancia {
    protected int id;
    protected String titulo;
    protected String tipo;
    protected LocalDateTime fechaHora;
    protected String descripcion;
    protected Estudiante estudiante;
    protected boolean estadoActivo = true;
    List<Funcionario> funcionarios;

    public Instancia(String titulo, String tipo, LocalDateTime fechaHora, String descripcion, Estudiante estudiante, List<Funcionario> funcionarios) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.fechaHora = fechaHora;
        this.descripcion = descripcion;
        this.estudiante = estudiante;
        this.funcionarios = funcionarios;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void addFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
    }

    public boolean isEstadoActivo() {
        return estadoActivo;
    }

    public void setEstadoActivo(boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    @Override
    public String toString() {
        return "Instancia{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", tipo='" + tipo + '\'' +
                ", fechaHora=" + fechaHora +
                ", descripcion='" + descripcion + '\'' +
                ", estadoActivo=" + estadoActivo +
                '}';
    }

}

