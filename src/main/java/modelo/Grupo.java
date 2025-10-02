package main.java.modelo;

public class Grupo {

    private String id;
    private String nombre;
    private Carrera carrera;

    public Grupo(String id, String nombre, Carrera carrera) {
        this.id = id;
        this.nombre = nombre;
        this.carrera = carrera;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Carrera getCarrera() {
        return carrera;
    }


    @Override
    public String toString() {
        return "Grupo{" +
                "id= " +'\''+ id +'\''+
                ", nombre= " + '\''+nombre + '\'' +
                ", carrera= " + '\''+carrera +'\''+
                '}';
    }
}