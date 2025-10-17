package DAO;

import SINGLETON.ConexionSingleton;
import modelo.Estudiante;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {

    private final Connection conn;

    public EstudianteDAO() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // 🟢 Crear estudiante (inserta en usuarios y estudiantes)
    public Estudiante crearEstudiante(Estudiante est) throws SQLException {
        try {
            conn.setAutoCommit(false);

            // Insertamos primero en usuarios
            String sqlUsuario = "INSERT INTO usuarios (cedula, nombre, apellido, username, password, correo) " +
                    "VALUES (?, ?, ?, ?, ?, ?) RETURNING id_usuario";
            try (PreparedStatement ps = conn.prepareStatement(sqlUsuario)) {
                ps.setString(1, est.getCedula());
                ps.setString(2, est.getNombre());
                ps.setString(3, est.getApellido());
                ps.setString(4, est.getUsername());
                ps.setString(5, est.getPassword());
                ps.setString(6, est.getCorreo());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    est.setIdUsuario(rs.getInt("id_usuario"));
                }
            }

            // Luego insertamos en estudiantes
            String sqlEst = "INSERT INTO estudiantes (id_usuario, id_grupo, est_activo) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlEst)) {
                ps.setInt(1, est.getIdUsuario());
                if (est.getIdGrupo() > 0) {
                    ps.setInt(2, est.getIdGrupo());
                } else {
                    ps.setNull(2, Types.INTEGER);
                }
                ps.setBoolean(3, est.isEstActivo());
                ps.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }

        return est;
    }

    // 🟡 Obtener estudiante por ID
    public Estudiante obtenerEstudiante(int idUsuario) throws SQLException {
        String sql = """
            SELECT u.id_usuario, u.cedula, u.nombre, u.apellido, u.username, u.password, u.correo,
                   e.id_grupo, e.est_activo
            FROM usuarios u
            JOIN estudiantes e ON u.id_usuario = e.id_usuario
            WHERE u.id_usuario = ?
        """;

        Estudiante est = null;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                est = new Estudiante(
                        rs.getInt("id_usuario"),
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("correo"),
                        rs.getInt("id_grupo"),
                        rs.getBoolean("est_activo")
                );
            }
        }
        return est;
    }

    // 🟠 Listar todos los estudiantes
    public List<Estudiante> listarEstudiantes() throws SQLException {
        List<Estudiante> lista = new ArrayList<>();

        String sql = """
            SELECT u.id_usuario, u.cedula, u.nombre, u.apellido, u.username, u.password, u.correo,
                   e.id_grupo, e.est_activo
            FROM usuarios u
            JOIN estudiantes e ON u.id_usuario = e.id_usuario
        """;

        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Estudiante est = new Estudiante(
                        rs.getInt("id_usuario"),
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("correo"),
                        rs.getInt("id_grupo"),
                        rs.getBoolean("est_activo")
                );
                lista.add(est);
            }
        }
        return lista;
    }

    // 🔵 Actualizar estudiante
    public boolean actualizarEstudiante(Estudiante est) throws SQLException {
        int filasUsuarios;
        int filasEstudiantes;

        String sqlUsuario = "UPDATE usuarios SET cedula=?, nombre=?, apellido=?, username=?, password=?, correo=? WHERE id_usuario=?";
        try (PreparedStatement ps = conn.prepareStatement(sqlUsuario)) {
            ps.setString(1, est.getCedula());
            ps.setString(2, est.getNombre());
            ps.setString(3, est.getApellido());
            ps.setString(4, est.getUsername());
            ps.setString(5, est.getPassword());
            ps.setString(6, est.getCorreo());
            ps.setInt(7, est.getIdUsuario());
            filasUsuarios = ps.executeUpdate();
        }

        String sqlEst = "UPDATE estudiantes SET id_grupo=?, est_activo=? WHERE id_usuario=?";
        try (PreparedStatement ps = conn.prepareStatement(sqlEst)) {
            if (est.getIdGrupo() > 0) {
                ps.setInt(1, est.getIdGrupo());
            } else {
                ps.setNull(1, Types.INTEGER);
            }
            ps.setBoolean(2, est.isEstActivo());
            ps.setInt(3, est.getIdUsuario());
            filasEstudiantes = ps.executeUpdate();
        }

        return filasUsuarios > 0 && filasEstudiantes > 0;
    }

    // 🔴 Baja lógica (desactiva estudiante)
    public boolean eliminarEstudiante(int idUsuario) throws SQLException {
        String sql = "UPDATE estudiantes SET est_activo = false WHERE id_usuario = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        }
    }

    // ⚪ Verificar si un estudiante está activo
    public boolean estaActivo(int idUsuario) throws SQLException {
        String sql = "SELECT est_activo FROM estudiantes WHERE id_usuario = ?";
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
