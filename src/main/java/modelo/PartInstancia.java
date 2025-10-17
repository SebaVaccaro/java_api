package modelo;

public class PartInstancia {
    private int idParticipante;  // FK hacia Estudiante
    private int idInstancia;     // FK hacia Instancia

    // Constructor vacío
    public PartInstancia() {
    }

    // Constructor completo
    public PartInstancia(int idParticipante, int idInstancia) {
        this.idParticipante = idParticipante;
        this.idInstancia = idInstancia;
    }

    // Getters y Setters
    public int getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(int idParticipante) {
        this.idParticipante = idParticipante;
    }

    public int getIdInstancia() {
        return idInstancia;
    }

    public void setIdInstancia(int idInstancia) {
        this.idInstancia = idInstancia;
    }

    @Override
    public String toString() {
        return "PartInstancia{" +
                "idParticipante=" + idParticipante +
                ", idInstancia=" + idInstancia +
                '}';
    }
}
