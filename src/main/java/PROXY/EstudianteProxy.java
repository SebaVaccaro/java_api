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
            throw new SecurityException("Solo los usuarios con rol ADMINISTRADOR pueden crear estudiantes.");
        }
        return estudianteServicio.registrarEstudiante(cedula, password, nombre, apellido, fechaNacimiento, idGrupo);
    }

    public Estudiante obtenerPorId(int idUsuario) throws SQLException {
        return estudianteServicio.obtenerPorId(idUsuario);
    }

    public List<Estudiante> listarTodos() throws SQLException {
        return estudianteServicio.listarTodos();
    }

    public boolean actualizarEstudiante(Estudiante est) throws SQLException {
        return estudianteServicio.actualizarEstudiante(est);
    }

    public boolean desactivarEstudiante(int idUsuario) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo los usuarios con rol ADMINISTRADOR pueden desactivar estudiantes.");
        }
        return estudianteServicio.desactivarEstudiante(idUsuario);
    }

    public boolean estaActivo(int idUsuario) throws SQLException {
        return estudianteServicio.estaActivo(idUsuario);
    }
}
