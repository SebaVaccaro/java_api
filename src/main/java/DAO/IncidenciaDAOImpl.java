package DAO;

import SINGLETON.ConexionSingleton;
import modelo.Incidencia;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class IncidenciaDAOImpl {

    private final Connection conn;

    public IncidenciaDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // 🔹 Crear nueva incidencia (inserta en instancia y luego en incidencia)
    public Incidencia crearIncidencia(Incidencia incidencia) throws SQLException {
        String sqlInstancia = "INSERT INTO instancias (titulo, fec_hora, descripcion, activo_flag, id_funcionario) VALUES (?, ?, ?, ?, ?)";
        String sqlIncidencia = "INSERT INTO incidencias (id_instancia, lugar) VALUES (?, ?)";

        try {
            conn.setAutoCommit(false); // iniciar transacción

            // 1️⃣ Insertar en instancias
            try (PreparedStatement psInst = conn.prepareStatement(sqlInstancia, Statement.RETURN_GENERATED_KEYS)) {
                psInst.setString(1, incidencia.getTitulo());
                psInst.setObject(2, incidencia.getFecHora());
                psInst.setString(3, incidencia.getDescripcion());
                psInst.setBoolean(4, incidencia.isEstActivo());
                psInst.setInt(5, incidencia.getIdFuncionario());
                psInst.executeUpdate();

                // Recuperar el id generado
                ResultSet rs = psInst.getGeneratedKeys();
                if (rs.next()) {
                    incidencia.setIdInstancia(rs.getInt(1));
                }
            }

            // 2️⃣ Insertar en incidencias
            try (PreparedStatement psInc = conn.prepareStatement(sqlIncidencia)) {
                psInc.setInt(1, incidencia.getIdInstancia());
                psInc.setString(2, incidencia.getLugar());
                psInc.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }

        return incidencia;
    }

    // 🔹 Obtener una incidencia (JOIN con instancia base)
    public Incidencia obtenerIncidencia(int idInstancia) throws SQLException {
        String sql = """
            SELECT i.id_instancia, i.titulo, i.fec_hora, i.descripcion, i.activo_flag, 
                   i.id_funcionario, inc.lugar
            FROM instancias i
            JOIN incidencias inc ON i.id_instancia = inc.id_instancia
            WHERE i.id_instancia = ?
        """;

        Incidencia incidencia = null;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idInstancia);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                incidencia = new Incidencia(
                        rs.getInt("id_instancia"),
                        rs.getString("titulo"),
                        rs.getObject("fec_hora", OffsetDateTime.class),
                        rs.getString("descripcion"),
                        rs.getBoolean("activo_flag"),
                        rs.getInt("id_funcionario"),
                        rs.getString("lugar")
                );
            }
        }
        return incidencia;
    }

    // 🔹 Listar todas las incidencias
    public List<Incidencia> listarIncidencias() throws SQLException {
        List<Incidencia> lista = new ArrayList<>();
        String sql = """
            SELECT i.id_instancia, i.titulo, i.fec_hora, i.descripcion, i.activo_flag, 
                   i.id_funcionario, inc.lugar
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
                        rs.getBoolean("activo_flag"),
                        rs.getInt("id_funcionario"),
                        rs.getString("lugar")
                );
                lista.add(inc);
            }
        }
        return lista;
    }

    // 🔹 Listar incidencias por funcionario
    public List<Incidencia> listarPorFuncionario(int idFuncionario) throws SQLException {
        List<Incidencia> lista = new ArrayList<>();
        String sql = """
            SELECT i.id_instancia, i.titulo, i.fec_hora, i.descripcion, i.activo_flag, 
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
                        rs.getBoolean("activo_flag"),
                        rs.getInt("id_funcionario"),
                        rs.getString("lugar")
                );
                lista.add(inc);
            }
        }
        return lista;
    }

    // 🔹 Actualizar incidencia (actualiza tanto instancia como subtabla)
    public boolean actualizarIncidencia(Incidencia incidencia) throws SQLException {
        String sqlInst = "UPDATE instancias SET titulo = ?, fec_hora = ?, descripcion = ?, activo_flag = ?, id_funcionario = ? WHERE id_instancia = ?";
        String sqlInc = "UPDATE incidencias SET lugar = ? WHERE id_instancia = ?";
        int filasAfectadas = 0;

        try {
            conn.setAutoCommit(false);

            try (PreparedStatement psInst = conn.prepareStatement(sqlInst)) {
                psInst.setString(1, incidencia.getTitulo());
                psInst.setObject(2, incidencia.getFecHora());
                psInst.setString(3, incidencia.getDescripcion());
                psInst.setBoolean(4, incidencia.isEstActivo());
                psInst.setInt(5, incidencia.getIdFuncionario());
                psInst.setInt(6, incidencia.getIdInstancia());
                filasAfectadas += psInst.executeUpdate();
            }

            try (PreparedStatement psInc = conn.prepareStatement(sqlInc)) {
                psInc.setString(1, incidencia.getLugar());
                psInc.setInt(2, incidencia.getIdInstancia());
                filasAfectadas += psInc.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }

        return filasAfectadas > 0;
    }

    // 🔹 Eliminar incidencia (borra de ambas tablas)
    public boolean eliminarIncidencia(int idInstancia) throws SQLException {
        String sqlInc = "DELETE FROM incidencias WHERE id_instancia = ?";
        String sqlInst = "DELETE FROM instancias WHERE id_instancia = ?";
        int filasAfectadas = 0;

        try {
            conn.setAutoCommit(false);

            try (PreparedStatement psInc = conn.prepareStatement(sqlInc)) {
                psInc.setInt(1, idInstancia);
                psInc.executeUpdate();
            }

            try (PreparedStatement psInst = conn.prepareStatement(sqlInst)) {
                psInst.setInt(1, idInstancia);
                filasAfectadas += psInst.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }

        return filasAfectadas > 0;
    }
}
