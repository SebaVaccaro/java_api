package PROXY;

import modelo.Estudiante;
import servicios.EstudianteServicio;
import utils.ValidarUsuario;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EstudianteProxy {

    private final EstudianteServicio estudianteServicio;
    private final ValidarUsuario validarUsuario;

    public EstudianteProxy() throws Exception {
        this.estudianteServicio = new EstudianteServicio();
        this.validarUsuario = new ValidarUsuario();
    }

    public Estudiante crearEstudiante(String cedula, String nombre, String apellido,
                                      String password, int idGrupo, LocalDate fechaNacimiento) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo administradores pueden crear estudiantes.");
        }
        return estudianteServicio.registrarEstudiante(cedula, password, nombre, apellido, fechaNacimiento, idGrupo);
    }

    public Estudiante obtenerPorId(int idUsuario) throws SQLException {
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(idUsuario)) {
            throw new SecurityException("Solo administradores, psicólogos o el propio usuario pueden obtener estudiantes.");
        }
        return estudianteServicio.obtenerPorId(idUsuario);
    }

    public List<Estudiante> listarTodos() throws SQLException {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("Solo administradores o psicopedagogo pueden listar estudiantes.");
        }
        return estudianteServicio.listarTodos();
    }

    public boolean actualizarEstudiante(Estudiante est) throws SQLException {
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(est.getIdUsuario())) {
            throw new SecurityException("Solo administradores, psicólogos o el propio usuario pueden actualizar estudiantes.");
        }
        return estudianteServicio.actualizarEstudiante(est);
    }

    public boolean desactivarEstudiante(int idUsuario) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo administradores pueden desactivar estudiantes.");
        }
        return estudianteServicio.desactivarEstudiante(idUsuario);
    }

    public boolean estaActivo(int idUsuario) throws SQLException {
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(idUsuario)) {
            throw new SecurityException("Solo administradores, psicólogos o el propio usuario pueden ver el estado del estudiante.");
        }
        return estudianteServicio.estaActivo(idUsuario);
    }
}
