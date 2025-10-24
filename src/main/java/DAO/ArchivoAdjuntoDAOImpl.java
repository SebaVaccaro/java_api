package DAO;

import SINGLETON.ConexionSingleton;
import modelo.ArchivoAdjunto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArchivoAdjuntoDAOImpl {

    private final Connection conn;

    public ArchivoAdjuntoDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // 🔹 Crear nuevo archivo adjunto
    public ArchivoAdjunto crearArchivoAdjunto(ArchivoAdjunto archivo) throws SQLException {
        String sql = "INSERT INTO arch_adjuntos (id_usuario, id_estudiante, ruta, categoria, est_activo) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id_archivo_adjunto";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, archivo.getIdUsuario());
            ps.setInt(2, archivo.getIdEstudiante());
            ps.setString(3, archivo.getRuta());
            ps.setString(4, archivo.getCategoria());
            ps.setBoolean(5, archivo.isEstActivo());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                archivo.setIdArchivoAdjunto(rs.getInt("id_archivo_adjunto"));
            }
        }
        return archivo;
    }

    // 🔹 Obtener archivo por ID
    public ArchivoAdjunto obtenerArchivoAdjunto(int idArchivo) throws SQLException {
        String sql = "SELECT * FROM arch_adjuntos WHERE id_archivo_adjunto = ?";
        ArchivoAdjunto archivo = null;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idArchivo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                archivo = new ArchivoAdjunto(
                        rs.getInt("id_archivo_adjunto"),
                        rs.getInt("id_usuario"),
                        rs.getInt("id_estudiante"),
                        rs.getString("ruta"),
                        rs.getString("categoria"),
                        rs.getBoolean("est_activo")
                );
            }
        }
        return archivo;
    }

    // 🔹 Listar todos los archivos activos
    public List<ArchivoAdjunto> listarArchivosAdjuntosActivos() throws SQLException {
        List<ArchivoAdjunto> archivos = new ArrayList<>();
        String sql = "SELECT * FROM arch_adjuntos WHERE est_activo = true";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                ArchivoAdjunto archivo = new ArchivoAdjunto(
                        rs.getInt("id_archivo_adjunto"),
                        rs.getInt("id_usuario"),
                        rs.getInt("id_estudiante"),
                        rs.getString("ruta"),
                        rs.getString("categoria"),
                        rs.getBoolean("est_activo")
                );
                archivos.add(archivo);
            }
        }
        return archivos;
    }

    // 🔹 Listar archivos por estudiante
    public List<ArchivoAdjunto> listarPorEstudiante(int idEstudiante) throws SQLException {
        List<ArchivoAdjunto> archivos = new ArrayList<>();
        String sql = "SELECT * FROM arch_adjuntos WHERE id_estudiante = ? AND est_activo = true";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEstudiante);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ArchivoAdjunto archivo = new ArchivoAdjunto(
                        rs.getInt("id_archivo_adjunto"),
                        rs.getInt("id_usuario"),
                        rs.getInt("id_estudiante"),
                        rs.getString("ruta"),
                        rs.getString("categoria"),
                        rs.getBoolean("est_activo")
                );
                archivos.add(archivo);
            }
        }
        return archivos;
    }

    // 🔹 Actualizar archivo
    public boolean actualizarArchivoAdjunto(ArchivoAdjunto archivo) throws SQLException {
        String sql = "UPDATE arch_adjuntos SET id_usuario=?, id_estudiante=?, ruta=?, categoria=?, est_activo=? " +
                "WHERE id_archivo_adjunto=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, archivo.getIdUsuario());
            ps.setInt(2, archivo.getIdEstudiante());
            ps.setString(3, archivo.getRuta());
            ps.setString(4, archivo.getCategoria());
            ps.setBoolean(5, archivo.isEstActivo());
            ps.setInt(6, archivo.getIdArchivoAdjunto());
            return ps.executeUpdate() > 0;
        }
    }

    // 🔹 Baja lógica (desactivar archivo)
    public boolean eliminarArchivoAdjunto(int idArchivo) throws SQLException {
        String sql = "UPDATE arch_adjuntos SET est_activo = false WHERE id_archivo_adjunto = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idArchivo);
            return ps.executeUpdate() > 0;
        }
    }

    // 🔹 Eliminar físico (opcional, si realmente querés borrar de la BD)
    public boolean eliminarFisico(int idArchivo) throws SQLException {
        String sql = "DELETE FROM arch_adjuntos WHERE id_archivo_adjunto = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idArchivo);
            return ps.executeUpdate() > 0;
        }
    }
}
