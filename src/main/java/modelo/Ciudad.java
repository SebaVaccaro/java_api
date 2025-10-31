package modelo;

public class Ciudad {

    private int idCiudad;
    private int codPostal;
    private String nombre;
    private String departamento;

    // Constructor vacío
    public Ciudad() {
    }

    // Constructor sin ID (para insertar en la BD)
    public Ciudad(int codPostal, String nombre, String departamento) {
        this.codPostal = codPostal;
        this.nombre = nombre;
        this.departamento = departamento;
    }

    // Constructor completo
    public Ciudad(int idCiudad, int codPostal, String nombre, String departamento) {
        this.idCiudad = idCiudad;
        this.codPostal = codPostal;
        this.nombre = nombre;
        this.departamento = departamento;
    }

    // Getters y Setters
    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public int getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(int codPostal) {
        this.codPostal = codPostal;
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

    // Representación en texto
    @Override
    public String toString() {
        return "Ciudad{" +
                "idCiudad=" + idCiudad +
                ", codPostal=" + codPostal +
                ", nombre='" + nombre + '\'' +
                ", departamento='" + departamento + '\'' +
                '}';
    }
}

