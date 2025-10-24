package DAO;

import SINGLETON.ConexionSingleton;
import modelo.Recibe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecibeDAOImpl {

    private final Connection conn;

    public RecibeDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // 🔹 Insertar relación Notificación <-> Usuario
    public boolean agregar(Recibe r) throws SQLException {
        String sql = "INSERT INTO recibe (id_notificacion, id_usuario) VALUES (?, ?)";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, r.getIdNotificacion());
            psmt.setInt(2, r.getIdUsuario());
            return psmt.executeUpdate() > 0;
        }
    }

    // 🔹 Eliminar relación Notificación <-> Usuario
    public boolean eliminar(Recibe r) throws SQLException {
        String sql = "DELETE FROM recibe WHERE id_notificacion=? AND id_usuario=?";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, r.getIdNotificacion());
            psmt.setInt(2, r.getIdUsuario());
            return psmt.executeUpdate() > 0;
        }
    }

    // 🔹 Listar todas las relaciones
    public List<Recibe> listarTodos() throws SQLException {
        List<Recibe> lista = new ArrayList<>();
        String sql = "SELECT id_notificacion, id_usuario FROM recibe";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Recibe r = new Recibe(
                        rs.getInt("id_notificacion"),
                        rs.getInt("id_usuario")
                );
                lista.add(r);
            }
        }
        return lista;
    }

    // 🔹 Listar usuarios que reciben una notificación específica
    public List<Integer> listarUsuariosPorNotificacion(int idNotificacion) throws SQLException {
        List<Integer> usuarios = new ArrayList<>();
        String sql = "SELECT id_usuario FROM recibe WHERE id_notificacion=?";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, idNotificacion);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                usuarios.add(rs.getInt("id_usuario"));
            }
        }
        return usuarios;
    }

    // 🔹 Listar notificaciones recibidas por un usuario específico
    public List<Integer> listarNotificacionesPorUsuario(int idUsuario) throws SQLException {
        List<Integer> notificaciones = new ArrayList<>();
        String sql = "SELECT id_notificacion FROM recibe WHERE id_usuario=?";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, idUsuario);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                notificaciones.add(rs.getInt("id_notificacion"));
            }
        }
        return notificaciones;
    }
}
