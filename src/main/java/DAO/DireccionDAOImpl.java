package DAO;

import DAO.interfaz.DireccionDAO;
import SINGLETON.ConexionSingleton;
import modelo.Direccion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DireccionDAOImpl implements DireccionDAO {

    // Conexión a la base de datos obtenida mediante el patrón Singleton
    private final Connection conn;

    // Constructor: inicializa la conexión al crear una instancia del DAO
    public DireccionDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // Crear una nueva dirección y devolver el objeto con su ID generado
    @Override
    public Direccion crearDireccion(Direccion direccion) throws SQLException {
        String sql = "INSERT INTO direcciones (calle, num_puerta, num_apto, id_ciudad, id_usuario) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id_direccion";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, direccion.getCalle());
            ps.setString(2, direccion.getNumPuerta());
            ps.setString(3, direccion.getNumApto());
            ps.setInt(4, direccion.getIdCiudad());
            ps.setInt(5, direccion.getIdUsuario());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                direccion.setIdDireccion(rs.getInt("id_direccion"));
            }
        }
        return direccion;
    }

    // Obtener una dirección específica según su ID
    @Override
    public Direccion obtenerDireccion(int idDireccion) throws SQLException {
        String sql = "SELECT * FROM direcciones WHERE id_direccion = ?";
        Direccion direccion = null;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDireccion);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                direccion = new Direccion(
                        rs.getInt("id_direccion"),
                        rs.getString("calle"),
                        rs.getString("num_puerta"),
                        rs.getString("num_apto"),
                        rs.getInt("id_ciudad"),
                        rs.getInt("id_usuario")
                );
            }
        }
        return direccion;
    }

    // Listar todas las direcciones registradas en la base de datos
    @Override
    public List<Direccion> listarDirecciones() throws SQLException {
        List<Direccion> direcciones = new ArrayList<>();
        String sql = "SELECT * FROM direcciones ORDER BY id_direccion";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                direcciones.add(new Direccion(
                        rs.getInt("id_direccion"),
                        rs.getString("calle"),
                        rs.getString("num_puerta"),
                        rs.getString("num_apto"),
                        rs.getInt("id_ciudad"),
                        rs.getInt("id_usuario")
                ));
            }
        }
        return direcciones;
    }

    // Listar todas las direcciones asociadas a un usuario específico
    @Override
    public List<Direccion> listarPorUsuario(int idUsuario) throws SQLException {
        List<Direccion> direcciones = new ArrayList<>();
        String sql = "SELECT * FROM direcciones WHERE id_usuario = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                direcciones.add(new Direccion(
                        rs.getInt("id_direccion"),
                        rs.getString("calle"),
                        rs.getString("num_puerta"),
                        rs.getString("num_apto"),
                        rs.getInt("id_ciudad"),
                        rs.getInt("id_usuario")
                ));
            }
        }
        return direcciones;
    }

    // Listar todas las direcciones pertenecientes a una ciudad específica
    @Override
    public List<Direccion> listarPorCiudad(int idCiudad) throws SQLException {
        List<Direccion> direcciones = new ArrayList<>();
        String sql = "SELECT * FROM direcciones WHERE id_ciudad = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCiudad);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                direcciones.add(new Direccion(
                        rs.getInt("id_direccion"),
                        rs.getString("calle"),
                        rs.getString("num_puerta"),
                        rs.getString("num_apto"),
                        rs.getInt("id_ciudad"),
                        rs.getInt("id_usuario")
                ));
            }
        }
        return direcciones;
    }

    // Actualizar los datos de una dirección existente
    @Override
    public boolean actualizarDireccion(Direccion direccion) throws SQLException {
        String sql = "UPDATE direcciones SET calle = ?, num_puerta = ?, num_apto = ?, id_ciudad = ?, id_usuario = ? " +
                "WHERE id_direccion = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, direccion.getCalle());
            ps.setString(2, direccion.getNumPuerta());
            ps.setString(3, direccion.getNumApto());
            ps.setInt(4, direccion.getIdCiudad());
            ps.setInt(5, direccion.getIdUsuario());
            ps.setInt(6, direccion.getIdDireccion());
            return ps.executeUpdate() > 0;
        }
    }

    // Eliminar una dirección de la base de datos según su ID
    @Override
    public boolean eliminarDireccion(int idDireccion) throws SQLException {
        String sql = "DELETE FROM direcciones WHERE id_direccion = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDireccion);
            return ps.executeUpdate() > 0;
        }
    }
}
