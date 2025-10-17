package servicios;

import DAO.ArchivoAdjuntoDAO;
import modelo.ArchivoAdjunto;

import java.sql.SQLException;
import java.util.List;

public class ArchivoAdjuntoService {

    private final ArchivoAdjuntoDAO archivoDAO;

    public ArchivoAdjuntoService() throws SQLException {
        this.archivoDAO = new ArchivoAdjuntoDAO();
    }

    // Crear un archivo adjunto
    public ArchivoAdjunto crearArchivo(int idUsuario, int idEstudiante, String ruta, String categoria) throws SQLException {
        ArchivoAdjunto archivo = new ArchivoAdjunto(idUsuario, idEstudiante, ruta, categoria, true);
        return archivoDAO.crearArchivoAdjunto(archivo);
    }

    // Obtener archivo por ID
    public ArchivoAdjunto obtenerArchivo(int idArchivo) throws SQLException {
        return archivoDAO.obtenerArchivoAdjunto(idArchivo);
    }

    // Listar todos los archivos activos
    public List<ArchivoAdjunto> listarActivos() throws SQLException {
        return archivoDAO.listarArchivosAdjuntosActivos();
    }

    // Listar archivos de un estudiante
    public List<ArchivoAdjunto> listarPorEstudiante(int idEstudiante) throws SQLException {
        return archivoDAO.listarPorEstudiante(idEstudiante);
    }

    // Actualizar archivo
    public boolean actualizarArchivo(ArchivoAdjunto archivo) throws SQLException {
        return archivoDAO.actualizarArchivoAdjunto(archivo);
    }

    // Baja lógica
    public boolean eliminarArchivo(int idArchivo) throws SQLException {
        return archivoDAO.eliminarArchivoAdjunto(idArchivo);
    }

    // Eliminar físico (opcional)
    public boolean eliminarFisico(int idArchivo) throws SQLException {
        return archivoDAO.eliminarFisico(idArchivo);
    }
}
