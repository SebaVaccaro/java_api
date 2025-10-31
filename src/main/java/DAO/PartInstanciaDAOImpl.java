package DAO;

import DAO.interfaz.PartInstanciaDAO;
import SINGLETON.ConexionSingleton;
import modelo.PartInstancia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartInstanciaDAOImpl implements PartInstanciaDAO {

    // Conexión única a la base de datos mediante el Singleton
    private final Connection conn;

    // Constructor: obtiene la conexión desde el Singleton
    public PartInstanciaDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // Agregar un nuevo participante a una instancia
    @Override
    public boolean agregarParticipante(PartInstancia pi) throws SQLException {
        String sql = "INSERT INTO part_instancia (id_participante, id_instancia) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pi.getIdParticipante());
            ps.setInt(2, pi.getIdInstancia());
            return ps.executeUpdate() > 0;
        }
    }

    // Eliminar un participante de una instancia
    @Override
    public boolean eliminarParticipante(PartInstancia pi) throws SQLException {
        String sql = "DELETE FROM part_instancia WHERE id_participante=? AND id_instancia=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pi.getIdParticipante());
            ps.setInt(2, pi.getIdInstancia());
            return ps.executeUpdate() > 0;
        }
    }

    // Listar todas las relaciones participante-instancia
    @Override
    public List<PartInstancia> listarTodos() throws SQLException {
        List<PartInstancia> lista = new ArrayList<>();
        String sql = "SELECT id_participante, id_instancia FROM part_instancia";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lista.add(new PartInstancia(
                        rs.getInt("id_participante"),
                        rs.getInt("id_instancia")
                ));
            }
        }
        return lista;
    }

    // Listar todas las instancias en las que participa un usuario
    @Override
    public List<Integer> listarInstanciasPorParticipante(int idParticipante) throws SQLException {
        List<Integer> instancias = new ArrayList<>();
        String sql = "SELECT id_instancia FROM part_instancia WHERE id_participante=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idParticipante);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                instancias.add(rs.getInt("id_instancia"));
            }
        }
        return instancias;
    }

    // Listar todos los participantes asociados a una instancia
    @Override
    public List<Integer> listarParticipantesPorInstancia(int idInstancia) throws SQLException {
        List<Integer> participantes = new ArrayList<>();
        String sql = "SELECT id_participante FROM part_instancia WHERE id_instancia=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idInstancia);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                participantes.add(rs.getInt("id_participante"));
            }
        }
        return participantes;
    }
}
