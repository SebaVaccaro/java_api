package CasoEstudioUTEC;
import java.util.List;

public class ITR {

    private int id;
    private int numITR;
    private List<String> telefonos;
    private Direccion direccion;
    private List<Carrera> carreras;

    public ITR(int id, int numITR, String telefono, Direccion direccion) {
        this.id = id;
        this.numITR = numITR;
        telefonos.add(telefono);
        direccion = direccion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumITR() {
        return numITR;
    }

    public void setNumITR(int numITR) {
        this.numITR = numITR;
    }

    public List<String> getTelefonos() {
        return telefonos;
    }

    public void addTelefonos(String telefono) {
        telefonos.add(telefono);
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public List<Carrera> getCarreras() {
        return carreras;
    }

    public void addCarreras(Carrera carrera) {
        carreras.add(carrera);
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "ITR{" +
                "id=" + id +
                ", numITR=" + numITR +
                ", telefonos=" + telefonos +
                '}';
    }
}