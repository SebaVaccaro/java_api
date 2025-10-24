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

    // 🔹 Crear nueva InstanciaComun (inserta en instancias y luego en inst_comun)
    public InstanciaComun crearInstanciaComun(InstanciaComun ic) throws SQLException {
        String sqlInstancia = "INSERT INTO instancias (titulo, fec_hora, descripcion, est_activo, id_funcionario) VALUES (?, ?, ?, ?, ?)";
        String sqlComun = "INSERT INTO inst_comun (id_instancia, id_seguimiento) VALUES (?, ?)";

        try {
            conn.setAutoCommit(false);

            // 1️⃣ Insertar en instancias
            try (PreparedStatement psInst = conn.prepareStatement(sqlInstancia, Statement.RETURN_GENERATED_KEYS)) {
                psInst.setString(1, ic.getTitulo());
                psInst.setObject(2, ic.getFecHora());
                psInst.setString(3, ic.getDescripcion());
                psInst.setBoolean(4, ic.isEstActivo());
                psInst.setInt(5, ic.getIdFuncionario());
                psInst.executeUpdate();

                ResultSet rs = psInst.getGeneratedKeys();
                if (rs.next()) {
                    ic.setIdInstancia(rs.getInt(1));
                }
            }

            // 2️⃣ Insertar en inst_comun
            try (PreparedStatement psComun = conn.prepareStatement(sqlComun)) {
                psComun.setInt(1, ic.getIdInstancia());
                psComun.setInt(2, ic.getIdSeguimiento());
                psComun.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }

        return ic;
    }

    // 🔹 Obtener una InstanciaComun por id_instancia
    public InstanciaComun obtenerPorInstancia(int idInstancia) throws SQLException {
        String sql = """
            SELECT i.id_instancia, i.titulo, i.fec_hora, i.descripcion, i.est_activo, 
                   i.id_funcionario, ic.id_seguimiento
            FROM instancias i
            JOIN inst_comun ic ON i.id_instancia = ic.id_instancia
            WHERE i.id_instancia = ?
        """;

        InstanciaComun ic = null;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idInstancia);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ic = new InstanciaComun(
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
        return ic;
    }

    // 🔹 Listar todas las InstanciaComun
    public List<InstanciaComun> listarTodos() throws SQLException {
        List<InstanciaComun> lista = new ArrayList<>();
        String sql = """
            SELECT i.id_instancia, i.titulo, i.fec_hora, i.descripcion, i.est_activo,
                   i.id_funcionario, ic.id_seguimiento
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

    // 🔹 Actualizar InstanciaComun (instancias + inst_comun)
    public boolean actualizarInstanciaComun(InstanciaComun ic) throws SQLException {
        String sqlInst = "UPDATE instancias SET titulo = ?, fec_hora = ?, descripcion = ?, est_activo = ?, id_funcionario = ? WHERE id_instancia = ?";
        String sqlComun = "UPDATE inst_comun SET id_seguimiento = ? WHERE id_instancia = ?";
        int filas = 0;

        try {
            conn.setAutoCommit(false);

            try (PreparedStatement psInst = conn.prepareStatement(sqlInst)) {
                psInst.setString(1, ic.getTitulo());
                psInst.setObject(2, ic.getFecHora());
                psInst.setString(3, ic.getDescripcion());
                psInst.setBoolean(4, ic.isEstActivo());
                psInst.setInt(5, ic.getIdFuncionario());
                psInst.setInt(6, ic.getIdInstancia());
                filas += psInst.executeUpdate();
            }

            try (PreparedStatement psComun = conn.prepareStatement(sqlComun)) {
                psComun.setInt(1, ic.getIdSeguimiento());
                psComun.setInt(2, ic.getIdInstancia());
                filas += psComun.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }

        return filas > 0;
    }

    // 🔹 Eliminar InstanciaComun (de ambas tablas)
    public boolean eliminarPorInstancia(int idInstancia) throws SQLException {
        String sqlComun = "DELETE FROM inst_comun WHERE id_instancia = ?";
        String sqlInst = "DELETE FROM instancias WHERE id_instancia = ?";
        int filas = 0;

        try {
            conn.setAutoCommit(false);

            try (PreparedStatement psComun = conn.prepareStatement(sqlComun)) {
                psComun.setInt(1, idInstancia);
                psComun.executeUpdate();
            }

            try (PreparedStatement psInst = conn.prepareStatement(sqlInst)) {
                psInst.setInt(1, idInstancia);
                filas += psInst.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }

        return filas > 0;
    }
}
