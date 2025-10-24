package DAO;

import DAO.interfaz.FuncionarioDAO;
import modelo.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAOImpl implements FuncionarioDAO {

    private final Connection conn;

    public FuncionarioDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertarFuncionario(Funcionario f) throws SQLException {
        String sql = "INSERT INTO funcionarios (id_usuario, id_rol, est_activo) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, f.getIdUsuario());
            if (f.getIdRol() > 0) ps.setInt(2, f.getIdRol());
            else ps.setNull(2, Types.INTEGER);
            ps.setBoolean(3, f.isEstActivo());
            ps.executeUpdate();
        }
    }

    @Override
    public Funcionario obtenerFuncionario(int idUsuario) throws SQLException {
        String sql = """
            SELECT u.id_usuario, u.cedula, u.nombre, u.apellido, u.username, u.password, u.correo,
                   f.id_rol, f.est_activo
            FROM usuarios u
            JOIN funcionarios f ON u.id_usuario = f.id_usuario
            WHERE u.id_usuario = ?
        """;
        Funcionario fun = null;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                fun = new Funcionario(
                        rs.getInt("id_usuario"),
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("correo"),
                        rs.getInt("id_rol"),
                        rs.getBoolean("est_activo")
                );
            }
        }
        return fun;
    }

    @Override
    public List<Funcionario> listarFuncionarios() throws SQLException {
        List<Funcionario> lista = new ArrayList<>();
        String sql = """
            SELECT u.id_usuario, u.cedula, u.nombre, u.apellido, u.username, u.password, u.correo,
                   f.id_rol, f.est_activo
            FROM usuarios u
            JOIN funcionarios f ON u.id_usuario = f.id_usuario
        """;
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lista.add(new Funcionario(
                        rs.getInt("id_usuario"),
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("correo"),
                        rs.getInt("id_rol"),
                        rs.getBoolean("est_activo")
                ));
            }
        }
        return lista;
    }

    @Override
    public boolean actualizarFuncionario(Funcionario f) throws SQLException {
        String sql = "UPDATE funcionarios SET id_rol=?, est_activo=? WHERE id_usuario=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            if (f.getIdRol() > 0) ps.setInt(1, f.getIdRol());
            else ps.setNull(1, Types.INTEGER);
            ps.setBoolean(2, f.isEstActivo());
            ps.setInt(3, f.getIdUsuario());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminarFuncionario(int idUsuario) throws SQLException {
        String sql = "UPDATE funcionarios SET est_activo=false WHERE id_usuario=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean estaActivo(int idUsuario) throws SQLException {
        String sql = "SELECT est_activo FROM funcionarios WHERE id_usuario=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getBoolean("est_activo");
        }
    }
}