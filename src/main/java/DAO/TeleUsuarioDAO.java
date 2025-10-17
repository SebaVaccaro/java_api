package DAO;

import SINGLETON.ConexionSingleton;
import modelo.TeleUsuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeleUsuarioDAO {

    private final Connection conn;

    public TeleUsuarioDAO() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // 🔹 Crear un TeleUsuario
    public TeleUsuario crearTeleUsuario(TeleUsuario teleUsuario) throws SQLException {
        String sql = "INSERT INTO tele_usuario (numero, id_usuario) VALUES (?, ?) RETURNING id_telefono";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, teleUsuario.getNumero());
            ps.setInt(2, teleUsuario.getIdUsuario());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                teleUsuario.setIdTelefono(rs.getInt("id_telefono"));
            }
        }
        return teleUsuario;
    }

    // 🔹 Obtener TeleUsuario por id
    public TeleUsuario obtenerTeleUsuario(int idTelefono) throws SQLException {
        String sql = "SELECT * FROM tele_usuario WHERE id_telefono = ?";
        TeleUsuario teleUsuario = null;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idTelefono);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                teleUsuario = new TeleUsuario(
                        rs.getInt("id_telefono"),
                        rs.getString("numero"),
                        rs.getInt("id_usuario")
                );
            }
        }
        return teleUsuario;
    }

    // 🔹 Listar todos los TeleUsuarios
    public List<TeleUsuario> listarTodos() throws SQLException {
        List<TeleUsuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM tele_usuario";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                TeleUsuario teleUsuario = new TeleUsuario(
                        rs.getInt("id_telefono"),
                        rs.getString("numero"),
                        rs.getInt("id_usuario")
                );
                lista.add(teleUsuario);
            }
        }
        return lista;
    }

    // 🔹 Eliminar TeleUsuario por id
    public boolean eliminarTeleUsuario(int idTelefono) throws SQLException {
        String sql = "DELETE FROM tele_usuario WHERE id_telefono = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idTelefono);
            return ps.executeUpdate() > 0;
        }
    }

    // 🔹 Actualizar TeleUsuario
    public boolean actualizarTeleUsuario(TeleUsuario teleUsuario) throws SQLException {
        String sql = "UPDATE tele_usuario SET numero = ?, id_usuario = ? WHERE id_telefono = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, teleUsuario.getNumero());
            ps.setInt(2, teleUsuario.getIdUsuario());
            ps.setInt(3, teleUsuario.getIdTelefono());
            return ps.executeUpdate() > 0;
        }
    }
}
