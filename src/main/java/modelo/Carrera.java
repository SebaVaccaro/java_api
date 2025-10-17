package modelo;

public class Carrera {
    private int idCarrera;
    private String codigo;
    private String nombre;
    private String plan;

    // Constructor vacío
    public Carrera() {
    }

    // Constructor sin id (para insertar)
    public Carrera(String codigo, String nombre, String plan) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.plan = plan;
    }

    // Constructor completo
    public Carrera(int idCarrera, String codigo, String nombre, String plan) {
        this.idCarrera = idCarrera;
        this.codigo = codigo;
        this.nombre = nombre;
        this.plan = plan;
    }

    // Getters y Setters
    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    @Override
    public String toString() {
        return "Carrera{" +
                "idCarrera=" + idCarrera +
                ", codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", plan='" + plan + '\'' +
                '}';
    }
}
