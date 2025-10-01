package CasoEstudioUTEC;

public class Carrera {
    private int id;
    private String codigo;
    private String nombre;
    private String plan;

    public Carrera(int id, String codigo, String nombre, String plan) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.plan = plan;
    }

    public int getId() { return id; }
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getPlan() { return plan; }

    @Override
    public String toString() {
        return "Carrera{" + "id=" + id + ", codigo='" + codigo + '\'' + ", nombre='" + nombre + '\'' + ", plan='" + plan + '\'' + '}';
    }
}
