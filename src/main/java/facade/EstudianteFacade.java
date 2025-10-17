package facade;

import modelo.Estudiante;
import servicios.EstudianteService;

import java.sql.SQLException;
import java.util.List;

public class EstudianteFacade {

    private final EstudianteService estudianteService;

    public EstudianteFacade() throws SQLException {
        this.estudianteService = new EstudianteService();
    }

    // ============================================================
    // CREAR ESTUDIANTE
    // ============================================================
    public Estudiante crearEstudiante(String cedula, String nombre, String apellido, String username,
                                      String password, String correo, int idGrupo, boolean estActivo) throws SQLException {
        return estudianteService.crearEstudiante(cedula, nombre, apellido, username, password, correo, idGrupo, estActivo);
    }

    // ============================================================
    // OBTENER ESTUDIANTE
    // ============================================================
    public Estudiante obtenerPorId(int idUsuario) throws SQLException {
        return estudianteService.obtenerPorId(idUsuario);
    }

    // ============================================================
    // LISTAR ESTUDIANTES
    // ============================================================
    public List<Estudiante> listarTodos() throws SQLException {
        return estudianteService.listarTodos();
    }

    // ============================================================
    // ACTUALIZAR ESTUDIANTE
    // ============================================================
    public boolean actualizarEstudiante(int idUsuario, String cedula, String nombre, String apellido, String username,
                                        String password, String correo, int idGrupo, boolean estActivo) throws SQLException {
        return estudianteService.actualizarEstudiante(idUsuario, cedula, nombre, apellido, username, password, correo, idGrupo, estActivo);
    }

    // ============================================================
    // BAJA LÓGICA (DESACTIVAR ESTUDIANTE)
    // ============================================================
    public boolean desactivarEstudiante(int idUsuario) throws SQLException {
        return estudianteService.desactivarEstudiante(idUsuario);
    }

    // ============================================================
    // VERIFICAR ESTADO
    // ============================================================
    public boolean estaActivo(int idUsuario) throws SQLException {
        return estudianteService.estaActivo(idUsuario);
    }
}
