package DAO;

import SINGLETON.ConexionSingleton;
import modelo.Rol;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolDAOImpl {

    private final Connection conn;

    public RolDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // 🔹 Crear un nuevo rol
    public boolean agregar(Rol rol) throws SQLException {
        String sql = "INSERT INTO roles (nombre, est_activo) VALUES (?, ?)";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setString(1, rol.getNombre());
            psmt.setBoolean(2, rol.isEstActivo());
            return psmt.executeUpdate() > 0;
        }
    }

    // 🔹 Actualizar un rol existente
    public boolean actualizar(Rol rol) throws SQLException {
        String sql = "UPDATE roles SET nombre=?, est_activo=? WHERE id_rol=?";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setString(1, rol.getNombre());
            psmt.setBoolean(2, rol.isEstActivo());
            psmt.setInt(3, rol.getIdRol());
            return psmt.executeUpdate() > 0;
        }
    }

    // 🔹 Eliminar un rol (opcional, según est_activo o físico)
    public boolean eliminar(int idRol) throws SQLException {
        String sql = "DELETE FROM roles WHERE id_rol=?";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, idRol);
            return psmt.executeUpdate() > 0;
        }
    }

    // 🔹 Buscar rol por ID
    public Rol buscarPorId(int idRol) throws SQLException {
        String sql = "SELECT id_rol, nombre, est_activo FROM roles WHERE id_rol=?";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, idRol);
            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                return new Rol(
                        rs.getInt("id_rol"),
                        rs.getString("nombre"),
                        rs.getBoolean("est_activo")
                );
            }
        }
        return null;
    }

    // 🔹 Listar todos los roles
    public List<Rol> listarTodos() throws SQLException {
        List<Rol> lista = new ArrayList<>();
        String sql = "SELECT id_rol, nombre, est_activo FROM roles";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Rol rol = new Rol(
                        rs.getInt("id_rol"),
                        rs.getString("nombre"),
                        rs.getBoolean("est_activo")
                );
                lista.add(rol);
            }
        }
        return lista;
    }
}
