package DAO;

import SINGLETON.ConexionSingleton;
import modelo.Incidencia;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del DAO para la entidad Incidencia.
 * Gestiona las operaciones CRUD sobre la tabla 'incidencias',
 * la cual extiende la información de la tabla general 'instancias'.
 */
public class IncidenciaDAOImpl {

    // Conexión única a la base de datos mediante el Singleton
    private final Connection conn;

    // Constructor: obtiene la conexión desde el Singleton
    public IncidenciaDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // Crear una nueva incidencia (subregistro vinculado a 'instancias')
    public void insertarIncidencia(Incidencia incidencia) throws SQLException {
        String sql = "INSERT INTO incidencias (id_instancia, lugar) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, incidencia.getIdInstancia());
            ps.setString(2, incidencia.getLugar());
            ps.executeUpdate();
        }
    }

    // Obtener una incidencia por su ID de instancia
    public Incidencia obtenerIncidencia(int idInstancia) throws SQLException {
        String sql = """
            SELECT i.id_instancia, i.titulo, i.fec_hora, i.descripcion, i.est_activo, i.id_funcionario,
                   inc.lugar
            FROM instancias i
            JOIN incidencia inc ON i.id_instancia = inc.id_instancia
            WHERE i.id_instancia = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idInstancia);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Incidencia(
                        rs.getInt("id_instancia"),
                        rs.getString("titulo"),
                        rs.getObject("fec_hora", OffsetDateTime.class),
                        rs.getString("descripcion"),
                        rs.getBoolean("est_activo"),
                        rs.getInt("id_funcionario"),
                        rs.getString("lugar")
                );
            }
        }
        return null;
    }

    // Listar todas las incidencias registradas
    public List<Incidencia> listarIncidencias() throws SQLException {
        List<Incidencia> lista = new ArrayList<>();
        String sql = """
            SELECT i.id_instancia, i.titulo, i.fec_hora, i.descripcion, i.est_activo, i.id_funcionario,
                   inc.lugar
            FROM instancias i
            JOIN incidencias inc ON i.id_instancia = inc.id_instancia
            ORDER BY i.id_instancia
        """;

        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Incidencia inc = new Incidencia(
                        rs.getInt("id_instancia"),
                        rs.getString("titulo"),
                        rs.getObject("fec_hora", OffsetDateTime.class),
                        rs.getString("descripcion"),
                        rs.getBoolean("est_activo"),
                        rs.getInt("id_funcionario"),
                        rs.getString("lugar")
                );
                lista.add(inc);
            }
        }
        return lista;
    }

    // Listar todas las incidencias asociadas a un funcionario específico
    public List<Incidencia> listarPorFuncionario(int idFuncionario) throws SQLException {
        List<Incidencia> lista = new ArrayList<>();
        String sql = """
            SELECT i.id_instancia, i.titulo, i.fec_hora, i.descripcion, i.est_activo,
                   i.id_funcionario, inc.lugar
            FROM instancias i
            JOIN incidencias inc ON i.id_instancia = inc.id_instancia
            WHERE i.id_funcionario = ?
            ORDER BY i.id_instancia
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idFuncionario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Incidencia inc = new Incidencia(
                        rs.getInt("id_instancia"),
                        rs.getString("titulo"),
                        rs.getObject("fec_hora", OffsetDateTime.class),
                        rs.getString("descripcion"),
                        rs.getBoolean("est_activo"),
                        rs.getInt("id_funcionario"),
                        rs.getString("lugar")
                );
                lista.add(inc);
            }
        }
        return lista;
    }

    // Actualizar los datos de la tabla 'incidencias'
    public boolean actualizarIncidencia(Incidencia incidencia) throws SQLException {
        String sql = "UPDATE incidencias SET lugar = ? WHERE id_instancia = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, incidencia.getLugar());
            ps.setInt(2, incidencia.getIdInstancia());
            return ps.executeUpdate() > 0;
        }
    }

    // Realizar baja lógica: desactivar la incidencia en la tabla 'instancias'
    public boolean eliminarIncidencia(int idInstancia) throws SQLException {
        String sql = "UPDATE instancias SET est_activo = false WHERE id_instancia = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idInstancia);
            return ps.executeUpdate() > 0;
        }
    }
}
