package DAO;

import SINGLETON.ConexionSingleton;
import modelo.Instancia;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class InstanciaDAO {

    private final Connection conn;

    public InstanciaDAO() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // 🔹 Crear una instancia
    public Instancia crearInstancia(Instancia instancia) throws SQLException {
        String sql = "INSERT INTO instancias (titulo, tipo, fec_hora, descripcion, est_activo, id_funcionario) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING id_instancia";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, instancia.getTitulo());
            ps.setString(2, instancia.getTipo());
            ps.setObject(3, instancia.getFecHora());
            ps.setString(4, instancia.getDescripcion());
            ps.setBoolean(5, instancia.isEstActivo());
            ps.setInt(6, instancia.getIdFuncionario());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                instancia.setIdInstancia(rs.getInt("id_instancia"));
            }
        }
        return instancia;
    }

    // 🔹 Obtener instancia por ID
    public Instancia obtenerInstancia(int idInstancia) throws SQLException {
        String sql = "SELECT * FROM instancias WHERE id_instancia = ?";
        Instancia instancia = null;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idInstancia);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                instancia = new Instancia(
                        rs.getInt("id_instancia"),
                        rs.getString("titulo"),
                        rs.getString("tipo"),
                        rs.getObject("fec_hora", OffsetDateTime.class),
                        rs.getString("descripcion"),
                        rs.getBoolean("est_activo"),
                        rs.getInt("id_funcionario")
                );
            }
        }
        return instancia;
    }

    // 🔹 Listar todas las instancias
    public List<Instancia> listarInstancias() throws SQLException {
        List<Instancia> instancias = new ArrayList<>();
        String sql = "SELECT * FROM instancias ORDER BY id_instancia";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Instancia instancia = new Instancia(
                        rs.getInt("id_instancia"),
                        rs.getString("titulo"),
                        rs.getString("tipo"),
                        rs.getObject("fec_hora", OffsetDateTime.class),
                        rs.getString("descripcion"),
                        rs.getBoolean("est_activo"),
                        rs.getInt("id_funcionario")
                );
                instancias.add(instancia);
            }
        }
        return instancias;
    }

    // 🔹 Actualizar instancia
    public boolean actualizarInstancia(Instancia instancia) throws SQLException {
        String sql = "UPDATE instancias SET titulo=?, tipo=?, fec_hora=?, descripcion=?, est_activo=?, id_funcionario=? " +
                "WHERE id_instancia=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, instancia.getTitulo());
            ps.setString(2, instancia.getTipo());
            ps.setObject(3, instancia.getFecHora());
            ps.setString(4, instancia.getDescripcion());
            ps.setBoolean(5, instancia.isEstActivo());
            ps.setInt(6, instancia.getIdFuncionario());
            ps.setInt(7, instancia.getIdInstancia());
            return ps.executeUpdate() > 0;
        }
    }

    // 🔹 Baja lógica (activar/desactivar)
    public boolean eliminarInstancia(int idInstancia) throws SQLException {
        String sql = "UPDATE instancias SET est_activo=false WHERE id_instancia=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idInstancia);
            return ps.executeUpdate() > 0;
        }
    }

    // 🔹 Listar instancias de un funcionario
    public List<Instancia> listarPorFuncionario(int idFuncionario) throws SQLException {
        List<Instancia> instancias = new ArrayList<>();
        String sql = "SELECT * FROM instancias WHERE id_funcionario=? ORDER BY id_instancia";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idFuncionario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Instancia instancia = new Instancia(
                        rs.getInt("id_instancia"),
                        rs.getString("titulo"),
                        rs.getString("tipo"),
                        rs.getObject("fec_hora", OffsetDateTime.class),
                        rs.getString("descripcion"),
                        rs.getBoolean("est_activo"),
                        rs.getInt("id_funcionario")
                );
                instancias.add(instancia);
            }
        }
        return instancias;
    }
}
