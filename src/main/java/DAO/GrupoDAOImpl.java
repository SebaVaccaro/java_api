package DAO;

import DAO.interfaz.GrupoDAO;
import SINGLETON.ConexionSingleton;
import modelo.Grupo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del DAO para la entidad Grupo.
 * Gestiona las operaciones CRUD (crear, leer, actualizar, eliminar)
 * sobre la tabla 'grupos' en la base de datos.
 */
public class GrupoDAOImpl implements GrupoDAO {

    // Conexión única a la base de datos mediante el Singleton
    private final Connection conn;

    // Constructor: obtiene la conexión desde el Singleton
    public GrupoDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // Crear un nuevo grupo en la base de datos
    @Override
    public Grupo crearGrupo(Grupo grupo) throws SQLException {
        String sql = "INSERT INTO grupos (nom_grupo, id_carrera) VALUES (?, ?) RETURNING id_grupo";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, grupo.getNomGrupo());
            // Si no se asigna carrera, se inserta NULL
            if (grupo.getIdCarrera() > 0) {
                ps.setInt(2, grupo.getIdCarrera());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                grupo.setIdGrupo(rs.getInt("id_grupo"));
            }
        }
        return grupo;
    }

    // Obtener un grupo por su ID
    @Override
    public Grupo obtenerGrupo(int idGrupo) throws SQLException {
        String sql = "SELECT * FROM grupos WHERE id_grupo = ?";
        Grupo grupo = null;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idGrupo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                grupo = new Grupo(
                        rs.getInt("id_grupo"),
                        rs.getString("nom_grupo"),
                        rs.getInt("id_carrera")
                );
            }
        }
        return grupo;
    }

    // Listar todos los grupos existentes en la base de datos
    @Override
    public List<Grupo> listarGrupos() throws SQLException {
        List<Grupo> grupos = new ArrayList<>();
        String sql = "SELECT * FROM grupos ORDER BY id_grupo";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                grupos.add(new Grupo(
                        rs.getInt("id_grupo"),
                        rs.getString("nom_grupo"),
                        rs.getInt("id_carrera")
                ));
            }
        }
        return grupos;
    }

    // Listar todos los grupos pertenecientes a una carrera específica
    @Override
    public List<Grupo> listarPorCarrera(int idCarrera) throws SQLException {
        List<Grupo> grupos = new ArrayList<>();
        String sql = "SELECT * FROM grupos WHERE id_carrera = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCarrera);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                grupos.add(new Grupo(
                        rs.getInt("id_grupo"),
                        rs.getString("nom_grupo"),
                        rs.getInt("id_carrera")
                ));
            }
        }
        return grupos;
    }

    // Actualizar la información de un grupo existente
    @Override
    public boolean actualizarGrupo(Grupo grupo) throws SQLException {
        String sql = "UPDATE grupos SET nom_grupo = ?, id_carrera = ? WHERE id_grupo = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, grupo.getNomGrupo());
            if (grupo.getIdCarrera() > 0) {
                ps.setInt(2, grupo.getIdCarrera());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setInt(3, grupo.getIdGrupo());
            return ps.executeUpdate() > 0;
        }
    }

    // Eliminar un grupo de la base de datos (eliminación física)
    @Override
    public boolean eliminarGrupo(int idGrupo) throws SQLException {
        String sql = "DELETE FROM grupos WHERE id_grupo = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idGrupo);
            return ps.executeUpdate() > 0;
        }
    }
}
