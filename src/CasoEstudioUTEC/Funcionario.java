package CasoEstudioUTEC;

import java.time.LocalDateTime;
import java.util.List;

public class Funcionario extends Usuario {

    private boolean estadoActivo = true;
    private Rol rol;

    public enum Rol {
        ADMINISTRADOR, PSICOPEDAGOGO, ANALISTA_EDUCATIVO, RESPONSABLE_EDUCATIVO, AREA_LEGAL
    }

    public Funcionario(Rol rol, String id, String ci, String username, String password, String nombre, String apellido, String correo, String telefono, Direccion direccion) {
        super(id, ci, username, password, nombre, apellido, correo, telefono, direccion);
        this.rol = rol;
    }

    public boolean isEstadoActivo() {
        return estadoActivo;
    }

    public void setEstadoActivo(boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    public void generarInstancia(String id, String titulo, String tipo, LocalDateTime fechaHora, String descripcion, Estudiante estudiante, List<Funcionario> funcionarios){
        Instancia instancia = new Instancia(id, titulo, tipo, fechaHora, descripcion, estudiante, funcionarios);
    }

    public void generarIncidencia(String lugar, String id, String titulo, String tipo, LocalDateTime fechaHora, String descripcion, Estudiante estudiante, List<Funcionario> funcionarios){
        Instancia instancia = new Incidencia(lugar, id, titulo, tipo, fechaHora, descripcion, estudiante, funcionarios);
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return super.toString()  +
                "estadoActivo=" +  '\'' +  estadoActivo +  '\'' +
        ", rol=" + '\'' +rol+'\'';
    }
}