package DAO;

import SINGLETON.ConexionSingleton;
import modelo.Instancia;

import java.sql.*;
import java.time.OffsetDateTime;

/**
 * Implementación del DAO para la entidad Instancia.
 * Gestiona las operaciones CRUD sobre la tabla base 'instancias',
 * que almacena la información general de todas las instancias del sistema.
 */
public class InstanciaDAOImpl {

    // Conexión única a la base de datos mediante el Singleton
    private final Connection conn;

    // Constructor: obtiene la conexión desde el Singleton
    public InstanciaDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // Insertar una nueva instancia en la tabla base 'instancias'
    // Retorna el ID generado automáticamente
    public int insertarInstancia(Instancia instancia) throws SQLException {
        String sql = "INSERT INTO instancias (titulo, fec_hora, descripcion, est_activo, id_funcionario) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id_instancia";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, instancia.getTitulo());
            ps.setObject(2, instancia.getFecHora());
            ps.setString(3, instancia.getDescripcion());
            ps.setBoolean(4, instancia.isEstActivo());
            ps.setInt(5, instancia.getIdFuncionario());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_instancia");
            }
        }
        throw new SQLException("No se pudo insertar la instancia");
    }

    // Actualizar los datos de una instancia existente en la tabla base
    public boolean actualizarInstancia(Instancia instancia) throws SQLException {
        String sql = "UPDATE instancias SET titulo=?, fec_hora=?, descripcion=?, est_activo=?, id_funcionario=? " +
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

    // Realizar una baja lógica (marcar la instancia como inactiva)
    public boolean desactivarInstancia(int idInstancia) throws SQLException {
        String sql = "UPDATE instancias SET est_activo=false WHERE id_instancia=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idInstancia);
            return ps.executeUpdate() > 0;
        }
    }
}
