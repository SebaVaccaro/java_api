package servicios;

import DAO.RecibeDAOImpl;
import DAO.NotificacionDAOImpl;
import DAO.EstudianteDAOImpl;
import DAO.FuncionarioDAOImpl;
import SINGLETON.ConexionSingleton;
import modelo.Recibe;
import modelo.Notificacion;
import modelo.Estudiante;
import modelo.Funcionario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RecibeServicio {

    private final RecibeDAOImpl dao;
    private final NotificacionDAOImpl notificacionDAO;
    private final EstudianteDAOImpl estudianteDAO;
    private final FuncionarioDAOImpl funcionarioDAO;

    public RecibeServicio() throws SQLException {
        this.dao = new RecibeDAOImpl();
        this.notificacionDAO = new NotificacionDAOImpl();
        this.estudianteDAO = new EstudianteDAOImpl();

        Connection conn = ConexionSingleton.getInstance().getConexion();
        this.funcionarioDAO = new FuncionarioDAOImpl(conn);
    }

    // Agregar relación Notificación ↔ Usuario
    public boolean agregarRecibe(int idNotificacion, int idUsuario) throws SQLException {

        if (idNotificacion <= 0) {
            throw new IllegalArgumentException("ID de notificación inválido: debe ser mayor que 0.");
        }
        if (idUsuario <= 0) {
            throw new IllegalArgumentException("ID de usuario inválido: debe ser mayor que 0.");
        }

        Notificacion notificacion = notificacionDAO.obtenerNotificacion(idNotificacion);
        if (notificacion == null) {
            throw new IllegalArgumentException("La notificación con ID " + idNotificacion + " no existe.");
        }

        if (!notificacion.isEstActivo()) {
            throw new IllegalArgumentException("La notificación con ID " + idNotificacion + " no está activa.");
        }

        boolean usuarioExiste = validarUsuarioExiste(idUsuario);
        if (!usuarioExiste) {
            throw new IllegalArgumentException("El usuario con ID " + idUsuario + " no existe como estudiante ni como funcionario.");
        }

        if (!validarUsuarioActivo(idUsuario)) {
            throw new IllegalArgumentException("El usuario con ID " + idUsuario + " no está activo.");
        }

        List<Integer> usuarios = dao.listarUsuariosPorNotificacion(idNotificacion);
        if (usuarios.contains(idUsuario)) {
            throw new IllegalArgumentException("El usuario " + idUsuario +
                    " ya tiene asignada la notificación " + idNotificacion + ".");
        }

        Recibe r = new Recibe(idNotificacion, idUsuario);
        return dao.agregar(r);
    }

    // Eliminar relación Notificación ↔ Usuario
    public boolean eliminarRecibe(int idNotificacion, int idUsuario) throws SQLException {
        if (idNotificacion <= 0) {
            throw new IllegalArgumentException("ID de notificación inválido: debe ser mayor que 0.");
        }
        if (idUsuario <= 0) {
            throw new IllegalArgumentException("ID de usuario inválido: debe ser mayor que 0.");
        }

        Notificacion notificacion = notificacionDAO.obtenerNotificacion(idNotificacion);
        if (notificacion == null) {
            throw new IllegalArgumentException("La notificación con ID " + idNotificacion + " no existe.");
        }

        boolean usuarioExiste = validarUsuarioExiste(idUsuario);
        if (!usuarioExiste) {
            throw new IllegalArgumentException("El usuario con ID " + idUsuario + " no existe como estudiante ni como funcionario.");
        }

        List<Integer> usuarios = dao.listarUsuariosPorNotificacion(idNotificacion);
        if (!usuarios.contains(idUsuario)) {
            throw new IllegalArgumentException("El usuario " + idUsuario +
                    " no tiene asignada la notificación " + idNotificacion + ".");
        }

        Recibe r = new Recibe(idNotificacion, idUsuario);
        return dao.eliminar(r);
    }

    // Listar todas las relaciones
    public List<Recibe> listarTodos() throws SQLException {
        return dao.listarTodos();
    }

    // Listar usuarios que reciben una notificación específica
    public List<Integer> listarUsuariosPorNotificacion(int idNotificacion) throws SQLException {
        if (idNotificacion <= 0) {
            throw new IllegalArgumentException("ID de notificación inválido: debe ser mayor que 0.");
        }

        Notificacion notificacion = notificacionDAO.obtenerNotificacion(idNotificacion);
        if (notificacion == null) {
            throw new IllegalArgumentException("La notificación con ID " + idNotificacion + " no existe.");
        }

        return dao.listarUsuariosPorNotificacion(idNotificacion);
    }

    // Listar notificaciones recibidas por un usuario específico
    public List<Integer> listarNotificacionesPorUsuario(int idUsuario) throws SQLException {
        if (idUsuario <= 0) {
            throw new IllegalArgumentException("ID de usuario inválido: debe ser mayor que 0.");
        }

        boolean usuarioExiste = validarUsuarioExiste(idUsuario);
        if (!usuarioExiste) {
            throw new IllegalArgumentException("El usuario con ID " + idUsuario + " no existe como estudiante ni como funcionario.");
        }

        return dao.listarNotificacionesPorUsuario(idUsuario);
    }


    private boolean validarUsuarioExiste(int idUsuario) throws SQLException {
        Estudiante estudiante = estudianteDAO.obtenerEstudiante(idUsuario);
        if (estudiante != null) {
            return true;
        }

        Funcionario funcionario = funcionarioDAO.obtenerFuncionario(idUsuario);
        return funcionario != null;
    }

    private boolean validarUsuarioActivo(int idUsuario) throws SQLException {

        Estudiante estudiante = estudianteDAO.obtenerEstudiante(idUsuario);
        if (estudiante != null) {
            return estudiante.isActivo();
        }


        Funcionario funcionario = funcionarioDAO.obtenerFuncionario(idUsuario);
        if (funcionario != null) {
            return funcionario.isActivo();
        }

        return false;
    }
}