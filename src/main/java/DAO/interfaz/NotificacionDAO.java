package DAO.interfaz;

import modelo.Notificacion;
import java.sql.SQLException;
import java.util.List;

public interface NotificacionDAO {

    // Crear notificación
    Notificacion crearNotificacion(Notificacion n) throws SQLException;

    // Obtener notificación por ID
    Notificacion obtenerNotificacion(int id) throws SQLException;

    // Listar todas las notificaciones
    List<Notificacion> listarTodas() throws SQLException;

    // Actualizar notificación
    boolean actualizarNotificacion(Notificacion n) throws SQLException;

    // Baja lógica (desactivar notificación)
    boolean eliminarNotificacion(int id) throws SQLException;
}
