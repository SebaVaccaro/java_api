package DAO;

import SINGLETON.ConexionSingleton;
import modelo.InstanciaComun;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstanciaComunDAO {

    private final Connection conn;

    public InstanciaComunDAO() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // 🔹 Crear vínculo Instancia <-> Seguimiento
    public InstanciaComun crearInstanciaComun(InstanciaComun ic) throws SQLException {
        String sql = "INSERT INTO inst_comun (id_instancia, id_seguimiento) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ic.getIdInstancia());
            ps.setInt(2, ic.getIdSeguimiento());
            ps.executeUpdate();
        }
        return ic;
    }

    // 🔹 Obtener vínculo por idInstancia
    public InstanciaComun obtenerPorInstancia(int idInstancia) throws SQLException {
        String sql = "SELECT * FROM inst_comun WHERE id_instancia = ?";
        InstanciaComun ic = null;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idInstancia);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ic = new InstanciaComun(
                        rs.getInt("id_instancia"),
                        rs.getInt("id_seguimiento")
                );
            }
        }
        return ic;
    }

    // 🔹 Listar todos los vínculos
    public List<InstanciaComun> listarTodos() throws SQLException {
        List<InstanciaComun> lista = new ArrayList<>();
        String sql = "SELECT * FROM inst_comun";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                InstanciaComun ic = new InstanciaComun(
                        rs.getInt("id_instancia"),
                        rs.getInt("id_seguimiento")
                );
                lista.add(ic);
            }
        }
        return lista;
    }

    // 🔹 Eliminar vínculo por idInstancia
    public boolean eliminarPorInstancia(int idInstancia) throws SQLException {
        String sql = "DELETE FROM inst_comun WHERE id_instancia = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idInstancia);
            return ps.executeUpdate() > 0;
        }
    }

    // 🔹 Listar vínculos por seguimiento
    public List<InstanciaComun> listarPorSeguimiento(int idSeguimiento) throws SQLException {
        List<InstanciaComun> lista = new ArrayList<>();
        String sql = "SELECT * FROM inst_comun WHERE id_seguimiento = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idSeguimiento);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                InstanciaComun ic = new InstanciaComun(
                        rs.getInt("id_instancia"),
                        rs.getInt("id_seguimiento")
                );
                lista.add(ic);
            }
        }
        return lista;
    }
}
