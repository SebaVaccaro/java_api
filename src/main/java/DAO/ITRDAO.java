package DAO;

import SINGLETON.ConexionSingleton;
import modelo.ITR;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ITRDAO {

    private final Connection conn;

    public ITRDAO() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // 🔹 Crear un ITR
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

    // 🔹 Obtener ITR por id
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

    // 🔹 Listar todos los ITRs
    public List<ITR> listarTodos() throws SQLException {
        List<ITR> lista = new ArrayList<>();
        String sql = "SELECT * FROM itr";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                ITR itr = new ITR(
                        rs.getInt("id_itr"),
                        rs.getInt("id_direccion")
                );
                lista.add(itr);
            }
        }
        return lista;
    }

    // 🔹 Eliminar ITR por id
    public boolean eliminarITR(int idItr) throws SQLException {
        String sql = "DELETE FROM itr WHERE id_itr = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idItr);
            return ps.executeUpdate() > 0;
        }
    }

    // 🔹 Actualizar ITR
    public boolean actualizarITR(ITR itr) throws SQLException {
        String sql = "UPDATE itr SET id_direccion = ? WHERE id_itr = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, itr.getIdDireccion());
            ps.setInt(2, itr.getIdItr());
            return ps.executeUpdate() > 0;
        }
    }
}
