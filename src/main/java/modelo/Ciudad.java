package modelo;

public class Ciudad {
    private Integer id;
    private String nombre;
    private String departamento;
    private int codPostal;

    public Ciudad(Integer id, String nombre, String departamento, int codPostal) {
        this.id = id;
        this.nombre = nombre;
        this.departamento = departamento;
        this.codPostal = codPostal;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    public int getCodPostal() { return codPostal; }
    public void setCodPostal(int codPostal) { this.codPostal = codPostal; }

    @Override
    public String toString() {
        return "Ciudad{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", departamento='" + departamento + '\'' +
                ", codPostal=" + codPostal +
                '}';
    }
}
