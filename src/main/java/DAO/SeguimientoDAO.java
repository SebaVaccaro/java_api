package DAO;

import SINGLETON.ConexionSingleton;
import modelo.Seguimiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeguimientoDAO {

    private final Connection conn;

    public SeguimientoDAO() throws SQLException {
        this.conn =  ConexionSingleton.getInstance().getConexion();
    }

    /**
     * Obtiene todos los seguimientos asociados a un estudiante
     */
    public List<Seguimiento> obtenerPorEstudiante(int idEstudiante) {
        List<Seguimiento> lista = new ArrayList<>();
        String sql = "SELECT id_seguimiento, fec_inicio, fec_cierre, est_activo " +
                "FROM seguimientos WHERE id_estudiante = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEstudiante);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Constructor: id, estudiante, fechaInicio, participantes
                    Seguimiento s = new Seguimiento(
                            rs.getInt("id_seguimiento"),
                            null, // no cargamos el estudiante aquí
                            rs.getDate("fec_inicio").toLocalDate(),
                            new ArrayList<>() // lista vacía de participantes
                    );

                    // seteo extra de atributos
                    s.setActivo(rs.getBoolean("est_activo"));

                    if (rs.getDate("fec_cierre") != null) {
                        s.setFechaCierre(rs.getDate("fec_cierre").toLocalDate());
                    }

                    lista.add(s);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
