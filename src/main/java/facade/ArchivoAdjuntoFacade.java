package facade;

import modelo.ArchivoAdjunto;
import servicios.ArchivoAdjuntoService;

import java.sql.SQLException;
import java.util.List;

public class ArchivoAdjuntoFacade {

    private final ArchivoAdjuntoService service;

    public ArchivoAdjuntoFacade() throws SQLException {
        this.service = new ArchivoAdjuntoService();
    }

    // ==== CREATE ====
    public ArchivoAdjunto crearArchivo(int idUsuario, int idEstudiante, String ruta, String categoria) throws SQLException {
        return service.crearArchivo(idUsuario, idEstudiante, ruta, categoria);
    }

    // ==== READ ====
    public ArchivoAdjunto obtenerPorId(int idArchivo) throws SQLException {
        return service.obtenerArchivo(idArchivo);
    }

    public List<ArchivoAdjunto> listarActivos() throws SQLException {
        return service.listarActivos();
    }

    public List<ArchivoAdjunto> listarPorEstudiante(int idEstudiante) throws SQLException {
        return service.listarPorEstudiante(idEstudiante);
    }

    // ==== UPDATE ====
    public boolean actualizarArchivo(ArchivoAdjunto archivo) throws SQLException {
        return service.actualizarArchivo(archivo);
    }

    // ==== DELETE ====
    public boolean eliminar(int idArchivo) throws SQLException {
        return service.eliminarArchivo(idArchivo);
    }

    public boolean eliminarFisico(int idArchivo) throws SQLException {
        return service.eliminarFisico(idArchivo);
    }
}
// UI -> facade -> servicio -> dao -> postgreSQL