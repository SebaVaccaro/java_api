package modelo;

public class Pertenece {
    private int idCarrera;  // FK hacia Carrera
    private int idItr;      // FK hacia ITR

    // Constructor vacío
    public Pertenece() {
    }

    // Constructor completo
    public Pertenece(int idCarrera, int idItr) {
        this.idCarrera = idCarrera;
        this.idItr = idItr;
    }

    // Getters y Setters
    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public int getIdItr() {
        return idItr;
    }

    public void setIdItr(int idItr) {
        this.idItr = idItr;
    }

    @Override
    public String toString() {
        return "Pertenece{" +
                "idCarrera=" + idCarrera +
                ", idItr=" + idItr +
                '}';
    }
}
