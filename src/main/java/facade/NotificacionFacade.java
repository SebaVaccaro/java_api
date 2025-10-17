package facade;

import modelo.Notificacion;
import servicios.NotificacionService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class NotificacionFacade {

    private final NotificacionService notificacionService;

    public NotificacionFacade() throws SQLException {
        this.notificacionService = new NotificacionService();
    }

    // ============================================================
    // CREAR NOTIFICACIÓN
    // ============================================================
    public Notificacion crearNotificacion(int idInstancia, String asunto, String mensaje,
                                          String destinatario, LocalDate fecEnvio) throws SQLException {
        return notificacionService.crearNotificacion(idInstancia, asunto, mensaje, destinatario, fecEnvio);
    }

    // ============================================================
    // OBTENER NOTIFICACIÓN
    // ============================================================
    public Notificacion obtenerNotificacion(int id) throws SQLException {
        return notificacionService.obtenerNotificacion(id);
    }

    // ============================================================
    // LISTAR NOTIFICACIONES
    // ============================================================
    public List<Notificacion> listarTodas() throws SQLException {
        return notificacionService.listarTodas();
    }

    // ============================================================
    // ACTUALIZAR NOTIFICACIÓN
    // ============================================================
    public boolean actualizarNotificacion(Notificacion notificacion) throws SQLException {
        return notificacionService.actualizarNotificacion(notificacion);
    }

    // ============================================================
    // BAJA LÓGICA DE NOTIFICACIÓN
    // ============================================================
    public boolean desactivarNotificacion(int id) throws SQLException {
        return notificacionService.desactivarNotificacion(id);
    }
}
