package main.java.modelo;

import java.time.LocalDateTime;
import java.util.List;

public class Incidencia extends Instancia {

    private String lugar;

    public Incidencia(String lugar, String id, String titulo, String tipo, LocalDateTime fechaHora, String descripcion, Estudiante estudiante, List<Funcionario> funcionarios) {
        super(id, titulo, tipo, fechaHora, descripcion, estudiante, funcionarios);
        this.lugar = lugar;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    @Override
    public String toString() {
        return super.toString() +
                "lugar='" + '\'' +lugar + '\'';
    }
}