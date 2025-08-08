package CasoEstudioUTEC;

public class Ciudad {

    private String id;
    private String nombre;
    private String departamento;
    private int codPostal;

    public Ciudad(String id, String nombre, String departamento, int codPostal) {
        this.id = id;
        this.departamento = departamento;
        this.nombre = nombre;
        this.codPostal = codPostal;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public int getCodPostal() {
        return codPostal;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Ciudad{" + "nombre='" + nombre + '\'' + ", departamento='" + departamento + '\'' + ", codPostal=" + codPostal + '}';
    }
}