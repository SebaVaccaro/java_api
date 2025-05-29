package CasoEstudioUTEC;

public class Estudiante extends Usuario{

    private String historialAcademico;
    //private List<ArchivoAdjunto> informesMedicos;
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
}
