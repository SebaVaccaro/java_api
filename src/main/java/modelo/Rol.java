package modelo;


public class Rol {
    private int idRol;
    private String nombre;
    private boolean estActivo;

    // Constructor vacío
    public Rol() {
    }

    // Constructor sin id (para insertar)
    public Rol(String nombre, boolean estActivo) {
        this.nombre = nombre;
        this.estActivo = estActivo;
    }

    // Constructor completo
    public Rol(int idRol, String nombre, boolean estActivo) {
        this.idRol = idRol;
        this.nombre = nombre;
        this.estActivo = estActivo;
    }

    // Getters y Setters
    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEstActivo() {
        return estActivo;
    }

    public void setEstActivo(boolean estActivo) {
        this.estActivo = estActivo;
    }

    @Override
    public String toString() {
        return "Rol{" +
                "idRol=" + idRol +
                ", nombre='" + nombre + '\'' +
                ", estActivo=" + estActivo +
                '}';
    }
}
