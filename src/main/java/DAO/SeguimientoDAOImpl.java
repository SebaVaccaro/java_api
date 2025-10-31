package DAO;

import DAO.interfaz.SeguimientoDAO;
import SINGLETON.ConexionSingleton;
import modelo.Seguimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeguimientoDAOImpl implements SeguimientoDAO {

    // Conexión a la base de datos mediante Singleton
    private final Connection conn;

    // Constructor: obtiene la conexión del Singleton
    public SeguimientoDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // Agregar un nuevo seguimiento
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

    // Actualizar un seguimiento existente
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

    // Eliminar un seguimiento por ID
    @Override
    public boolean eliminar(int idSeguimiento) throws SQLException {
        String sql = "DELETE FROM seguimientos WHERE id_seguimiento = ?";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, idSeguimiento);
            return psmt.executeUpdate() > 0;
        }
    }

    // Buscar un seguimiento por su ID
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

    // Listar todos los seguimientos
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

    // Verifica si un estudiante tiene un seguimiento activo
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
