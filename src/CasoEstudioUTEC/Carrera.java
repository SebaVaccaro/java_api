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

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
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