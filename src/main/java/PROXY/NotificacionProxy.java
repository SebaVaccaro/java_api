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

    // Constructor
    public NotificacionProxy() throws SQLException, Exception {
        this.notificacionServicio = new NotificacionServicio();
        this.recibeServicio = new RecibeServicio();
        this.validarUsuario = new ValidarUsuario();
    }

    // Crear notificación (solo administradores o psicopedagogos)
    public Notificacion crearNotificacion(int idInstancia, String asunto, String mensaje,
                                          String destinatario, LocalDate fecEnvio) throws SQLException, Exception {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("Solo administradores o psicopedagogos pueden crear notificaciones.");
        }
        return notificacionServicio.crearNotificacion(idInstancia, asunto, mensaje, destinatario, fecEnvio);
    }

    // Obtener notificación por ID (solo administradores, psicopedagogos o propietario)
    public Notificacion obtenerNotificacion(int id) throws SQLException, Exception {
        Notificacion notificacion = notificacionServicio.obtenerNotificacion(id);
        if (notificacion == null) {
            throw new IllegalArgumentException("No se encontró notificación para el ID especificado.");
        }

        List<Integer> usuarios = recibeServicio.listarUsuariosPorNotificacion(id);
        Integer idPropietario = usuarios.isEmpty() ? -1 : usuarios.get(0);

        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(idPropietario)) {
            throw new SecurityException("Solo administradores, psicopedagogos o el propietario pueden ver esta notificación.");
        }

        return notificacion;
    }

    // Listar todas las notificaciones (solo administradores o psicopedagogos)
    public List<Notificacion> listarTodas() throws SQLException, Exception {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("Solo administradores o psicopedagogos pueden listar notificaciones.");
        }
        return notificacionServicio.listarTodas();
    }

    // Actualizar notificación (solo administradores o psicopedagogos)
    public boolean actualizarNotificacion(Notificacion notificacion) throws SQLException, Exception {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("Solo administradores o psicopedagogos pueden actualizar notificaciones.");
        }

        // Validar que la notificación exista antes de actualizar
        Notificacion existente = notificacionServicio.obtenerNotificacion(notificacion.getIdNotificacion());
        if (existente == null) {
            throw new IllegalArgumentException("No se encontró notificación para actualizar.");
        }

        return notificacionServicio.actualizarNotificacion(notificacion);
    }

    // Desactivar notificación (solo administradores o psicopedagogos)
    public boolean desactivarNotificacion(int id) throws SQLException, Exception {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("Solo administradores o psicopedagogos pueden desactivar esta notificación.");
        }

        Notificacion notificacion = notificacionServicio.obtenerNotificacion(id);
        if (notificacion == null) {
            throw new IllegalArgumentException("No se encontró notificación para desactivar.");
        }

        return notificacionServicio.desactivarNotificacion(id);
    }
}
