package DAO;

import SINGLETON.ConexionSingleton;
import modelo.Notificacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificacionDAO {

    private final Connection conn;

    public NotificacionDAO() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // 🔹 Crear notificación
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

    // 🔹 Obtener notificación por id
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

    // 🔹 Listar todas las notificaciones
    public List<Notificacion> listarTodas() throws SQLException {
        List<Notificacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM notificaciones";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Notificacion n = new Notificacion(
                        rs.getInt("id_notificacion"),
                        rs.getInt("id_instancia"),
                        rs.getString("asunto"),
                        rs.getString("mensaje"),
                        rs.getString("destinatario"),
                        rs.getDate("fec_envio").toLocalDate(),
                        rs.getBoolean("est_activo")
                );
                lista.add(n);
            }
        }
        return lista;
    }

    // 🔹 Actualizar notificación
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

    // 🔹 Baja lógica (desactivar notificación)
    public boolean eliminarNotificacion(int id) throws SQLException {
        String sql = "UPDATE notificaciones SET est_activo=false WHERE id_notificacion=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
