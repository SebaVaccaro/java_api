package DAO;

import SINGLETON.ConexionSingleton;
import modelo.Carrera;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarreraDAOImpl {

    private final Connection conn;

    public CarreraDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // 🔹 Crear nueva carrera
    public Carrera crearCarrera(Carrera carrera) throws SQLException {
        String sql = "INSERT INTO carreras (codigo, nombre, plan) VALUES (?, ?, ?) RETURNING id_carrera";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, carrera.getCodigo());
            ps.setString(2, carrera.getNombre());
            ps.setString(3, carrera.getPlan());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                carrera.setIdCarrera(rs.getInt("id_carrera"));
            }
        }
        return carrera;
    }

    // 🔹 Obtener carrera por ID
    public Carrera obtenerCarrera(int idCarrera) throws SQLException {
        String sql = "SELECT * FROM carreras WHERE id_carrera = ?";
        Carrera carrera = null;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCarrera);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                carrera = new Carrera(
                        rs.getInt("id_carrera"),
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("plan")
                );
            }
        }
        return carrera;
    }

    // 🔹 Listar todas las carreras
    public List<Carrera> listarCarreras() throws SQLException {
        List<Carrera> carreras = new ArrayList<>();
        String sql = "SELECT * FROM carreras ORDER BY nombre";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Carrera carrera = new Carrera(
                        rs.getInt("id_carrera"),
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("plan")
                );
                carreras.add(carrera);
            }
        }
        return carreras;
    }

    // 🔹 Buscar carrera por código
    public Carrera obtenerPorCodigo(String codigo) throws SQLException {
        String sql = "SELECT * FROM carreras WHERE codigo = ?";
        Carrera carrera = null;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                carrera = new Carrera(
                        rs.getInt("id_carrera"),
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("plan")
                );
            }
        }
        return carrera;
    }

    // 🔹 Actualizar carrera
    public boolean actualizarCarrera(Carrera carrera) throws SQLException {
        String sql = "UPDATE carreras SET codigo = ?, nombre = ?, plan = ? WHERE id_carrera = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, carrera.getCodigo());
            ps.setString(2, carrera.getNombre());
            ps.setString(3, carrera.getPlan());
            ps.setInt(4, carrera.getIdCarrera());
            return ps.executeUpdate() > 0;
        }
    }

    // 🔹 Eliminar carrera (borrado físico)
    public boolean eliminarCarrera(int idCarrera) throws SQLException {
        String sql = "DELETE FROM carreras WHERE id_carrera = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCarrera);
            return ps.executeUpdate() > 0;
        }
    }
}
