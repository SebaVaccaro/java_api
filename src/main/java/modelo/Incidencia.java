package modelo;

import java.time.OffsetDateTime;

public class Incidencia extends Instancia {

    private String lugar;

    // Constructor vacío
    public Incidencia() {
        super();
    }

    // Constructor sin ID (para insertar en la BD)
    public Incidencia(String titulo, OffsetDateTime fecHora, String descripcion, boolean estActivo, int idFuncionario, String lugar) {
        super(titulo, fecHora, descripcion, estActivo, idFuncionario);
        this.lugar = lugar;
    }

    // Constructor completo
    public Incidencia(int idInstancia, String titulo, OffsetDateTime fecHora, String descripcion, boolean estActivo, int idFuncionario, String lugar) {
        super(idInstancia, titulo, fecHora, descripcion, estActivo, idFuncionario);
        this.lugar = lugar;
    }

    // Getters y Setters
    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    // Representación en texto
    @Override
    public String toString() {
        return "Incidencia{" +
                super.toString() +
                ", lugar='" + lugar + '\'' +
                '}';
    }
}
