package DAO;

import SINGLETON.ConexionSingleton;
import modelo.Instancia;

import java.sql.*;
import java.time.OffsetDateTime;

public class InstanciaDAOImpl {

    private final Connection conn;

    public InstanciaDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // 🔹 Insertar solo en tabla base instancias, retorna id
    public int insertarInstancia(Instancia instancia) throws SQLException {
        String sql = "INSERT INTO instancias (titulo, fec_hora, descripcion, activo_flag, id_funcionario) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id_instancia";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, instancia.getTitulo());
            ps.setObject(2, instancia.getFecHora());
            ps.setString(3, instancia.getDescripcion());
            ps.setBoolean(4, instancia.isEstActivo());
            ps.setInt(5, instancia.getIdFuncionario());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id_instancia");
        }
        throw new SQLException("No se pudo insertar la instancia");
    }

    // 🔹 Actualizar solo tabla base
    public boolean actualizarInstancia(Instancia instancia) throws SQLException {
        String sql = "UPDATE instancias SET titulo=?, fec_hora=?, descripcion=?, activo_flag=?, id_funcionario=? " +
                "WHERE id_instancia=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, instancia.getTitulo());
            ps.setObject(2, instancia.getFecHora());
            ps.setString(3, instancia.getDescripcion());
            ps.setBoolean(4, instancia.isEstActivo());
            ps.setInt(5, instancia.getIdFuncionario());
            ps.setInt(6, instancia.getIdInstancia());
            return ps.executeUpdate() > 0;
        }
    }

    // 🔹 Baja lógica
    public boolean desactivarInstancia(int idInstancia) throws SQLException {
        String sql = "UPDATE instancias SET activo_flag=false WHERE id_instancia=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idInstancia);
            return ps.executeUpdate() > 0;
        }
    }
}