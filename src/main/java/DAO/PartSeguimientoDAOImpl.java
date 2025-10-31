package DAO;

import DAO.interfaz.PartSeguimientoDAO;
import SINGLETON.ConexionSingleton;
import modelo.PartSeguimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartSeguimientoDAOImpl implements PartSeguimientoDAO {

    // Conexión única a la base de datos mediante el Singleton
    private final Connection conn;

    // Constructor: obtiene la conexión desde el Singleton
    public PartSeguimientoDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // Agregar un nuevo participante a un seguimiento
    @Override
    public boolean agregarParticipante(PartSeguimiento ps) throws SQLException {
        String sql = "INSERT INTO part_seguimientos (id_participante, id_seguimiento) VALUES (?, ?)";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, ps.getIdParticipante());
            psmt.setInt(2, ps.getIdSeguimiento());
            return psmt.executeUpdate() > 0;
        }
    }

    // Eliminar un participante de un seguimiento
    @Override
    public boolean eliminarParticipante(PartSeguimiento ps) throws SQLException {
        String sql = "DELETE FROM part_seguimientos WHERE id_participante=? AND id_seguimiento=?";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, ps.getIdParticipante());
            psmt.setInt(2, ps.getIdSeguimiento());
            return psmt.executeUpdate() > 0;
        }
    }

    // Listar todas las relaciones participante-seguimiento
    @Override
    public List<PartSeguimiento> listarTodos() throws SQLException {
        List<PartSeguimiento> lista = new ArrayList<>();
        String sql = "SELECT id_participante, id_seguimiento FROM part_seguimientos";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lista.add(new PartSeguimiento(
                        rs.getInt("id_participante"),
                        rs.getInt("id_seguimiento")
                ));
            }
        }
        return lista;
    }

    // Listar todos los seguimientos en los que participa un usuario
    @Override
    public List<Integer> listarSeguimientosPorParticipante(int idParticipante) throws SQLException {
        List<Integer> seguimientos = new ArrayList<>();
        String sql = "SELECT id_seguimiento FROM part_seguimientos WHERE id_participante=?";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, idParticipante);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                seguimientos.add(rs.getInt("id_seguimiento"));
            }
        }
        return seguimientos;
    }

    // Listar todos los participantes asociados a un seguimiento
    @Override
    public List<Integer> listarParticipantesPorSeguimiento(int idSeguimiento) throws SQLException {
        List<Integer> participantes = new ArrayList<>();
        String sql = "SELECT id_participante FROM part_seguimientos WHERE id_seguimiento=?";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, idSeguimiento);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                participantes.add(rs.getInt("id_participante"));
            }
        }
        return participantes;
    }
}
