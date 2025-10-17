package modelo;


public class Grupo {
    private int idGrupo;
    private String nomGrupo;
    private int idCarrera; // FK hacia Carrera

    // Constructor vacío
    public Grupo() {
    }

    // Constructor sin id (para insertar)
    public Grupo(String nomGrupo, int idCarrera) {
        this.nomGrupo = nomGrupo;
        this.idCarrera = idCarrera;
    }

    // Constructor completo
    public Grupo(int idGrupo, String nomGrupo, int idCarrera) {
        this.idGrupo = idGrupo;
        this.nomGrupo = nomGrupo;
        this.idCarrera = idCarrera;
    }

    // Getters y Setters
    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getNomGrupo() {
        return nomGrupo;
    }

    public void setNomGrupo(String nomGrupo) {
        this.nomGrupo = nomGrupo;
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "idGrupo=" + idGrupo +
                ", nomGrupo='" + nomGrupo + '\'' +
                ", idCarrera=" + idCarrera +
                '}';
    }
}
