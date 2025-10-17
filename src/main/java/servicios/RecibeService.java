package servicios;

import DAO.RecibeDAO;
import modelo.Recibe;

import java.sql.SQLException;
import java.util.List;

public class RecibeService {

    private final RecibeDAO dao;

    public RecibeService() throws SQLException {
        this.dao = new RecibeDAO();
    }

    // 🔹 Agregar relación Notificación ↔ Usuario
    public boolean agregarRecibe(int idNotificacion, int idUsuario) throws SQLException {
        if (idNotificacion <= 0 || idUsuario <= 0) {
            throw new IllegalArgumentException("ID de notificación o usuario inválido.");
        }
        Recibe r = new Recibe(idNotificacion, idUsuario);
        return dao.agregar(r);
    }

    // 🔹 Eliminar relación Notificación ↔ Usuario
    public boolean eliminarRecibe(int idNotificacion, int idUsuario) throws SQLException {
        if (idNotificacion <= 0 || idUsuario <= 0) {
            throw new IllegalArgumentException("ID de notificación o usuario inválido.");
        }
        Recibe r = new Recibe(idNotificacion, idUsuario);
        return dao.eliminar(r);
    }

    // 🔹 Listar todas las relaciones
    public List<Recibe> listarTodos() throws SQLException {
        return dao.listarTodos();
    }

    // 🔹 Listar usuarios que reciben una notificación específica
    public List<Integer> listarUsuariosPorNotificacion(int idNotificacion) throws SQLException {
        if (idNotificacion <= 0) {
            throw new IllegalArgumentException("ID de notificación inválido.");
        }
        return dao.listarUsuariosPorNotificacion(idNotificacion);
    }

    // 🔹 Listar notificaciones recibidas por un usuario específico
    public List<Integer> listarNotificacionesPorUsuario(int idUsuario) throws SQLException {
        if (idUsuario <= 0) {
            throw new IllegalArgumentException("ID de usuario inválido.");
        }
        return dao.listarNotificacionesPorUsuario(idUsuario);
    }
}
