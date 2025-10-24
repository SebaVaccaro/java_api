package DAO;

import DAO.interfaz.GrupoDAO;
import SINGLETON.ConexionSingleton;
import modelo.Grupo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GrupoDAOImpl implements GrupoDAO {

    private final Connection conn;

    public GrupoDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    @Override
    public Grupo crearGrupo(Grupo grupo) throws SQLException {
        String sql = "INSERT INTO grupos (nom_grupo, id_carrera) VALUES (?, ?) RETURNING id_grupo";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, grupo.getNomGrupo());
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

    @Override
    public boolean eliminarGrupo(int idGrupo) throws SQLException {
        String sql = "DELETE FROM grupos WHERE id_grupo = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idGrupo);
            return ps.executeUpdate() > 0;
        }
    }
}
