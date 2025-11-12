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

    // Obtener estudiante por ID (solo administradores, psicopedagogo o el propio usuario)
    public Estudiante obtenerPorId(int idUsuario) throws SQLException {
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(idUsuario)) {
            throw new SecurityException("Solo administradores, psicólogos o el propio usuario pueden obtener estudiantes.");
        }
        return estudianteServicio.obtenerPorId(idUsuario);
    }

    // Listar todos los estudiantes (solo administradores o psicopedagogo)
    public List<Estudiante> listarTodos() throws SQLException {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("Solo administradores o psicopedagogo pueden listar estudiantes.");
        }
        return estudianteServicio.listarTodos();
    }

    // Actualizar estudiante (solo administradores, psicólogos o el propio usuario)
    public boolean actualizarEstudiante(Estudiante est) throws SQLException {
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(est.getIdUsuario())) {
            throw new SecurityException("Solo administradores, psicólogos o el propio usuario pueden actualizar estudiantes.");
        }
        return estudianteServicio.actualizarEstudiante(est);
    }

    // Desactivar estudiante (solo administradores)
    public boolean desactivarEstudiante(int idUsuario) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo administradores pueden desactivar estudiantes.");
        }
        return estudianteServicio.desactivarEstudiante(idUsuario);
    }

    // Verificar si un estudiante está activo (solo administradores, psicólogos o el propio usuario)
    public boolean estaActivo(int idUsuario) throws SQLException {
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(idUsuario)) {
            throw new SecurityException("Solo administradores, psicólogos o el propio usuario pueden ver su estado.");
        }
        return estudianteServicio.estaActivo(idUsuario);
    }
}

