package DAO;

import DAO.interfaz.FuncionarioDAO;
import modelo.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del DAO para la entidad Funcionario.
 * Esta clase gestiona las operaciones CRUD sobre la tabla 'funcionarios'
 * y sus relaciones con la tabla 'usuarios'.
 */
public class FuncionarioDAOImpl implements FuncionarioDAO {

    // Conexión a la base de datos, inyectada desde el exterior
    private final Connection conn;

    // Constructor: recibe la conexión establecida
    public FuncionarioDAOImpl(Connection conn) {
        this.conn = conn;
    }

    // Insertar un nuevo funcionario en la base de datos
    @Override
    public void insertarFuncionario(Funcionario f) throws SQLException {
        String sql = "INSERT INTO funcionarios (id_usuario, id_rol, est_activo) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, f.getIdUsuario());
            // Si no tiene rol asignado, se inserta NULL
            if (f.getIdRol() > 0) ps.setInt(2, f.getIdRol());
            else ps.setNull(2, Types.INTEGER);
            ps.setBoolean(3, f.isActivo());
            ps.executeUpdate();
        }
    }

    // Obtener un funcionario específico por su ID de usuario
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

    // Listar todos los funcionarios registrados junto con sus datos de usuario
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

    // Actualizar los datos de un funcionario existente
    @Override
    public boolean actualizarFuncionario(Funcionario f) throws SQLException {
        String sql = "UPDATE funcionarios SET id_rol=?, est_activo=? WHERE id_usuario=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // Si no tiene rol asignado, se actualiza como NULL
            if (f.getIdRol() > 0) ps.setInt(1, f.getIdRol());
            else ps.setNull(1, Types.INTEGER);
            ps.setBoolean(2, f.isActivo());
            ps.setInt(3, f.getIdUsuario());
            return ps.executeUpdate() > 0;
        }
    }

    // Realizar baja lógica (marcar funcionario como inactivo)
    @Override
    public boolean eliminarFuncionario(int idUsuario) throws SQLException {
        String sql = "UPDATE funcionarios SET est_activo=false WHERE id_usuario=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        }
    }

    // Verificar si un funcionario está activo en el sistema
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
