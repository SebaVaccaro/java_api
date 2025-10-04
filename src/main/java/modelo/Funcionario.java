package modelo;

/**
 * Clase Funcionario que hereda de Usuario.
 * Representa un funcionario con rol definido.
 */
public class Funcionario extends Usuario {


    private Rol rol;

    public enum Rol {
        ADMINISTRADOR, PSICOPEDAGOGO, ANALISTA_EDUCATIVO, RESPONSABLE_EDUCATIVO, AREA_LEGAL
    }

    public Funcionario(Rol rol, int id, String ci, String username, String password, String nombre, String apellido, String correo, String telefono,  Direccion direccion) {
        super(id, ci, username, password, nombre, apellido, correo, telefono, direccion);
        if(rol!=null){
            this.rol = rol;
        }
    }



    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public String getTipo() {
        return "FUNCIONARIO";
    }

    public String toString() {
        return super.toString() +
                ", rol=" + rol;
    }
}
