package servicios;

import DAO.NotificacionDAOImpl;
import modelo.Notificacion;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class NotificacionServicio {

    private final NotificacionDAOImpl notificacionDAOImpl;

    // Constructor
    public NotificacionServicio() throws SQLException {
        this.notificacionDAOImpl = new NotificacionDAOImpl();
    }

    // Crear notificación
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

    // Obtener notificación por ID
    public Notificacion obtenerNotificacion(int id) throws SQLException {
        Notificacion notificacion = notificacionDAOImpl.obtenerNotificacion(id);
        if (notificacion == null) {
            throw new IllegalArgumentException("No se encontró notificación para el ID proporcionado.");
        }
        return notificacion;
    }

    // Listar todas las notificaciones activas
    public List<Notificacion> listarTodas() throws SQLException {
        return notificacionDAOImpl.listarTodas();
    }

    // Actualizar notificación existente
    public boolean actualizarNotificacion(Notificacion n) throws SQLException {
        Notificacion existente = notificacionDAOImpl.obtenerNotificacion(n.getIdNotificacion());
        if (existente == null) {
            throw new IllegalArgumentException("No se encontró notificación para actualizar.");
        }
        return notificacionDAOImpl.actualizarNotificacion(n);
    }

    // Desactivar (baja lógica) notificación
    public boolean desactivarNotificacion(int id) throws SQLException {
        Notificacion existente = notificacionDAOImpl.obtenerNotificacion(id);
        if (existente == null) {
            throw new IllegalArgumentException("No se encontró notificación para desactivar.");
        }
        return notificacionDAOImpl.eliminarNotificacion(id);
    }
}
