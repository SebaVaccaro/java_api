package DAO;

import SINGLETON.ConexionSingleton;
import modelo.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    private final Connection conn;

    public FuncionarioDAO() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // 🟢 Crear funcionario
    public Funcionario crearFuncionario(Funcionario f) throws SQLException {
        try {
            conn.setAutoCommit(false);

            // Insertar en usuarios
            String sqlUsuario = "INSERT INTO usuarios (cedula, nombre, apellido, username, password, correo) " +
                    "VALUES (?, ?, ?, ?, ?, ?) RETURNING id_usuario";
            try (PreparedStatement ps = conn.prepareStatement(sqlUsuario)) {
                ps.setString(1, f.getCedula());
                ps.setString(2, f.getNombre());
                ps.setString(3, f.getApellido());
                ps.setString(4, f.getUsername());
                ps.setString(5, f.getPassword());
                ps.setString(6, f.getCorreo());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    f.setIdUsuario(rs.getInt("id_usuario"));
                }
            }

            // Insertar en funcionarios
            String sqlFunc = "INSERT INTO funcionarios (id_usuario, id_rol, est_activo) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlFunc)) {
                ps.setInt(1, f.getIdUsuario());
                if (f.getIdRol() > 0) {
                    ps.setInt(2, f.getIdRol());
                } else {
                    ps.setNull(2, Types.INTEGER);
                }
                ps.setBoolean(3, f.isEstActivo());
                ps.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }

        return f;
    }

    // 🟡 Obtener funcionario por ID
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

    // 🟠 Listar todos los funcionarios
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
                Funcionario fun = new Funcionario(
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
                lista.add(fun);
            }
        }

        return lista;
    }

    // 🔵 Actualizar funcionario
    public boolean actualizarFuncionario(Funcionario f) throws SQLException {
        int filasUsuarios;
        int filasFuncionario;

        String sqlUsuario = "UPDATE usuarios SET cedula=?, nombre=?, apellido=?, username=?, password=?, correo=? WHERE id_usuario=?";
        try (PreparedStatement ps = conn.prepareStatement(sqlUsuario)) {
            ps.setString(1, f.getCedula());
            ps.setString(2, f.getNombre());
            ps.setString(3, f.getApellido());
            ps.setString(4, f.getUsername());
            ps.setString(5, f.getPassword());
            ps.setString(6, f.getCorreo());
            ps.setInt(7, f.getIdUsuario());
            filasUsuarios = ps.executeUpdate();
        }

        String sqlFunc = "UPDATE funcionarios SET id_rol=?, est_activo=? WHERE id_usuario=?";
        try (PreparedStatement ps = conn.prepareStatement(sqlFunc)) {
            if (f.getIdRol() > 0) {
                ps.setInt(1, f.getIdRol());
            } else {
                ps.setNull(1, Types.INTEGER);
            }
            ps.setBoolean(2, f.isEstActivo());
            ps.setInt(3, f.getIdUsuario());
            filasFuncionario = ps.executeUpdate();
        }

        return filasUsuarios > 0 && filasFuncionario > 0;
    }

    // 🔴 Baja lógica
    public boolean eliminarFuncionario(int idUsuario) throws SQLException {
        String sql = "UPDATE funcionarios SET est_activo = false WHERE id_usuario = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        }
    }

    // ⚪ Verificar si un funcionario está activo
    public boolean estaActivo(int idUsuario) throws SQLException {
        String sql = "SELECT est_activo FROM funcionarios WHERE id_usuario = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("est_activo");
            }
        }
        return false;
    }
}
