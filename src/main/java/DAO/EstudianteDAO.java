package main.java.DAO;

import main.java.SINGLETON.ConexionSingleton;
import main.java.modelo.Estudiante;
import main.java.modelo.Grupo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {

    private final Connection conn;

    public EstudianteDAO() throws SQLException {
        conn = ConexionSingleton.getInstance().getConexion();
    }

    // Crear estudiante
    public Estudiante crearEstudiante(Estudiante est) throws SQLException {
        // 🚨 Insertamos en usuarios (sin est_activo porque está en estudiantes)
        String sqlUsuario = "INSERT INTO usuarios(cedula, nombre, apellido, username, password, correo) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING id_usuario";
        try (PreparedStatement ps = conn.prepareStatement(sqlUsuario)) {
            ps.setString(1, est.getCi());
            ps.setString(2, est.getNombre());
            ps.setString(3, est.getApellido());
            ps.setString(4, est.getUsername());
            ps.setString(5, est.getPassword());
            ps.setString(6, est.getCorreo());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) est.setId(rs.getInt("id_usuario"));
        }

        // 🚨 Insertamos en estudiantes
        String sqlEst = "INSERT INTO estudiantes(id_usuario, id_grupo, est_activo) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sqlEst)) {
            ps.setInt(1, est.getId());
            if (est.getGrupo() != null) {
                ps.setInt(2, est.getGrupo().getId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setBoolean(3, est.isActivo());
            ps.executeUpdate();
        }

        return est;
    }

    // Obtener estudiante por id
    public Estudiante obtenerEstudiante(int id) throws SQLException {
        String sql = "SELECT u.id_usuario, u.cedula, u.username, u.password, u.nombre, u.apellido, u.correo, " +
                "e.id_grupo, e.est_activo " +
                "FROM usuarios u JOIN estudiantes e ON u.id_usuario=e.id_usuario " +
                "WHERE u.id_usuario=?";
        Estudiante est = null;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Grupo grupo = null;
                int idGrupo = rs.getInt("id_grupo");
                if (idGrupo != 0) {
                    grupo = new Grupo(idGrupo, null, null); // inicialización mínima
                }

                est = new Estudiante(
                        rs.getInt("id_usuario"),
                        rs.getString("cedula"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo"),
                        null, // telefono
                        null, // direccion
                        grupo
                );
                est.setActivo(rs.getBoolean("est_activo"));
            }
        }
        return est;
    }

    // Listar todos los estudiantes
    public List<Estudiante> listarEstudiantes() throws SQLException {
        List<Estudiante> estudiantes = new ArrayList<>();
        String sql = "SELECT u.id_usuario, u.cedula, u.username, u.password, u.nombre, u.apellido, u.correo, " +
                "e.id_grupo, e.est_activo " +
                "FROM usuarios u JOIN estudiantes e ON u.id_usuario=e.id_usuario";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Grupo grupo = null;
                int idGrupo = rs.getInt("id_grupo");
                if (idGrupo != 0) {
                    grupo = new Grupo(idGrupo, null, null);
                }

                Estudiante est = new Estudiante(
                        rs.getInt("id_usuario"),
                        rs.getString("cedula"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo"),
                        null,
                        null,
                        grupo
                );
                est.setActivo(rs.getBoolean("est_activo"));
                estudiantes.add(est);
            }
        }
        return estudiantes;
    }

    // Actualizar Estudiante
    public boolean actualizarEstudiante(Estudiante est) throws SQLException {
        int filasUsuarios = 0;
        int filasEstudiantes = 0;

        String sqlUsuario = "UPDATE usuarios SET cedula=?, nombre=?, apellido=?, username=?, password=?, correo=? " +
                "WHERE id_usuario=?";
        try (PreparedStatement psUsuario = conn.prepareStatement(sqlUsuario)) {
            psUsuario.setString(1, est.getCi());
            psUsuario.setString(2, est.getNombre());
            psUsuario.setString(3, est.getApellido());
            psUsuario.setString(4, est.getUsername());
            psUsuario.setString(5, est.getPassword());
            psUsuario.setString(6, est.getCorreo());
            psUsuario.setInt(7, est.getId());
            filasUsuarios = psUsuario.executeUpdate();
        }

        String sqlEst = "UPDATE estudiantes SET id_grupo=?, est_activo=? WHERE id_usuario=?";
        try (PreparedStatement psEst = conn.prepareStatement(sqlEst)) {
            if (est.getGrupo() != null) {
                psEst.setInt(1, est.getGrupo().getId());
            } else {
                psEst.setNull(1, Types.INTEGER);
            }
            psEst.setBoolean(2, est.isActivo());
            psEst.setInt(3, est.getId());
            filasEstudiantes = psEst.executeUpdate();
        }

        return (filasUsuarios > 0) && (filasEstudiantes > 0);
    }



    // Baja lógica
    public boolean eliminarEstudiante(int id) throws SQLException {
        String sql = "UPDATE estudiantes SET est_activo=false WHERE id_usuario=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // Estados de seguimientos
    public List<Boolean> obtenerEstadoSeguimientos(int idEstudiante) throws SQLException {
        List<Boolean> lista = new ArrayList<>();
        String sql = "SELECT est_activo FROM seguimientos WHERE id_estudiante=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEstudiante);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getBoolean("est_activo"));
            }
        }
        return lista;
    }
}
