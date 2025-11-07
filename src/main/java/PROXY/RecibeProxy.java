package PROXY;

import modelo.Recibe;
import servicios.RecibeServicio;
import utils.ValidarUsuario;

import java.sql.SQLException;
import java.util.List;

public class RecibeProxy {

    private final RecibeServicio recibeServicio;
    private final ValidarUsuario validarUsuario;

    public RecibeProxy() throws SQLException {
        this.recibeServicio = new RecibeServicio();
        this.validarUsuario = new ValidarUsuario();
    }


    // AGREGAR RELACIÓN NOTIFICACIÓN ↔ USUARIO
    public boolean agregarRecibe(int idNotificacion, int idUsuario) throws SQLException {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo un administrador puede agregar relaciones notificación–usuario.");
        }
        return recibeServicio.agregarRecibe(idNotificacion, idUsuario);
    }


    // ELIMINAR RELACIÓN NOTIFICACIÓN ↔ USUARIO
    public boolean eliminarRecibe(int idNotificacion, int idUsuario) throws SQLException {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo un administrador puede eliminar relaciones notificación–usuario.");
        }
        return recibeServicio.eliminarRecibe(idNotificacion, idUsuario);
    }

    // LISTAR TODAS LAS RELACIONES
    public List<Recibe> listarTodos() throws SQLException {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo un administrador puede listar todas las relaciones notificación–usuario.");
        }
        return recibeServicio.listarTodos();
    }

    // LISTAR USUARIOS QUE RECIBEN UNA NOTIFICACIÓN
    public List<Integer> listarUsuariosPorNotificacion(int idNotificacion) throws SQLException {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo un administrador puede consultar los usuarios asociados a una notificación.");
        }
        return recibeServicio.listarUsuariosPorNotificacion(idNotificacion);
    }

    // LISTAR NOTIFICACIONES RECIBIDAS POR UN USUARIO
    public List<Integer> listarNotificacionesPorUsuario(int idUsuario) throws SQLException {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo un administrador puede consultar las notificaciones asociadas a un usuario.");
        }
        return recibeServicio.listarNotificacionesPorUsuario(idUsuario);
    }
}

