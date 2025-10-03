package modelo;


import modelo.*;
import modelo.Instancia;



import java.time.LocalDateTime;
import java.util.List;

public class Incidencia extends Instancia {
    private String lugar;
    private Funcionario registrante; // Funcionario que registra la incidencia

    public Incidencia(String titulo, String tipo, LocalDateTime fechaHora, String descripcion, Estudiante estudiante, List<Funcionario> funcionarios, String lugar, Funcionario registrante) {
        super(titulo, tipo, fechaHora, descripcion, estudiante, funcionarios);
        this.lugar = lugar;
        this.registrante = registrante;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Funcionario getRegistrante() {
        return registrante;
    }

    public void setRegistrante(Funcionario registrante) {
        this.registrante = registrante;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", lugar='" + lugar + '\'' +
                ", registrante=" + (registrante != null ? registrante.getNombre() : "null");
    }
}
