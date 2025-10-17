package DAO;

import SINGLETON.ConexionSingleton;
import modelo.Incidencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IncidenciaDAO {

    private final Connection conn;

    public IncidenciaDAO() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // 🔹 Crear nueva incidencia (usa id_instancia existente)
    public Incidencia crearIncidencia(Incidencia incidencia) throws SQLException {
        String sql = "INSERT INTO incidencias (id_instancia, id_funcionario, lugar) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, incidencia.getIdInstancia());
            ps.setInt(2, incidencia.getIdFuncionario());
            ps.setString(3, incidencia.getLugar());
            ps.executeUpdate();
        }
        return incidencia;
    }

    // 🔹 Obtener incidencia por ID de instancia
    public Incidencia obtenerIncidencia(int idInstancia) throws SQLException {
        String sql = "SELECT * FROM incidencias WHERE id_instancia = ?";
        Incidencia incidencia = null;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idInstancia);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                incidencia = new Incidencia(
                        rs.getInt("id_instancia"),
                        rs.getInt("id_funcionario"),
                        rs.getString("lugar")
                );
            }
        }
        return incidencia;
    }

    // 🔹 Listar todas las incidencias
    public List<Incidencia> listarIncidencias() throws SQLException {
        List<Incidencia> incidencias = new ArrayList<>();
        String sql = "SELECT * FROM incidencias ORDER BY id_instancia";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Incidencia incidencia = new Incidencia(
                        rs.getInt("id_instancia"),
                        rs.getInt("id_funcionario"),
                        rs.getString("lugar")
                );
                incidencias.add(incidencia);
            }
        }
        return incidencias;
    }

    // 🔹 Listar incidencias por funcionario
    public List<Incidencia> listarPorFuncionario(int idFuncionario) throws SQLException {
        List<Incidencia> incidencias = new ArrayList<>();
        String sql = "SELECT * FROM incidencias WHERE id_funcionario = ? ORDER BY id_instancia";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idFuncionario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Incidencia incidencia = new Incidencia(
                        rs.getInt("id_instancia"),
                        rs.getInt("id_funcionario"),
                        rs.getString("lugar")
                );
                incidencias.add(incidencia);
            }
        }
        return incidencias;
    }

    // 🔹 Actualizar datos de una incidencia
    public boolean actualizarIncidencia(Incidencia incidencia) throws SQLException {
        String sql = "UPDATE incidencias SET id_funcionario = ?, lugar = ? WHERE id_instancia = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, incidencia.getIdFuncionario());
            ps.setString(2, incidencia.getLugar());
            ps.setInt(3, incidencia.getIdInstancia());
            return ps.executeUpdate() > 0;
        }
    }

    // 🔹 Eliminar incidencia
    public boolean eliminarIncidencia(int idInstancia) throws SQLException {
        String sql = "DELETE FROM incidencias WHERE id_instancia = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idInstancia);
            return ps.executeUpdate() > 0;
        }
    }
}
