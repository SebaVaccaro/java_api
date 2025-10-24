package DAO;

import SINGLETON.ConexionSingleton;
import modelo.Pertenece;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PerteneceDAOImpl {

    private final Connection conn;

    public PerteneceDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // 🔹 Agregar relación Carrera <-> ITR
    public boolean agregar(Pertenece p) throws SQLException {
        String sql = "INSERT INTO pertenece (id_carrera, id_itr) VALUES (?, ?)";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, p.getIdCarrera());
            psmt.setInt(2, p.getIdItr());
            return psmt.executeUpdate() > 0;
        }
    }

    // 🔹 Eliminar relación Carrera <-> ITR
    public boolean eliminar(Pertenece p) throws SQLException {
        String sql = "DELETE FROM pertenece WHERE id_carrera=? AND id_itr=?";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, p.getIdCarrera());
            psmt.setInt(2, p.getIdItr());
            return psmt.executeUpdate() > 0;
        }
    }

    // 🔹 Listar todas las relaciones
    public List<Pertenece> listarTodos() throws SQLException {
        List<Pertenece> lista = new ArrayList<>();
        String sql = "SELECT id_carrera, id_itr FROM pertenece";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Pertenece p = new Pertenece(
                        rs.getInt("id_carrera"),
                        rs.getInt("id_itr")
                );
                lista.add(p);
            }
        }
        return lista;
    }

    // 🔹 Listar ITRs de una carrera
    public List<Integer> listarItrPorCarrera(int idCarrera) throws SQLException {
        List<Integer> itrs = new ArrayList<>();
        String sql = "SELECT id_itr FROM pertenece WHERE id_carrera=?";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, idCarrera);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                itrs.add(rs.getInt("id_itr"));
            }
        }
        return itrs;
    }

    // 🔹 Listar carreras de un ITR
    public List<Integer> listarCarrerasPorItr(int idItr) throws SQLException {
        List<Integer> carreras = new ArrayList<>();
        String sql = "SELECT id_carrera FROM pertenece WHERE id_itr=?";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, idItr);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                carreras.add(rs.getInt("id_carrera"));
            }
        }
        return carreras;
    }
}
