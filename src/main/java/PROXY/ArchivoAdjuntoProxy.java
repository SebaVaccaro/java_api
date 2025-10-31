package PROXY;

import modelo.ArchivoAdjunto;
import servicios.ArchivoAdjuntoServicio;
import utils.ValidarUsuario;

import java.sql.SQLException;
import java.util.List;

public class ArchivoAdjuntoProxy {

    private final ArchivoAdjuntoServicio service;
    private final ValidarUsuario validarUsuario;

    // Constructor: inicializa el servicio de archivos adjuntos y el validador
    public ArchivoAdjuntoProxy() throws SQLException {
        this.service = new ArchivoAdjuntoServicio();
        this.validarUsuario = new ValidarUsuario();
    }

    // Crear archivo adjunto (solo admin, psicopedagogo o propietario)
    public ArchivoAdjunto crearArchivo(int idUsuario, int idEstudiante, String ruta, String categoria) throws Exception {
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(idUsuario)) {
            throw new SecurityException("No tiene permiso para crear este archivo.");
        }
        return service.crearArchivo(idUsuario, idEstudiante, ruta, categoria);
    }

    // Obtener archivo por ID (solo admin, psicopedagogo o propietario)
    public ArchivoAdjunto obtenerPorId(int idUsuario, int idArchivo) throws Exception {
        ArchivoAdjunto archivo = service.obtenerArchivo(idArchivo);
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(idUsuario)) {
            throw new SecurityException("No tiene permiso para ver este archivo.");
        }
        return archivo;
    }

    // Listar archivos activos (sin restricción de permisos)
    public List<ArchivoAdjunto> listarActivos() throws SQLException {
        return service.listarActivos();
    }

    // Listar archivos de un estudiante (solo admin, psicopedagogo o propietario)
    public List<ArchivoAdjunto> listarPorEstudiante(int idUsuario, int idEstudiante) throws Exception {
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(idUsuario)) {
            throw new SecurityException("No tiene permiso para ver los archivos de este estudiante.");
        }
        return service.listarPorEstudiante(idEstudiante);
    }

    // Actualizar archivo (solo admin, psicopedagogo o propietario)
    public boolean actualizarArchivo(ArchivoAdjunto archivo) throws Exception {
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(archivo.getIdUsuario())) {
            throw new SecurityException("No tiene permiso para actualizar este archivo.");
        }
        return service.actualizarArchivo(archivo);
    }

    // Eliminar archivo (baja lógica, solo admin, psicopedagogo o propietario)
    public boolean eliminar(int idArchivo) throws Exception {
        ArchivoAdjunto archivo = service.obtenerArchivo(idArchivo);
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(archivo.getIdUsuario())) {
            throw new SecurityException("No tiene permiso para eliminar este archivo.");
        }
        return service.eliminarArchivo(idArchivo);
    }

    // Eliminar archivo físicamente (solo admin, psicopedagogo o propietario)
    public boolean eliminarFisico(int idArchivo) throws Exception {
        ArchivoAdjunto archivo = service.obtenerArchivo(idArchivo);
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(archivo.getIdUsuario())) {
            throw new SecurityException("No tiene permiso para eliminar físicamente este archivo.");
        }
        return service.eliminarFisico(idArchivo);
    }
}

