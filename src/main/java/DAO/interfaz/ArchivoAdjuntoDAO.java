package DAO.interfaz;

import modelo.ArchivoAdjunto;
import java.sql.SQLException;
import java.util.List;

public interface ArchivoAdjuntoDAO {

    // Crear nuevo archivo adjunto
    ArchivoAdjunto crearArchivoAdjunto(ArchivoAdjunto archivo) throws SQLException;

    // Obtener archivo por ID
    ArchivoAdjunto obtenerArchivoAdjunto(int idArchivo) throws SQLException;

    // Listar todos los archivos activos
    List<ArchivoAdjunto> listarArchivosAdjuntosActivos() throws SQLException;

    // Listar archivos por estudiante
    List<ArchivoAdjunto> listarPorEstudiante(int idEstudiante) throws SQLException;

    // Actualizar archivo
    boolean actualizarArchivoAdjunto(ArchivoAdjunto archivo) throws SQLException;

    // Baja lógica (desactivar archivo)
    boolean eliminarArchivoAdjunto(int idArchivo) throws SQLException;

    // Eliminación física
    boolean eliminarFisico(int idArchivo) throws SQLException;
}
