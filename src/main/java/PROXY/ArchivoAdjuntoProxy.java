package PROXY;

import modelo.ArchivoAdjunto;
import servicios.ArchivoAdjuntoServicio;
import utils.ValidarUsuario;

import java.sql.SQLException;
import java.util.List;

public class ArchivoAdjuntoProxy {

    private final ArchivoAdjuntoServicio service;

    public ArchivoAdjuntoProxy() throws SQLException {
        this.service = new ArchivoAdjuntoServicio();
    }

    public ArchivoAdjunto crearArchivo(int idUsuario, int idEstudiante, String ruta, String categoria) throws  Exception {
        ValidarUsuario validar = new ValidarUsuario();
        if (!validar.tienePermisoAdminPsicoOPropietario(idUsuario)) {
            throw new SecurityException("❌ Acceso denegado: no tienes permiso para crear este archivo.");
        }
        return service.crearArchivo(idUsuario, idEstudiante, ruta, categoria);
    }


    public ArchivoAdjunto obtenerPorId(int idUsuario, int idArchivo) throws Exception{
        ArchivoAdjunto archivo = service.obtenerArchivo(idArchivo);

        ValidarUsuario validar = new ValidarUsuario();
        if (!validar.tienePermisoAdminPsicoOPropietario(idUsuario)) {
            throw new SecurityException("❌ Acceso denegado: no tienes permiso para ver este archivo.");
        }

        return archivo;
    }

    public List<ArchivoAdjunto> listarActivos() throws SQLException {
        return service.listarActivos();
    }

    public List<ArchivoAdjunto> listarPorEstudiante(int idUsuario, int idEstudiante) throws Exception{
        ValidarUsuario validar = new ValidarUsuario();
        if (!validar.tienePermisoAdminPsicoOPropietario(idUsuario)) {
            throw new SecurityException("❌ Acceso denegado: no tienes permiso para ver los archivos de este estudiante.");
        }
        return service.listarPorEstudiante(idEstudiante);
    }


    public boolean actualizarArchivo(ArchivoAdjunto archivo) throws Exception {
        ValidarUsuario validar = new ValidarUsuario();
        if (!validar.tienePermisoAdminPsicoOPropietario(archivo.getIdUsuario())) {
            throw new SecurityException("❌ Acceso denegado: no tienes permiso para actualizar este archivo.");
        }
        return service.actualizarArchivo(archivo);
    }

    public boolean eliminar(int idArchivo) throws Exception {
        ArchivoAdjunto archivo = service.obtenerArchivo(idArchivo);
        ValidarUsuario validar = new ValidarUsuario();
        if (!validar.tienePermisoAdminPsicoOPropietario(archivo.getIdUsuario())) {
            throw new SecurityException("❌ Acceso denegado: no tienes permiso para eliminar este archivo.");
        }
        return service.eliminarArchivo(idArchivo);
    }

    public boolean eliminarFisico(int idArchivo) throws Exception {
        ArchivoAdjunto archivo = service.obtenerArchivo(idArchivo);
        ValidarUsuario validar = new ValidarUsuario();
        if (!validar.tienePermisoAdminPsicoOPropietario(archivo.getIdUsuario())) {
            throw new SecurityException("❌ Acceso denegado: no tienes permiso para eliminar físicamente este archivo.");
        }
        return service.eliminarFisico(idArchivo);
    }
}
