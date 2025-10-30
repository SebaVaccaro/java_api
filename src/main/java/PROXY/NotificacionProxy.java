package PROXY;

import modelo.Notificacion;
import servicios.NotificacionServicio;
import servicios.RecibeServicio;
import utils.ValidarUsuario;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class NotificacionProxy {

    private final NotificacionServicio notificacionServicio;
    private final RecibeServicio recibeServicio;
    private final ValidarUsuario validarUsuario;

    public NotificacionProxy() throws SQLException, Exception {
        this.notificacionServicio = new NotificacionServicio();
        this.recibeServicio = new RecibeServicio(); // Ya existente
        this.validarUsuario = new ValidarUsuario();
    }

    public Notificacion crearNotificacion(int idInstancia, String asunto, String mensaje,
                                          String destinatario, LocalDate fecEnvio) throws SQLException, Exception {
        if (!validarUsuario.esAdministrador() && !validarUsuario.esPsicopedagogo()) {
            throw new SecurityException("No tienes permiso para crear notificaciones.");
        }
        return notificacionServicio.crearNotificacion(idInstancia, asunto, mensaje, destinatario, fecEnvio);
    }

    public Notificacion obtenerNotificacion(int id) throws SQLException, Exception {
        Notificacion notificacion = notificacionServicio.obtenerNotificacion(id);
        if (notificacion == null) return null;

        // Obtenemos la lista de usuarios que reciben la notificación
        List<Integer> usuarios = recibeServicio.listarUsuariosPorNotificacion(id);
        Integer idPropietario = usuarios.isEmpty() ? -1 : usuarios.get(0);

        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(idPropietario)) {
            throw new SecurityException("No tienes permiso para ver esta notificación.");
        }

        return notificacion;
    }

    public List<Notificacion> listarTodas() throws SQLException, Exception {
        if (!validarUsuario.esAdministrador() && !validarUsuario.esPsicopedagogo()) {
            throw new SecurityException("No tienes permiso para listar notificaciones.");
        }
        return notificacionServicio.listarTodas();
    }

    public boolean actualizarNotificacion(Notificacion notificacion) throws SQLException, Exception {
        if (!validarUsuario.esAdministrador() && !validarUsuario.esPsicopedagogo()) {
            throw new SecurityException("No tienes permiso para actualizar notificaciones.");
        }
        return notificacionServicio.actualizarNotificacion(notificacion);
    }

    public boolean desactivarNotificacion(int id) throws SQLException, Exception {
        Notificacion notificacion = notificacionServicio.obtenerNotificacion(id);
        if (notificacion == null) return false;

        List<Integer> usuarios = recibeServicio.listarUsuariosPorNotificacion(id);
        Integer idPropietario = usuarios.isEmpty() ? -1 : usuarios.get(0);

        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(idPropietario)) {
            throw new SecurityException("No tienes permiso para desactivar esta notificación.");
        }

        return notificacionServicio.desactivarNotificacion(id);
    }
}
