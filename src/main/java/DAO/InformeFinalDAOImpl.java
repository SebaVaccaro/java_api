package DAO;

import DAO.interfaz.InformeFinalDAO;
import SINGLETON.ConexionSingleton;
import modelo.InformeFinal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del DAO para la entidad InformeFinal.
 * Gestiona las operaciones CRUD sobre la tabla 'info_final',
 * que almacena los informes finales generados por los usuarios o procesos del sistema.
 */
public class InformeFinalDAOImpl implements InformeFinalDAO {

    // Conexión única a la base de datos mediante el Singleton
    private final Connection conn;

    // Constructor: obtiene la conexión desde el Singleton
    public InformeFinalDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // Crear un nuevo informe final en la base de datos
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

    // Obtener un informe final específico por su ID
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

    // Listar todos los informes finales registrados
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

    // Actualizar los datos de un informe final existente
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

    // Eliminar físicamente un informe final por su ID
    @Override
    public boolean eliminarInformeFinal(int idInfFinal) throws SQLException {
        String sql = "DELETE FROM info_final WHERE id_inf_final = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idInfFinal);
            return ps.executeUpdate() > 0;
        }
    }
}
