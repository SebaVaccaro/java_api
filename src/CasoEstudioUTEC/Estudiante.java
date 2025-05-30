package CasoEstudioUTEC;

import java.util.List;


public class Estudiante extends Usuario{

    private List<ArchivoAdjunto> historialAcademico; //Archivos adjuntos sobre el historial académico del estudiante.
    private List<ArchivoAdjunto> informesMedicos; //Archivos adjuntos que muestran el estado de salud.
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
                      String passwordHash,
                      String nombre,
                      String apellido,
                      String correo,
                      String telefono,
                      String direccion,
                      Rol rol,
                      List<ArchivoAdjunto> historialAcademico,
                      Estado estado) {

        super(id, username, passwordHash, nombre, apellido, correo, telefono, direccion, rol);
        this.historialAcademico = historialAcademico;
        this.estado = estado;
        
    }


    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<ArchivoAdjunto> getHistorialAcademico() {
        return historialAcademico;
    }

    public void addHistorialAcademico(ArchivoAdjunto historialAcademico){
        this.historialAcademico.add(historialAcademico);
    }


    public List<ArchivoAdjunto> getInformesMedicos() {
        return informesMedicos;
    }


    public void addInformesMedicos(ArchivoAdjunto informesMedicos){
        this.informesMedicos.add(informesMedicos);
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
                "historialAcademico=" + historialAcademico +
                ", informesMedicos=" + informesMedicos +
                ", estado=" + estado +
                ", observaciones=" + observaciones +
                '}';
    }
}
