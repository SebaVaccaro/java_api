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

    // Constructor: inicializa el servicio de estudiantes y el validador de usuario
    public EstudianteProxy() throws Exception {
        this.estudianteServicio = new EstudianteServicio();
        this.validarUsuario = new ValidarUsuario();
    }

    // Crear estudiante (solo administradores)
    public Estudiante crearEstudiante(String cedula, String nombre, String apellido,
                                      String password, int idGrupo, LocalDate fechaNacimiento) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo administradores pueden crear estudiantes.");
        }
        return estudianteServicio.registrarEstudiante(cedula, password, nombre, apellido, fechaNacimiento, idGrupo);
    }

    // Obtener estudiante por ID (sin restricción de permisos)
    public Estudiante obtenerPorId(int idUsuario) throws SQLException {
        return estudianteServicio.obtenerPorId(idUsuario);
    }

    // Listar todos los estudiantes (sin restricción de permisos)
    public List<Estudiante> listarTodos() throws SQLException {
        return estudianteServicio.listarTodos();
    }

    // Actualizar estudiante (sin restricción de permisos)
    public boolean actualizarEstudiante(Estudiante est) throws SQLException {
        return estudianteServicio.actualizarEstudiante(est);
    }

    // Desactivar estudiante (solo administradores)
    public boolean desactivarEstudiante(int idUsuario) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo administradores pueden desactivar estudiantes.");
        }
        return estudianteServicio.desactivarEstudiante(idUsuario);
    }

    // Verificar si un estudiante está activo (sin restricción de permisos)
    public boolean estaActivo(int idUsuario) throws SQLException {
        return estudianteServicio.estaActivo(idUsuario);
    }
}
