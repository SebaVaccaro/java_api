package DAO;

import SINGLETON.ConexionSingleton;
import modelo.PartSeguimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartSeguimientoDAOImpl {

    private final Connection conn;

    public PartSeguimientoDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // 🔹 Crear relación participante-seguimiento
    public boolean agregarParticipante(PartSeguimiento ps) throws SQLException {
        String sql = "INSERT INTO part_seguimientos (id_participante, id_seguimiento) VALUES (?, ?)";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, ps.getIdParticipante());
            psmt.setInt(2, ps.getIdSeguimiento());
            return psmt.executeUpdate() > 0;
        }
    }

    // 🔹 Eliminar relación participante-seguimiento
    public boolean eliminarParticipante(PartSeguimiento ps) throws SQLException {
        String sql = "DELETE FROM part_seguimientos WHERE id_participante=? AND id_seguimiento=?";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, ps.getIdParticipante());
            psmt.setInt(2, ps.getIdSeguimiento());
            return psmt.executeUpdate() > 0;
        }
    }

    // 🔹 Listar todas las relaciones
    public List<PartSeguimiento> listarTodos() throws SQLException {
        List<PartSeguimiento> lista = new ArrayList<>();
        String sql = "SELECT id_participante, id_seguimiento FROM part_seguimientos";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                PartSeguimiento ps = new PartSeguimiento(
                        rs.getInt("id_participante"),
                        rs.getInt("id_seguimiento")
                );
                lista.add(ps);
            }
        }
        return lista;
    }

    // 🔹 Listar seguimientos de un participante
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

    // 🔹 Listar participantes de un seguimiento
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
