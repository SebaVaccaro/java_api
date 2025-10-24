package DAO;

import DAO.interfaz.EstudianteDAO;
import SINGLETON.ConexionSingleton;
import modelo.Estudiante;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAOImpl implements EstudianteDAO {

    private final Connection conn;

    public EstudianteDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    @Override
    public void insertarEstudiante(Estudiante est) throws SQLException {
        String sql = "INSERT INTO estudiantes (id_usuario, id_grupo, est_activo) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, est.getIdUsuario());
            if (est.getIdGrupo() > 0) {
                ps.setInt(2, est.getIdGrupo());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setBoolean(3, est.isActivo());
            ps.executeUpdate();
        }
    }

    @Override
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

    @Override
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

    @Override
    public boolean actualizarEstudiante(Estudiante est) throws SQLException {
        String sql = "UPDATE estudiantes SET id_grupo=?, est_activo=? WHERE id_usuario=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            if (est.getIdGrupo() > 0) {
                ps.setInt(1, est.getIdGrupo());
            } else {
                ps.setNull(1, Types.INTEGER);
            }
            ps.setBoolean(2, est.isActivo());
            ps.setInt(3, est.getIdUsuario());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminarEstudiante(int idUsuario) throws SQLException {
        String sql = "UPDATE estudiantes SET est_activo=false WHERE id_usuario=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean estaActivo(int idUsuario) throws SQLException {
        String sql = "SELECT est_activo FROM estudiantes WHERE id_usuario=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getBoolean("est_activo");
        }
    }
}