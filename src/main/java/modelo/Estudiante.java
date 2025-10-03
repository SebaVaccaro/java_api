package modelo;

import modelo.ArchivoAdjunto;
import modelo.Observaciones;
import modelo.Direccion;
import modelo.Grupo;
import java.util.ArrayList;
import java.util.List;

public class Estudiante extends Usuario {

    private boolean activo = false; // Indica si el estudiante está activo o fue eliminado
    private List<ArchivoAdjunto> archivosAdjuntos;
    private List<Observaciones> observaciones;
    private Grupo grupo;

    public Estudiante(int id, String ci, String username, String password, String nombre, String apellido, String correo, String telefono, Direccion direccion, Grupo grupo) {
        super(id, ci, username, password, nombre, apellido, correo, telefono, direccion);
        this.archivosAdjuntos = new ArrayList<>();
        this.observaciones = new ArrayList<>();
        if(grupo!=null){
            this.grupo = grupo;
        } // puede ser null si aún no está asignado
    }

    // Getter / Setter activo
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<ArchivoAdjunto> getArchivosAdjuntos() {
        return archivosAdjuntos;
    }

    public void addArchivoAdjunto(ArchivoAdjunto archivo) {
        if (archivo != null) {
            this.archivosAdjuntos.add(archivo);
        }
    }

    @Override
    public String getTipo() {
        return "ESTUDIANTE";
    }

    public List<Observaciones> getObservaciones() {
        return observaciones;
    }

    public void addObservacion(Observaciones obs) {
        if (obs != null) {
            this.observaciones.add(obs);
        }
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", activo=" + activo +
                ", grupo=" + grupo +
                ", archivosAdjuntos=" + archivosAdjuntos +
                ", observaciones=" + observaciones;
    }
}
