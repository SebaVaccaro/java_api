package CasoEstudioUTEC;

import java.util.List;

public class Grupo {
    private int id;
    private String nomGrupo;
    private Carrera carrera;

    public Grupo(int id, String nomGrupo, Carrera carrera) {
        this.id = id;
        this.nomGrupo = nomGrupo;
        this.carrera = carrera;
    }

    public int getId() { return id; }
    public String getNomGrupo() { return nomGrupo; }
    public void setNomGrupo(String nomGrupo) { this.nomGrupo = nomGrupo; }
    public Carrera getCarrera() { return carrera; }

    @Override
    public String toString() {
        return "Grupo{" + "id=" + id + ", nomGrupo='" + nomGrupo + '\'' + ", carrera=" + carrera + '}';
    }
}
