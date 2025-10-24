package DAO.interfaz;

import modelo.Recibe;
import java.sql.SQLException;
import java.util.List;

public interface RecibeDAO {

    // Insertar relación Notificación <-> Usuario
    boolean agregar(Recibe r) throws SQLException;

    // Eliminar relación Notificación <-> Usuario
    boolean eliminar(Recibe r) throws SQLException;

    // Listar todas las relaciones
    List<Recibe> listarTodos() throws SQLException;

    // Listar usuarios que reciben una notificación específica
    List<Integer> listarUsuariosPorNotificacion(int idNotificacion) throws SQLException;

    // Listar notificaciones recibidas por un usuario específico
    List<Integer> listarNotificacionesPorUsuario(int idUsuario) throws SQLException;
}
