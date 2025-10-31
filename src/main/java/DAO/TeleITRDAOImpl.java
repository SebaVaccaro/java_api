package DAO;

import DAO.interfaz.TeleITRDAO;
import SINGLETON.ConexionSingleton;
import modelo.TeleITR;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeleITRDAOImpl implements TeleITRDAO {

    // Conexión a la base de datos mediante Singleton
    private final Connection conn;

    // Constructor: obtiene la conexión desde el Singleton
    public TeleITRDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // Agregar un nuevo teléfono asociado a un ITR
    @Override
    public boolean agregar(TeleITR t) throws SQLException {
        String sql = "INSERT INTO tele_itr (numero, id_itr) VALUES (?, ?)";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setString(1, t.getNumero());
            psmt.setInt(2, t.getIdItr());
            return psmt.executeUpdate() > 0;
        }
    }

    // Actualizar los datos de un teléfono
    @Override
    public boolean actualizar(TeleITR t) throws SQLException {
        String sql = "UPDATE tele_itr SET numero=?, id_itr=? WHERE id_telefono=?";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setString(1, t.getNumero());
            psmt.setInt(2, t.getIdItr());
            psmt.setInt(3, t.getIdTelefono());
            return psmt.executeUpdate() > 0;
        }
    }

    // Eliminar un teléfono por su ID
    @Override
    public boolean eliminar(int idTelefono) throws SQLException {
        String sql = "DELETE FROM tele_itr WHERE id_telefono=?";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, idTelefono);
            return psmt.executeUpdate() > 0;
        }
    }

    // Buscar un teléfono por su ID
    @Override
    public TeleITR buscarPorId(int idTelefono) throws SQLException {
        String sql = "SELECT id_telefono, numero, id_itr FROM tele_itr WHERE id_telefono=?";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, idTelefono);
            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                return new TeleITR(
                        rs.getInt("id_telefono"),
                        rs.getString("numero"),
                        rs.getInt("id_itr")
                );
            }
        }
        return null;
    }

    // Listar todos los teléfonos registrados
    @Override
    public List<TeleITR> listarTodos() throws SQLException {
        List<TeleITR> lista = new ArrayList<>();
        String sql = "SELECT id_telefono, numero, id_itr FROM tele_itr";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                TeleITR t = new TeleITR(
                        rs.getInt("id_telefono"),
                        rs.getString("numero"),
                        rs.getInt("id_itr")
                );
                lista.add(t);
            }
        }
        return lista;
    }
}
