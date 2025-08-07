package CasoEstudioUTEC;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Estudiante extends Usuario{

    private List<ArchivoAdjunto> archivoAdjuntoList = new ArrayList<ArchivoAdjunto>(); //Archivos adjuntos que muestran el estado de salud.
    private Estado estado;
    private List<Observaciones> observaciones = new ArrayList<Observaciones>();
    private Grupo grupo;

    public enum Estado {
        CREADO,
        ELIMINADO,
        EN_SEGUIMIENTO,
        FINALIZADO
    }

    public Estudiante(String id,
                      String ci,
                      String username,
                      String password,
                      String nombre,
                      String apellido,
                      String correo,
                      String telefono,
                      Direccion direccion,
                      List<ArchivoAdjunto> archivoAdjuntoList,
                      Estado estado,
                      Grupo grupo) {

        super(id, ci, username, password, nombre, apellido, correo, telefono, direccion);
        this.archivoAdjuntoList = archivoAdjuntoList;
        this.estado = estado;
        this.grupo = grupo;
        
    }


    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<ArchivoAdjunto> getArchivoAdjuntoList() {
        return archivoAdjuntoList;
    }

    public void addArchivoAdjunto(ArchivoAdjunto archivoAdjunto){
        this.archivoAdjuntoList.add(archivoAdjunto);
    }


    public List<Observaciones> getObservaciones() {
        return observaciones;
    }


    public void addObservacion(Observaciones observacion){
        observaciones.add(observacion);
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    /**
*    public String generarReporte(){
*        return ??
*    Metodo para generar reportes que no sabemos como implementar aún.
*    Pensamos que puede ser una query a la DB directamente, o un metodo de clase.
*   };
*/

@Override
    public String toString() {

        return super.toString() +
                "archivoAdjuntoList" + '\'' +archivoAdjuntoList +'\'' +
                ", estado=" + '\'' +estado +'\'' +
                ", observaciones=" + '\'' +observaciones +'\'';
    }
}
