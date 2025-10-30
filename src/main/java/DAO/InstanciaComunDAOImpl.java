package DAO;

import SINGLETON.ConexionSingleton;
import modelo.InstanciaComun;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class InstanciaComunDAOImpl {

    private final Connection conn;

    public InstanciaComunDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // 🔹 Insertar en subtabla instancia_comun
    public void insertarInstanciaComun(InstanciaComun instancia) throws SQLException {
        String sql = "INSERT INTO inst_comun (id_instancia, id_seguimiento) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, instancia.getIdInstancia());
            ps.setInt(2, instancia.getIdSeguimiento());
            ps.executeUpdate();
        }
    }

    // 🔹 Obtener por id
    public InstanciaComun obtenerInstanciaComun(int idInstancia) throws SQLException {
        String sql = """
            SELECT i.id_instancia, i.titulo, i.fec_hora, i.descripcion, i.est_activo, i.id_funcionario,
                   ic.id_seguimiento
            FROM instancias i
            JOIN inst_comun ic ON i.id_instancia = ic.id_instancia
            WHERE i.id_instancia = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idInstancia);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new InstanciaComun(
                        rs.getInt("id_instancia"),
                        rs.getString("titulo"),
                        rs.getObject("fec_hora", OffsetDateTime.class),
                        rs.getString("descripcion"),
                        rs.getBoolean("est_activo"),
                        rs.getInt("id_funcionario"),
                        rs.getInt("id_seguimiento")
                );
            }
        }
        return null;
    }

    // 🔹 Listar todas
    public List<InstanciaComun> listarInstanciasComunes() throws SQLException {
        List<InstanciaComun> lista = new ArrayList<>();
        String sql = """
            SELECT i.id_instancia, i.titulo, i.fec_hora, i.descripcion, i.est_activo, i.id_funcionario,
                   ic.id_seguimiento
            FROM instancias i
            JOIN inst_comun ic ON i.id_instancia = ic.id_instancia
            ORDER BY i.id_instancia
        """;

        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                InstanciaComun ic = new InstanciaComun(
                        rs.getInt("id_instancia"),
                        rs.getString("titulo"),
                        rs.getObject("fec_hora", OffsetDateTime.class),
                        rs.getString("descripcion"),
                        rs.getBoolean("est_activo"),
                        rs.getInt("id_funcionario"),
                        rs.getInt("id_seguimiento")
                );
                lista.add(ic);
            }
        }
        return lista;
    }

    // 🔹 Listar InstanciaComun por seguimiento
    public List<InstanciaComun> listarPorSeguimiento(int idSeguimiento) throws SQLException {
        List<InstanciaComun> lista = new ArrayList<>();
        String sql = """
            SELECT i.id_instancia, i.titulo, i.fec_hora, i.descripcion, i.est_activo,
                   i.id_funcionario, ic.id_seguimiento
            FROM instancias i
            JOIN inst_comun ic ON i.id_instancia = ic.id_instancia
            WHERE ic.id_seguimiento = ?
            ORDER BY i.id_instancia
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idSeguimiento);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                InstanciaComun ic = new InstanciaComun(
                        rs.getInt("id_instancia"),
                        rs.getString("titulo"),
                        rs.getObject("fec_hora", OffsetDateTime.class),
                        rs.getString("descripcion"),
                        rs.getBoolean("est_activo"),
                        rs.getInt("id_funcionario"),
                        rs.getInt("id_seguimiento")
                );
                lista.add(ic);
            }
        }
        return lista;
    }

    // 🔹 Actualizar solo tabla comun
    public boolean actualizarInstanciaComun(InstanciaComun instanciaComun) throws SQLException {
        String sql = "UPDATE inst_comun SET id_seguimiento=? WHERE id_instancia=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, instanciaComun.getIdSeguimiento());
            ps.setInt(2, instanciaComun.getIdInstancia());
            return ps.executeUpdate() > 0;
        }
    }

    // 🔹 Baja lógica
    public boolean eliminarInstanciaComun(int idInstancia) throws SQLException {
        String sql = "UPDATE instancias SET est_activo=false WHERE id_instancia=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idInstancia);
            return ps.executeUpdate() > 0;
        }
    }
}