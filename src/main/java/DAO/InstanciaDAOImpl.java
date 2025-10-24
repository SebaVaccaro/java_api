package DAO;

import SINGLETON.ConexionSingleton;
import modelo.Incidencia;
import modelo.Instancia;
import modelo.InstanciaComun;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class InstanciaDAOImpl {

    private final Connection conn;

    public InstanciaDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // 🔹 Crear una instancia base (usualmente llamado desde sub-DAO)
    public Instancia crearInstancia(Instancia instancia, String tipo) throws SQLException {
        String sql = "INSERT INTO instancias (titulo, tipo, fec_hora, descripcion, activo_flag, id_funcionario) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING id_instancia";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, instancia.getTitulo());
            ps.setString(2, tipo);
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

    // 🔹 Obtener instancia (retorna subclase correcta)
    public Instancia obtenerInstancia(int idInstancia) throws SQLException {
        String sql = "SELECT * FROM instancias WHERE id_instancia = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idInstancia);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String tipo = rs.getString("tipo");
                OffsetDateTime fecha = rs.getObject("fec_hora", OffsetDateTime.class);

                // Crear el tipo correspondiente según el valor en DB
                if ("COMUN".equalsIgnoreCase(tipo)) {
                    return new InstanciaComun(
                            rs.getInt("id_instancia"),
                            rs.getString("titulo"),
                            fecha,
                            rs.getString("descripcion"),
                            rs.getBoolean("activo_flag"),
                            rs.getInt("id_funcionario"),
                            0 // el idSeguimiento lo obtiene el DAO específico
                    );
                } else if ("INCIDENCIA".equalsIgnoreCase(tipo)) {
                    return new Incidencia(
                            rs.getInt("id_instancia"),
                            rs.getString("titulo"),
                            fecha,
                            rs.getString("descripcion"),
                            rs.getBoolean("activo_flag"),
                            rs.getInt("id_funcionario"),
                            null // lugar lo obtiene el DAO de Incidencia
                    );
                }
            }
        }
        return null;
    }

    // 🔹 Listar todas las instancias (sin detalles de subclases)
    public List<Instancia> listarInstancias() throws SQLException {
        List<Instancia> instancias = new ArrayList<>();
        String sql = "SELECT * FROM instancias ORDER BY id_instancia";

        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String tipo = rs.getString("tipo");
                OffsetDateTime fecha = rs.getObject("fec_hora", OffsetDateTime.class);

                if ("COMUN".equalsIgnoreCase(tipo)) {
                    instancias.add(new InstanciaComun(
                            rs.getInt("id_instancia"),
                            rs.getString("titulo"),
                            fecha,
                            rs.getString("descripcion"),
                            rs.getBoolean("activo_flag"),
                            rs.getInt("id_funcionario"),
                            0
                    ));
                } else if ("INCIDENCIA".equalsIgnoreCase(tipo)) {
                    instancias.add(new Incidencia(
                            rs.getInt("id_instancia"),
                            rs.getString("titulo"),
                            fecha,
                            rs.getString("descripcion"),
                            rs.getBoolean("activo_flag"),
                            rs.getInt("id_funcionario"),
                            null
                    ));
                }
            }
        }
        return instancias;
    }

    // 🔹 Actualizar instancia (solo parte base)
    public boolean actualizarInstancia(Instancia instancia, String tipo) throws SQLException {
        String sql = "UPDATE instancias SET titulo=?, tipo=?, fec_hora=?, descripcion=?, activo_flag=?, id_funcionario=? " +
                "WHERE id_instancia=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, instancia.getTitulo());
            ps.setString(2, tipo);
            ps.setObject(3, instancia.getFecHora());
            ps.setString(4, instancia.getDescripcion());
            ps.setBoolean(5, instancia.isEstActivo());
            ps.setInt(6, instancia.getIdFuncionario());
            ps.setInt(7, instancia.getIdInstancia());
            return ps.executeUpdate() > 0;
        }
    }

    // 🔹 Baja lógica (activar/desactivar)
    public boolean desactivarInstancia(int idInstancia) throws SQLException {
        String sql = "UPDATE instancias SET activo_flag = false WHERE id_instancia = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idInstancia);
            return ps.executeUpdate() > 0;
        }
    }

    // 🔹 Listar instancias por funcionario
    public List<Instancia> listarPorFuncionario(int idFuncionario) throws SQLException {
        List<Instancia> instancias = new ArrayList<>();
        String sql = "SELECT * FROM instancias WHERE id_funcionario = ? ORDER BY id_instancia";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idFuncionario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String tipo = rs.getString("tipo");
                OffsetDateTime fecha = rs.getObject("fec_hora", OffsetDateTime.class);

                if ("COMUN".equalsIgnoreCase(tipo)) {
                    instancias.add(new InstanciaComun(
                            rs.getInt("id_instancia"),
                            rs.getString("titulo"),
                            fecha,
                            rs.getString("descripcion"),
                            rs.getBoolean("activo_flag"),
                            rs.getInt("id_funcionario"),
                            0
                    ));
                } else if ("INCIDENCIA".equalsIgnoreCase(tipo)) {
                    instancias.add(new Incidencia(
                            rs.getInt("id_instancia"),
                            rs.getString("titulo"),
                            fecha,
                            rs.getString("descripcion"),
                            rs.getBoolean("activo_flag"),
                            rs.getInt("id_funcionario"),
                            null
                    ));
                }
            }
        }
        return instancias;
    }
}

