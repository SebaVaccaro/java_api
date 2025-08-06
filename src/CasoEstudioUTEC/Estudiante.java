package CasoEstudioUTEC;

import java.util.LinkedList;
import java.util.List;


public class Estudiante extends Usuario{

    private List<ArchivoAdjunto> archivoAdjuntoList; //Archivos adjuntos que muestran el estado de salud.
    private Estado estado;
    private List<Observaciones> observaciones;

    public enum Estado {
        CREADO,
        ELIMINADO,
        EN_SEGUIMIENTO,
        FINALIZADO
    }

    public Estudiante(String id,
                      String username,
                      String password,
                      String nombre,
                      String apellido,
                      String correo,
                      String telefono,
                      Direccion direccion,
                      List<ArchivoAdjunto> archivoAdjuntoList,
                      Estado estado) {

        super(id, username, password, nombre, apellido, correo, telefono, direccion);
        this.archivoAdjuntoList = archivoAdjuntoList;
        this.estado = estado;
        
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


    public void addObservaciones(Observaciones observaciones){
        this.observaciones.add(observaciones);
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

        return "Estudiante{" +
                "archivoAdjuntoList" + archivoAdjuntoList +
                ", estado=" + estado +
                ", observaciones=" + observaciones +
                '}';
    }
}
