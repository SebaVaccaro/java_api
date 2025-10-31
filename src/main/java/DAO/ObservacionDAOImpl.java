package DAO;

import DAO.interfaz.ObservacionDAO;
import SINGLETON.ConexionSingleton;
import modelo.Observacion;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class ObservacionDAOImpl implements ObservacionDAO {

    // Conexión única a la base de datos mediante el Singleton
    private final Connection conn;

    // Constructor: obtiene la conexión desde el Singleton
    public ObservacionDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // Crear una nueva observación en la base de datos
    @Override
    public Observacion crearObservacion(Observacion o) throws SQLException {
        String sql = "INSERT INTO observaciones (id_funcionario, id_estudiante, titulo, contenido, fec_hora, est_activo) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING id_observacion";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, o.getIdFuncionario());
            ps.setInt(2, o.getIdEstudiante());
            ps.setString(3, o.getTitulo());
            ps.setString(4, o.getContenido());
            ps.setObject(5, o.getFecHora());
            ps.setBoolean(6, o.isEstActivo());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                o.setIdObservacion(rs.getInt("id_observacion"));
            }
        }
        return o;
    }

    // Obtener una observación por su ID
    @Override
    public Observacion obtenerObservacion(int id) throws SQLException {
        String sql = "SELECT * FROM observaciones WHERE id_observacion = ?";
        Observacion o = null;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                o = new Observacion(
                        rs.getInt("id_observacion"),
                        rs.getInt("id_funcionario"),
                        rs.getInt("id_estudiante"),
                        rs.getString("titulo"),
                        rs.getString("contenido"),
                        rs.getObject("fec_hora", OffsetDateTime.class),
                        rs.getBoolean("est_activo")
                );
            }
        }
        return o;
    }

    // Listar todas las observaciones
    @Override
    public List<Observacion> listarTodas() throws SQLException {
        List<Observacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM observaciones";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lista.add(new Observacion(
                        rs.getInt("id_observacion"),
                        rs.getInt("id_funcionario"),
                        rs.getInt("id_estudiante"),
                        rs.getString("titulo"),
                        rs.getString("contenido"),
                        rs.getObject("fec_hora", OffsetDateTime.class),
                        rs.getBoolean("est_activo")
                ));
            }
        }
        return lista;
    }

    // Actualizar una observación existente
    @Override
    public boolean actualizarObservacion(Observacion o) throws SQLException {
        String sql = "UPDATE observaciones SET id_funcionario=?, id_estudiante=?, titulo=?, contenido=?, fec_hora=?, est_activo=? " +
                "WHERE id_observacion=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, o.getIdFuncionario());
            ps.setInt(2, o.getIdEstudiante());
            ps.setString(3, o.getTitulo());
            ps.setString(4, o.getContenido());
            ps.setObject(5, o.getFecHora());
            ps.setBoolean(6, o.isEstActivo());
            ps.setInt(7, o.getIdObservacion());
            return ps.executeUpdate() > 0;
        }
    }

    // Baja lógica de una observación (est_activo = false)
    @Override
    public boolean eliminarObservacion(int id) throws SQLException {
        String sql = "UPDATE observaciones SET est_activo=false WHERE id_observacion=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}

