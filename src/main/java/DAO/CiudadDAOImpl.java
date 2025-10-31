package DAO;

import DAO.interfaz.CiudadDAO;
import SINGLETON.ConexionSingleton;
import modelo.Ciudad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CiudadDAOImpl implements CiudadDAO {

    // Conexión a la base de datos obtenida mediante el patrón Singleton
    private final Connection conn;

    // Constructor: inicializa la conexión al crear una instancia del DAO
    public CiudadDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // Crear una nueva ciudad y devolver el objeto con su ID generado
    @Override
    public Ciudad crearCiudad(Ciudad ciudad) throws SQLException {
        String sql = "INSERT INTO ciudades (cod_postal, nombre, departamento) " +
                "VALUES (?, ?, ?) RETURNING id_ciudad";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ciudad.getCodPostal());
            ps.setString(2, ciudad.getNombre());
            ps.setString(3, ciudad.getDepartamento());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ciudad.setIdCiudad(rs.getInt("id_ciudad"));
            }
        }
        return ciudad;
    }

    // Obtener una ciudad específica según su ID
    @Override
    public Ciudad obtenerCiudad(int idCiudad) throws SQLException {
        String sql = "SELECT * FROM ciudades WHERE id_ciudad = ?";
        Ciudad ciudad = null;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCiudad);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ciudad = new Ciudad(
                        rs.getInt("id_ciudad"),
                        rs.getInt("cod_postal"),
                        rs.getString("nombre"),
                        rs.getString("departamento")
                );
            }
        }
        return ciudad;
    }

    // Listar todas las ciudades registradas, ordenadas alfabéticamente por nombre
    @Override
    public List<Ciudad> listarCiudades() throws SQLException {
        List<Ciudad> ciudades = new ArrayList<>();
        String sql = "SELECT * FROM ciudades ORDER BY nombre";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                ciudades.add(new Ciudad(
                        rs.getInt("id_ciudad"),
                        rs.getInt("cod_postal"),
                        rs.getString("nombre"),
                        rs.getString("departamento")
                ));
            }
        }
        return ciudades;
    }

    // Obtener una ciudad por su nombre (sin importar mayúsculas o minúsculas)
    @Override
    public Ciudad obtenerPorNombre(String nombre) throws SQLException {
        String sql = "SELECT * FROM ciudades WHERE LOWER(nombre) = LOWER(?)";
        Ciudad ciudad = null;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ciudad = new Ciudad(
                        rs.getInt("id_ciudad"),
                        rs.getInt("cod_postal"),
                        rs.getString("nombre"),
                        rs.getString("departamento")
                );
            }
        }
        return ciudad;
    }

    // Listar todas las ciudades pertenecientes a un departamento específico
    @Override
    public List<Ciudad> listarPorDepartamento(String departamento) throws SQLException {
        List<Ciudad> ciudades = new ArrayList<>();
        String sql = "SELECT * FROM ciudades WHERE LOWER(departamento) = LOWER(?) ORDER BY nombre";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, departamento);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ciudades.add(new Ciudad(
                        rs.getInt("id_ciudad"),
                        rs.getInt("cod_postal"),
                        rs.getString("nombre"),
                        rs.getString("departamento")
                ));
            }
        }
        return ciudades;
    }

    // Actualizar los datos de una ciudad existente
    @Override
    public boolean actualizarCiudad(Ciudad ciudad) throws SQLException {
        String sql = "UPDATE ciudades SET cod_postal = ?, nombre = ?, departamento = ? WHERE id_ciudad = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ciudad.getCodPostal());
            ps.setString(2, ciudad.getNombre());
            ps.setString(3, ciudad.getDepartamento());
            ps.setInt(4, ciudad.getIdCiudad());
            return ps.executeUpdate() > 0;
        }
    }

    // Eliminar una ciudad de la base de datos según su ID
    @Override
    public boolean eliminarCiudad(int idCiudad) throws SQLException {
        String sql = "DELETE FROM ciudades WHERE id_ciudad = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCiudad);
            return ps.executeUpdate() > 0;
        }
    }
}
