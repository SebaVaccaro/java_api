package facade;

import modelo.Recibe;
import servicios.RecibeService;

import java.sql.SQLException;
import java.util.List;

public class RecibeFacade {

    private final RecibeService recibeService;

    public RecibeFacade() throws SQLException {
        this.recibeService = new RecibeService();
    }

    // ============================================================
    // AGREGAR RELACIÓN NOTIFICACIÓN ↔ USUARIO
    // ============================================================
    public boolean agregarRecibe(int idNotificacion, int idUsuario) throws SQLException {
        return recibeService.agregarRecibe(idNotificacion, idUsuario);
    }

    // ============================================================
    // ELIMINAR RELACIÓN NOTIFICACIÓN ↔ USUARIO
    // ============================================================
    public boolean eliminarRecibe(int idNotificacion, int idUsuario) throws SQLException {
        return recibeService.eliminarRecibe(idNotificacion, idUsuario);
    }

    // ============================================================
    // LISTAR TODAS LAS RELACIONES
    // ============================================================
    public List<Recibe> listarTodos() throws SQLException {
        return recibeService.listarTodos();
    }

    // ============================================================
    // LISTAR USUARIOS QUE RECIBEN UNA NOTIFICACIÓN
    // ============================================================
    public List<Integer> listarUsuariosPorNotificacion(int idNotificacion) throws SQLException {
        return recibeService.listarUsuariosPorNotificacion(idNotificacion);
    }

    // ============================================================
    // LISTAR NOTIFICACIONES RECIBIDAS POR UN USUARIO
    // ============================================================
    public List<Integer> listarNotificacionesPorUsuario(int idUsuario) throws SQLException {
        return recibeService.listarNotificacionesPorUsuario(idUsuario);
    }
}
