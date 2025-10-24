package DAO;

import DAO.interfaz.SeguimientoDAO;
import SINGLETON.ConexionSingleton;
import modelo.Seguimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeguimientoDAOImpl implements SeguimientoDAO {

    private final Connection conn;

    public SeguimientoDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    @Override
    public boolean agregar(Seguimiento s) throws SQLException {
        String sql = "INSERT INTO seguimientos (id_informe, id_estudiante, fec_inicio, fec_cierre, est_activo) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            if (s.getIdInforme() != null && s.getIdInforme() > 0) {
                psmt.setInt(1, s.getIdInforme());
            } else {
                psmt.setNull(1, Types.INTEGER);
            }

            psmt.setInt(2, s.getIdEstudiante());
            psmt.setDate(3, Date.valueOf(s.getFecInicio()));

            if (s.getFecCierre() != null) {
                psmt.setDate(4, Date.valueOf(s.getFecCierre()));
            } else {
                psmt.setNull(4, Types.DATE);
            }

            psmt.setBoolean(5, s.isEstActivo());
            return psmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean actualizar(Seguimiento s) throws SQLException {
        String sql = "UPDATE seguimientos SET id_informe = ?, id_estudiante = ?, fec_inicio = ?, fec_cierre = ?, est_activo = ? WHERE id_seguimiento = ?";

        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            if (s.getIdInforme() != null && s.getIdInforme() > 0) {
                psmt.setInt(1, s.getIdInforme());
            } else {
                psmt.setNull(1, Types.INTEGER);
            }

            psmt.setInt(2, s.getIdEstudiante());
            psmt.setDate(3, Date.valueOf(s.getFecInicio()));

            if (s.getFecCierre() != null) {
                psmt.setDate(4, Date.valueOf(s.getFecCierre()));
            } else {
                psmt.setNull(4, Types.DATE);
            }

            psmt.setBoolean(5, s.isEstActivo());
            psmt.setInt(6, s.getIdSeguimiento());

            return psmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int idSeguimiento) throws SQLException {
        String sql = "DELETE FROM seguimientos WHERE id_seguimiento = ?";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, idSeguimiento);
            return psmt.executeUpdate() > 0;
        }
    }

    @Override
    public Seguimiento buscarPorId(int idSeguimiento) throws SQLException {
        String sql = "SELECT id_seguimiento, id_informe, id_estudiante, fec_inicio, fec_cierre, est_activo FROM seguimientos WHERE id_seguimiento = ?";

        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, idSeguimiento);
            ResultSet rs = psmt.executeQuery();

            if (rs.next()) {
                Integer idInforme = rs.getObject("id_informe") != null ? rs.getInt("id_informe") : null;
                Date fecCierreDate = rs.getDate("fec_cierre");

                return new Seguimiento(
                        rs.getInt("id_seguimiento"),
                        idInforme,
                        rs.getInt("id_estudiante"),
                        rs.getDate("fec_inicio").toLocalDate(),
                        fecCierreDate != null ? fecCierreDate.toLocalDate() : null,
                        rs.getBoolean("est_activo")
                );
            }
        }

        return null;
    }

    @Override
    public List<Seguimiento> listarTodos() throws SQLException {
        List<Seguimiento> lista = new ArrayList<>();
        String sql = "SELECT id_seguimiento, id_informe, id_estudiante, fec_inicio, fec_cierre, est_activo FROM seguimientos";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Integer idInforme = rs.getObject("id_informe") != null ? rs.getInt("id_informe") : null;
                Date fecCierreDate = rs.getDate("fec_cierre");

                Seguimiento s = new Seguimiento(
                        rs.getInt("id_seguimiento"),
                        idInforme,
                        rs.getInt("id_estudiante"),
                        rs.getDate("fec_inicio").toLocalDate(),
                        fecCierreDate != null ? fecCierreDate.toLocalDate() : null,
                        rs.getBoolean("est_activo")
                );
                lista.add(s);
            }
        }

        return lista;
    }

    @Override
    public boolean tieneSeguimientoActivo(int idEstudiante) throws SQLException {
        String sql = "SELECT COUNT(*) FROM seguimientos WHERE id_estudiante = ? AND est_activo = TRUE";

        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, idEstudiante);
            ResultSet rs = psmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }

        return false;
    }
}
