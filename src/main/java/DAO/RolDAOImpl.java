package DAO;

import DAO.interfaz.RolDAO;
import SINGLETON.ConexionSingleton;
import modelo.Rol;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolDAOImpl implements RolDAO {

    private final Connection conn;

    public RolDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    @Override
    public boolean agregar(Rol rol) throws SQLException {
        String sql = "INSERT INTO roles (nombre, est_activo) VALUES (?, ?)";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setString(1, rol.getNombre());
            psmt.setBoolean(2, rol.isEstActivo());
            return psmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean actualizar(Rol rol) throws SQLException {
        String sql = "UPDATE roles SET nombre=?, est_activo=? WHERE id_rol=?";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setString(1, rol.getNombre());
            psmt.setBoolean(2, rol.isEstActivo());
            psmt.setInt(3, rol.getIdRol());
            return psmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int idRol) throws SQLException {
        String sql = "DELETE FROM roles WHERE id_rol=?";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, idRol);
            return psmt.executeUpdate() > 0;
        }
    }

    @Override
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

    @Override
    public List<Rol> listarTodos() throws SQLException {
        List<Rol> lista = new ArrayList<>();
        String sql = "SELECT id_rol, nombre, est_activo FROM roles";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lista.add(new Rol(
                        rs.getInt("id_rol"),
                        rs.getString("nombre"),
                        rs.getBoolean("est_activo")
                ));
            }
        }
        return lista;
    }
}
