package modelo;

public class ITR {
    private int idItr;
    private int idDireccion; // FK hacia Direccion

    // Constructor vacío
    public ITR() {
    }

    // Constructor sin id (para insertar)
    public ITR(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    // Constructor completo
    public ITR(int idItr, int idDireccion) {
        this.idItr = idItr;
        this.idDireccion = idDireccion;
    }

    // Getters y Setters
    public int getIdItr() {
        return idItr;
    }

    public void setIdItr(int idItr) {
        this.idItr = idItr;
    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    @Override
    public String toString() {
        return "ITR{" +
                "idItr=" + idItr +
                ", idDireccion=" + idDireccion +
                '}';
    }
}
