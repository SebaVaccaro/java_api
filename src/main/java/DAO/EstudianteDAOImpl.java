package DAO;

import DAO.interfaz.EstudianteDAO;
import SINGLETON.ConexionSingleton;
import modelo.Estudiante;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAOImpl implements EstudianteDAO {

    // Conexión a la base de datos obtenida mediante el patrón Singleton
    private final Connection conn;

    // Constructor: inicializa la conexión al crear una instancia del DAO
    public EstudianteDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // Insertar un nuevo estudiante en la base de datos
    @Override
    public void insertarEstudiante(Estudiante est) throws SQLException {
        String sql = "INSERT INTO estudiantes (id_usuario, id_grupo, est_activo) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, est.getIdUsuario());
            // Si el estudiante no tiene grupo asignado, se inserta NULL
            if (est.getIdGrupo() > 0) {
                ps.setInt(2, est.getIdGrupo());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setBoolean(3, est.isActivo());
            ps.executeUpdate();
        }
    }

    // Obtener un estudiante específico según su ID de usuario
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

    // Listar todos los estudiantes registrados junto con sus datos de usuario
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

    // Actualizar los datos de un estudiante existente
    @Override
    public boolean actualizarEstudiante(Estudiante est) throws SQLException {
        String sql = "UPDATE estudiantes SET id_grupo=?, est_activo=? WHERE id_usuario=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // Si el estudiante no tiene grupo, se asigna NULL
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

    // Realizar una baja lógica del estudiante (marcar como inactivo)
    @Override
    public boolean eliminarEstudiante(int idUsuario) throws SQLException {
        String sql = "UPDATE estudiantes SET est_activo=false WHERE id_usuario=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        }
    }

    // Verificar si un estudiante se encuentra activo en el sistema
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
