package CasoEstudioUTEC;

import java.util.List;


public class Estudiante extends Usuario{

    private String historialAcademico;
    private List<ArchivoAdjunto> informesMedicos;
    private String observaciones;
    private Estado estado;

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
                      String historialAcademico,
                      String observaciones,
                      Estado estado) {

        super(id, username, passwordHash, nombre, apellido, correo, telefono, direccion, rol);
        this.historialAcademico = historialAcademico;
        this.observaciones = observaciones;
        this.estado = estado;
        
    }

    public String getHistorialAcademico() {
        return historialAcademico;
    }

    public void setHistorialAcademico(String historialAcademico) {
        this.historialAcademico = historialAcademico;
    }

    public List<ArchivoAdjunto> getInformesMedicos() {
        return informesMedicos;
    }

    public void setInformesMedicos(List<ArchivoAdjunto> informesMedicos) {
        this.informesMedicos = informesMedicos;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "historialAcademico='" + historialAcademico + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", estado=" + estado +
                '}';
    }

}
