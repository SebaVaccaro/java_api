package CasoEstudioUTEC;

import java.time.LocalDateTime;
import java.util.List;

public class Incidencia extends Instancia {

    private String lugar;

    public Incidencia(String lugar, String id, String titulo, String tipo, LocalDateTime fechaHora, String descripcion, List<Usuario> participantes) {
        super(id, titulo, tipo, fechaHora, descripcion, participantes);
        this.lugar = lugar;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Incidencia{" + "lugar='" + lugar + '\'' + '}';
    }
}