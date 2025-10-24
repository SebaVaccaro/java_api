package DAO.interfaz;

import modelo.Estudiante;
import java.sql.SQLException;
import java.util.List;

public interface EstudianteDAO {
    /**
     * Inserta un estudiante en la base de datos.
     */
    void insertarEstudiante(Estudiante est) throws SQLException;

    /**
     * Obtiene un estudiante por su ID de usuario.
     */
    Estudiante obtenerEstudiante(int idUsuario) throws SQLException;

    /**
     * Lista todos los estudiantes.
     */
    List<Estudiante> listarEstudiantes() throws SQLException;

    /**
     * Actualiza los datos de un estudiante.
     */
    boolean actualizarEstudiante(Estudiante est) throws SQLException;

    /**
     * Baja lógica del estudiante.
     */
    boolean eliminarEstudiante(int idUsuario) throws SQLException;

    /**
     * Verifica si el estudiante está activo.
     */
    boolean estaActivo(int idUsuario) throws SQLException;
}