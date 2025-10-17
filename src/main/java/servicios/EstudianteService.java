package servicios;

import DAO.EstudianteDAO;
import modelo.Estudiante;

import java.sql.SQLException;
import java.util.List;

public class EstudianteService {

    private final EstudianteDAO estudianteDAO;

    public EstudianteService() throws SQLException {
        this.estudianteDAO = new EstudianteDAO();
    }

    // 🔹 Crear estudiante
    public Estudiante crearEstudiante(String cedula, String nombre, String apellido, String username,
                                      String password, String correo, int idGrupo, boolean estActivo) throws SQLException {
        Estudiante est = new Estudiante();
        est.setCedula(cedula);
        est.setNombre(nombre);
        est.setApellido(apellido);
        est.setUsername(username);
        est.setPassword(password);
        est.setCorreo(correo);
        est.setIdGrupo(idGrupo);
        est.setEstActivo(estActivo);

        return estudianteDAO.crearEstudiante(est);
    }

    // 🔹 Obtener estudiante por ID
    public Estudiante obtenerPorId(int idUsuario) throws SQLException {
        return estudianteDAO.obtenerEstudiante(idUsuario);
    }

    // 🔹 Listar todos los estudiantes
    public List<Estudiante> listarTodos() throws SQLException {
        return estudianteDAO.listarEstudiantes();
    }

    // 🔹 Actualizar estudiante
    public boolean actualizarEstudiante(int idUsuario, String cedula, String nombre, String apellido, String username,
                                        String password, String correo, int idGrupo, boolean estActivo) throws SQLException {
        Estudiante est = new Estudiante(idUsuario, cedula, nombre, apellido, username, password, correo, idGrupo, estActivo);
        return estudianteDAO.actualizarEstudiante(est);
    }

    // 🔹 Baja lógica (desactivar estudiante)
    public boolean desactivarEstudiante(int idUsuario) throws SQLException {
        return estudianteDAO.eliminarEstudiante(idUsuario);
    }

    // 🔹 Verificar si un estudiante está activo
    public boolean estaActivo(int idUsuario) throws SQLException {
        return estudianteDAO.estaActivo(idUsuario);
    }
}
