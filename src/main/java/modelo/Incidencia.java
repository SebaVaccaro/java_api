package modelo;

import java.time.OffsetDateTime;

public class Incidencia extends Instancia {
    private String lugar;

    // Constructor vacío
    public Incidencia() {
        super();
    }

    // Constructor sin idInstancia (para insertar)
    public Incidencia(String titulo, OffsetDateTime fecHora, String descripcion, boolean estActivo, int idFuncionario, String lugar) {
        super(titulo, fecHora, descripcion, estActivo, idFuncionario);
        this.lugar = lugar;
    }

    // Constructor completo
    public Incidencia(int idInstancia, String titulo, OffsetDateTime fecHora, String descripcion, boolean estActivo, int idFuncionario, String lugar) {
        super(idInstancia, titulo, fecHora, descripcion, estActivo, idFuncionario);
        this.lugar = lugar;
    }

    // Getter y Setter
    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    @Override
    public String toString() {
        return "Incidencia{" +
                super.toString() +
                ", lugar='" + lugar + '\'' +
                '}';
    }
}
