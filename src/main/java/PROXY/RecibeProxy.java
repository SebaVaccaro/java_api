package PROXY;

import modelo.Recibe;
import servicios.RecibeServicio;

import java.sql.SQLException;
import java.util.List;

public class RecibeProxy {

    private final RecibeServicio recibeServicio;

    public RecibeProxy() throws SQLException {
        this.recibeServicio = new RecibeServicio();
    }

    // ============================================================
    // AGREGAR RELACIÓN NOTIFICACIÓN ↔ USUARIO
    // ============================================================
    public boolean agregarRecibe(int idNotificacion, int idUsuario) throws SQLException {
        return recibeServicio.agregarRecibe(idNotificacion, idUsuario);
    }

    // ============================================================
    // ELIMINAR RELACIÓN NOTIFICACIÓN ↔ USUARIO
    // ============================================================
    public boolean eliminarRecibe(int idNotificacion, int idUsuario) throws SQLException {
        return recibeServicio.eliminarRecibe(idNotificacion, idUsuario);
    }

    // ============================================================
    // LISTAR TODAS LAS RELACIONES
    // ============================================================
    public List<Recibe> listarTodos() throws SQLException {
        return recibeServicio.listarTodos();
    }

    // ============================================================
    // LISTAR USUARIOS QUE RECIBEN UNA NOTIFICACIÓN
    // ============================================================
    public List<Integer> listarUsuariosPorNotificacion(int idNotificacion) throws SQLException {
        return recibeServicio.listarUsuariosPorNotificacion(idNotificacion);
    }

    // ============================================================
    // LISTAR NOTIFICACIONES RECIBIDAS POR UN USUARIO
    // ============================================================
    public List<Integer> listarNotificacionesPorUsuario(int idUsuario) throws SQLException {
        return recibeServicio.listarNotificacionesPorUsuario(idUsuario);
    }
}
