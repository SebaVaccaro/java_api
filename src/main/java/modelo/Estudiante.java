package modelo;

public class Estudiante extends Usuario {
    private int idGrupo;
    private boolean estActivo;

    public Estudiante() {}

    public Estudiante(int idUsuario, String cedula, String nombre, String apellido, String username, String password, String correo,
                      int idGrupo, boolean estActivo) {
        super(idUsuario, cedula, nombre, apellido, username, password, correo);
        this.idGrupo = idGrupo;
        this.estActivo = estActivo;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    // ✅ Métodos renombrados
    public boolean isActivo() {
        return estActivo;
    }

    public void setActivo(boolean estActivo) {
        this.estActivo = estActivo;
    }

    @Override
    public String toString() {
        return "Estudiante{" + super.toString() +
                ", idGrupo=" + idGrupo +
                ", estActivo=" + estActivo +
                '}';
    }
}
