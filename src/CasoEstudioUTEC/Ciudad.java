public class Ciudad {

    private int id;
    private String nombre;
    private String departamento;
    private int codPostal;

    public Ciudad(int id, String nombre, String departamento, int codPostal) {
        this.departamento = departamento;
        this.nombre = nombre;
        this.codPostal = codPostal;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public int getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(int codPostal) {
        this.codPostal = codPostal;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Ciudad{" + "nombre='" + nombre + '\'' + ", departamento='" + departamento + '\'' + ", codPostal=" + codPostal + '}';
    }
}