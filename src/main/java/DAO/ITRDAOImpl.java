package DAO;

import DAO.interfaz.ITRDAO;
import SINGLETON.ConexionSingleton;
import modelo.ITR;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del DAO para la entidad ITR.
 * Gestiona las operaciones CRUD sobre la tabla 'itr',
 * que almacena los registros de Institutos de Tecnología Regional.
 */
public class ITRDAOImpl implements ITRDAO {

    // Conexión única a la base de datos mediante el Singleton
    private final Connection conn;

    // Constructor: obtiene la conexión desde el Singleton
    public ITRDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // Crear un nuevo ITR en la base de datos
    @Override
    public ITR crearITR(ITR itr) throws SQLException {
        String sql = "INSERT INTO itr (id_direccion) VALUES (?) RETURNING id_itr";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, itr.getIdDireccion());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                itr.setIdItr(rs.getInt("id_itr"));
            }
        }
        return itr;
    }

    // Obtener un ITR específico por su ID
    @Override
    public ITR obtenerITR(int idItr) throws SQLException {
        String sql = "SELECT * FROM itr WHERE id_itr = ?";
        ITR itr = null;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idItr);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                itr = new ITR(
                        rs.getInt("id_itr"),
                        rs.getInt("id_direccion")
                );
            }
        }
        return itr;
    }

    // Listar todos los ITR registrados
    @Override
    public List<ITR> listarTodos() throws SQLException {
        List<ITR> lista = new ArrayList<>();
        String sql = "SELECT * FROM itr";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lista.add(new ITR(
                        rs.getInt("id_itr"),
                        rs.getInt("id_direccion")
                ));
            }
        }
        return lista;
    }

    // Actualizar los datos de un ITR existente
    @Override
    public boolean actualizarITR(ITR itr) throws SQLException {
        String sql = "UPDATE itr SET id_direccion = ? WHERE id_itr = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, itr.getIdDireccion());
            ps.setInt(2, itr.getIdItr());
            return ps.executeUpdate() > 0;
        }
    }

    // Eliminar físicamente un ITR de la base de datos por su ID
    @Override
    public boolean eliminarITR(int idItr) throws SQLException {
        String sql = "DELETE FROM itr WHERE id_itr = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idItr);
            return ps.executeUpdate() > 0;
        }
    }
}
