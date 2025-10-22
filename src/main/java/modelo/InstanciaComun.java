package modelo;

import java.time.OffsetDateTime;

public class InstanciaComun extends Instancia {
    private int idSeguimiento;  // FK hacia Seguimiento

    // Constructor vacío
    public InstanciaComun() {
        super();
    }

    // Constructor sin idInstancia (para insertar)
    public InstanciaComun(String titulo, OffsetDateTime fecHora, String descripcion, boolean estActivo, int idFuncionario, int idSeguimiento) {
        super(titulo, fecHora, descripcion, estActivo, idFuncionario);
        this.idSeguimiento = idSeguimiento;
    }

    // Constructor completo
    public InstanciaComun(int idInstancia, String titulo, OffsetDateTime fecHora, String descripcion, boolean estActivo, int idFuncionario, int idSeguimiento) {
        super(idInstancia, titulo, fecHora, descripcion, estActivo, idFuncionario);
        this.idSeguimiento = idSeguimiento;
    }

    // Getter y Setter
    public int getIdSeguimiento() {
        return idSeguimiento;
    }

    public void setIdSeguimiento(int idSeguimiento) {
        this.idSeguimiento = idSeguimiento;
    }

    @Override
    public String toString() {
        return "InstanciaComun{" +
                super.toString() +
                ", idSeguimiento=" + idSeguimiento +
                '}';
    }
}
