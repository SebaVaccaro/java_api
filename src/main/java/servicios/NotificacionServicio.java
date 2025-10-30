package servicios;

import DAO.NotificacionDAOImpl;
import modelo.Notificacion;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class NotificacionServicio {

    private final NotificacionDAOImpl notificacionDAOImpl;

    public NotificacionServicio() throws SQLException {
        this.notificacionDAOImpl = new NotificacionDAOImpl();
    }

    // 🔹 Crear notificación con validaciones simples
    public Notificacion crearNotificacion(int idInstancia, String asunto, String mensaje, String destinatario, LocalDate fecEnvio) throws SQLException {
        if (asunto == null || asunto.isBlank()) {
            throw new IllegalArgumentException("El asunto no puede estar vacío.");
        }
        if (mensaje == null || mensaje.isBlank()) {
            throw new IllegalArgumentException("El mensaje no puede estar vacío.");
        }
        if (destinatario == null || destinatario.isBlank()) {
            throw new IllegalArgumentException("El destinatario no puede estar vacío.");
        }
        if (fecEnvio == null) {
            fecEnvio = LocalDate.now();
        }

        Notificacion n = new Notificacion(idInstancia, asunto, mensaje, destinatario, fecEnvio, true);
        return notificacionDAOImpl.crearNotificacion(n);
    }

    // 🔹 Obtener notificación por id
    public Notificacion obtenerNotificacion(int id) throws SQLException {
        return notificacionDAOImpl.obtenerNotificacion(id);
    }

    // 🔹 Listar todas las notificaciones activas
    public List<Notificacion> listarTodas() throws SQLException {
        return notificacionDAOImpl.listarTodas();
    }

    // 🔹 Actualizar notificación
    public boolean actualizarNotificacion(Notificacion n) throws SQLException {
        if (n.getIdNotificacion() <= 0) {
            throw new IllegalArgumentException("ID de notificación inválido.");
        }
        return notificacionDAOImpl.actualizarNotificacion(n);
    }

    // 🔹 Baja lógica de notificación
    public boolean desactivarNotificacion(int id) throws SQLException {
        return notificacionDAOImpl.eliminarNotificacion(id);
    }
}
