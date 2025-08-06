

public class Funcionario extends Usuario {

    private boolean estadoActivo = true;
    private Rol rol;

    public enum Rol {
        ADMINISTRADOR, PSICOPEDAGOGO, ANALISTA_EDUCATIVO, RESPONSABLE_EDUCATIVO, AREA_LEGAL
    }

    public Funcionario(Rol rol, String id, String username, String password, String nombre, String apellido, String correo, String telefono) {
        super(username, password, nombre, apellido, correo, telefono);
        this.rol = rol;
    }

    public boolean isEstadoActivo() {
        return estadoActivo;
    }

    public void setEstadoActivo(boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}