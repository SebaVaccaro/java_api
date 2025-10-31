package modelo;

public class PartSeguimiento {

    private int idParticipante;
    private int idSeguimiento;

    // Constructor vacío
    public PartSeguimiento() {
    }

    // Constructor completo
    public PartSeguimiento(int idParticipante, int idSeguimiento) {
        this.idParticipante = idParticipante;
        this.idSeguimiento = idSeguimiento;
    }

    // Getters y Setters
    public int getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(int idParticipante) {
        this.idParticipante = idParticipante;
    }

    public int getIdSeguimiento() {
        return idSeguimiento;
    }

    public void setIdSeguimiento(int idSeguimiento) {
        this.idSeguimiento = idSeguimiento;
    }

    // Representación en texto
    @Override
    public String toString() {
        return "PartSeguimiento{" +
                "idParticipante=" + idParticipante +
                ", idSeguimiento=" + idSeguimiento +
                '}';
    }
}
