package DAO;

import DAO.interfaz.NotificacionDAO;
import SINGLETON.ConexionSingleton;
import modelo.Notificacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del DAO para la entidad Notificacion.
 * Gestiona las operaciones CRUD sobre la tabla 'notificaciones',
 * que almacena los mensajes generados dentro del sistema.
 */
public class NotificacionDAOImpl implements NotificacionDAO {

    // Conexión única a la base de datos mediante el Singleton
    private final Connection conn;

    // Constructor: obtiene la conexión desde el Singleton
    public NotificacionDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // Crear una nueva notificación en la base de datos
    @Override
    public Notificacion crearNotificacion(Notificacion n) throws SQLException {
        String sql = "INSERT INTO notificaciones (id_instancia, asunto, mensaje, destinatario, fec_envio, est_activo) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING id_notificacion";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, n.getIdInstancia());
            ps.setString(2, n.getAsunto());
            ps.setString(3, n.getMensaje());
            ps.setString(4, n.getDestinatario());
            ps.setDate(5, Date.valueOf(n.getFecEnvio()));
            ps.setBoolean(6, n.isEstActivo());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                n.setIdNotificacion(rs.getInt("id_notificacion"));
            }
        }
        return n;
    }

    // Obtener una notificación específica por su ID
    @Override
    public Notificacion obtenerNotificacion(int id) throws SQLException {
        String sql = "SELECT * FROM notificaciones WHERE id_notificacion = ?";
        Notificacion n = null;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                n = new Notificacion(
                        rs.getInt("id_notificacion"),
                        rs.getInt("id_instancia"),
                        rs.getString("asunto"),
                        rs.getString("mensaje"),
                        rs.getString("destinatario"),
                        rs.getDate("fec_envio").toLocalDate(),
                        rs.getBoolean("est_activo")
                );
            }
        }
        return n;
    }

    // Listar todas las notificaciones registradas
    @Override
    public List<Notificacion> listarTodas() throws SQLException {
        List<Notificacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM notificaciones";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lista.add(new Notificacion(
                        rs.getInt("id_notificacion"),
                        rs.getInt("id_instancia"),
                        rs.getString("asunto"),
                        rs.getString("mensaje"),
                        rs.getString("destinatario"),
                        rs.getDate("fec_envio").toLocalDate(),
                        rs.getBoolean("est_activo")
                ));
            }
        }
        return lista;
    }

    // Actualizar los datos de una notificación existente
    @Override
    public boolean actualizarNotificacion(Notificacion n) throws SQLException {
        String sql = "UPDATE notificaciones SET id_instancia=?, asunto=?, mensaje=?, destinatario=?, fec_envio=?, est_activo=? " +
                "WHERE id_notificacion=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, n.getIdInstancia());
            ps.setString(2, n.getAsunto());
            ps.setString(3, n.getMensaje());
            ps.setString(4, n.getDestinatario());
            ps.setDate(5, Date.valueOf(n.getFecEnvio()));
            ps.setBoolean(6, n.isEstActivo());
            ps.setInt(7, n.getIdNotificacion());
            return ps.executeUpdate() > 0;
        }
    }

    // Realizar una baja lógica de una notificación (est_activo = false)
    @Override
    public boolean eliminarNotificacion(int id) throws SQLException {
        String sql = "UPDATE notificaciones SET est_activo=false WHERE id_notificacion=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
