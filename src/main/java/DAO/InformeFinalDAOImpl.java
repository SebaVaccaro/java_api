package DAO;

import DAO.interfaz.InformeFinalDAO;
import SINGLETON.ConexionSingleton;
import modelo.InformeFinal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InformeFinalDAOImpl implements InformeFinalDAO {

    private final Connection conn;

    public InformeFinalDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    @Override
    public InformeFinal crearInformeFinal(InformeFinal informe) throws SQLException {
        String sql = "INSERT INTO info_final (contenido, valoracion, fec_creacion) VALUES (?, ?, ?) RETURNING id_inf_final";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, informe.getContenido());
            ps.setInt(2, informe.getValoracion());
            ps.setDate(3, Date.valueOf(informe.getFecCreacion()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                informe.setIdInfFinal(rs.getInt("id_inf_final"));
            }
        }
        return informe;
    }

    @Override
    public InformeFinal obtenerInformeFinal(int idInfFinal) throws SQLException {
        String sql = "SELECT * FROM info_final WHERE id_inf_final = ?";
        InformeFinal informe = null;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idInfFinal);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                informe = new InformeFinal(
                        rs.getInt("id_inf_final"),
                        rs.getString("contenido"),
                        rs.getInt("valoracion"),
                        rs.getDate("fec_creacion").toLocalDate()
                );
            }
        }
        return informe;
    }

    @Override
    public List<InformeFinal> listarInformesFinales() throws SQLException {
        List<InformeFinal> informes = new ArrayList<>();
        String sql = "SELECT * FROM info_final ORDER BY id_inf_final";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                informes.add(new InformeFinal(
                        rs.getInt("id_inf_final"),
                        rs.getString("contenido"),
                        rs.getInt("valoracion"),
                        rs.getDate("fec_creacion").toLocalDate()
                ));
            }
        }
        return informes;
    }

    @Override
    public boolean actualizarInformeFinal(InformeFinal informe) throws SQLException {
        String sql = "UPDATE info_final SET contenido = ?, valoracion = ?, fec_creacion = ? WHERE id_inf_final = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, informe.getContenido());
            ps.setInt(2, informe.getValoracion());
            ps.setDate(3, Date.valueOf(informe.getFecCreacion()));
            ps.setInt(4, informe.getIdInfFinal());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminarInformeFinal(int idInfFinal) throws SQLException {
        String sql = "DELETE FROM info_final WHERE id_inf_final = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idInfFinal);
            return ps.executeUpdate() > 0;
        }
    }
}
