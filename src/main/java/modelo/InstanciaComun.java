package modelo;

public class InstanciaComun {
    private int idInstancia;    // PK y FK hacia Instancia
    private int idSeguimiento;  // FK hacia Seguimiento

    // Constructor vacío
    public InstanciaComun() {
    }

    // Constructor completo
    public InstanciaComun(int idInstancia, int idSeguimiento) {
        this.idInstancia = idInstancia;
        this.idSeguimiento = idSeguimiento;
    }

    // Getters y Setters
    public int getIdInstancia() {
        return idInstancia;
    }

    public void setIdInstancia(int idInstancia) {
        this.idInstancia = idInstancia;
    }

    public int getIdSeguimiento() {
        return idSeguimiento;
    }

    public void setIdSeguimiento(int idSeguimiento) {
        this.idSeguimiento = idSeguimiento;
    }

    @Override
    public String toString() {
        return "InstanciaComun{" +
                "idInstancia=" + idInstancia +
                ", idSeguimiento=" + idSeguimiento +
                '}';
    }
}
