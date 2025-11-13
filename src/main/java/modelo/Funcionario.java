package modelo;

public class Funcionario extends Usuario {

    private int idRol;
    private boolean estActivo;

    // Constructor vacío
    public Funcionario() {
    }

    // Constructor completo
    public Funcionario(int idUsuario, String cedula, String nombre, String apellido, String username, String password, String correo,
                       int idRol, boolean estActivo) {
        super(idUsuario, cedula, nombre, apellido, username, password, correo);
        this.idRol = idRol;
        this.estActivo = estActivo;
    }

    // Getters y Setters
    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public boolean isActivo() {
        return estActivo;
    }

    public void setActivo(boolean estActivo) {
        this.estActivo = estActivo;
    }

    @Override
    public String toString() {
        return "idUsuario=" + getIdUsuario() + "\n" +
                "cedula='" + getCedula() + '\'' + "\n" +
                "nombre='" + getNombre() + '\'' + "\n" +
                "apellido='" + getApellido() + '\'' + "\n" +
                "username='" + getUsername() + '\'' + "\n" +
                "correo='" + getCorreo() + '\'' + "\n" +
                "idRol=" + idRol + "\n" +
                "estActivo=" + estActivo + "\n" +
                "----------";
    }


}

