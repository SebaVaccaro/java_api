package CasoEstudioUTEC;

public class Carrera {

    private int id;
    private String nombre;
    private int codigo;
    private String plan;

    public Carrera(int id, String nombre, int codigo, String plan) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.plan = plan;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getPlan() {
        return plan;
    }


    @java.lang.Override
    public java.lang.String toString() {
        return "Carrera{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", codigo=" + codigo +
                ", plan='" + plan + '\'' +
                '}';
    }
}