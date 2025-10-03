package modelo;

import java.util.List;
import java.util.ArrayList;

public class ITR {
    private Integer id;
    private Direccion direccion;
    private List<String> telefonos = new ArrayList<>();
    private List<Carrera> carreras = new ArrayList<>();

    public ITR(Integer id, Direccion direccion, String telefono) {
        this.id = id;
        this.direccion = direccion;
        this.telefonos.add(telefono);
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Direccion getDireccion() { return direccion; }
    public void setDireccion(Direccion direccion) { this.direccion = direccion; }
    public List<String> getTelefonos() { return telefonos; }
    public void addTelefono(String telefono) { telefonos.add(telefono); }
    public List<Carrera> getCarreras() { return carreras; }
    public void addCarrera(Carrera carrera) { carreras.add(carrera); }

    @Override
    public String toString() {
        return "ITR{" +
                "id=" + id +
                ", direccion=" + direccion +
                ", telefonos=" + telefonos +
                ", carreras=" + carreras +
                '}';
    }
}
