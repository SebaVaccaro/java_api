package PROXY;

import modelo.Direccion;
import servicios.DireccionServicio;
import utils.ValidarUsuario;

import java.sql.SQLException;
import java.util.List;

public class DireccionProxy {

    private final DireccionServicio direccionServicio;

    public DireccionProxy() throws SQLException {
        this.direccionServicio = new DireccionServicio();
    }

    // ============================================================
    // CREAR
    // ============================================================
    public Direccion crearDireccion(int idUsuario, String calle, String numPuerta, String numApto, int idCiudad) throws Exception {
        ValidarUsuario validar = new ValidarUsuario();
        if (!validar.tienePermisoAdminPsicoOPropietario(idUsuario)) {
            throw new SecurityException("❌ Acceso denegado: no tienes permiso para crear esta dirección.");
        }
        return direccionServicio.crearDireccion(calle, numPuerta, numApto, idCiudad, idUsuario);
    }

    // ============================================================
    // LISTAR TODAS
    // ============================================================
    public List<Direccion> listarDirecciones(int idUsuario) throws Exception {
        ValidarUsuario validar = new ValidarUsuario();
        if (!validar.tienePermisoAdminPsicoOPropietario(idUsuario)) {
            throw new SecurityException("❌ Acceso denegado: no tienes permiso para listar direcciones.");
        }
        return direccionServicio.listarTodas();
    }

    // ============================================================
    // OBTENER POR ID
    // ============================================================
    public Direccion obtenerDireccion(int idUsuario, int idDireccion) throws Exception {
        Direccion direccion = direccionServicio.obtenerPorId(idDireccion);
        ValidarUsuario validar = new ValidarUsuario();
        if (!validar.tienePermisoAdminPsicoOPropietario(idUsuario)) {
            throw new SecurityException("❌ Acceso denegado: no tienes permiso para ver esta dirección.");
        }
        return direccion;
    }

    // ============================================================
    // LISTAR POR USUARIO
    // ============================================================
    public List<Direccion> listarPorUsuario(int idUsuarioSolicitante, int idUsuarioObjetivo) throws Exception {
        ValidarUsuario validar = new ValidarUsuario();
        if (!validar.tienePermisoAdminPsicoOPropietario(idUsuarioSolicitante)) {
            throw new SecurityException("❌ Acceso denegado: no tienes permiso para ver las direcciones de este usuario.");
        }
        return direccionServicio.listarPorUsuario(idUsuarioObjetivo);
    }

    // ============================================================
    // LISTAR POR CIUDAD
    // ============================================================
    public List<Direccion> listarPorCiudad(int idUsuario, int idCiudad) throws Exception {
        ValidarUsuario validar = new ValidarUsuario();
        if (!validar.tienePermisoAdminPsicoOPropietario(idUsuario)) {
            throw new SecurityException("❌ Acceso denegado: no tienes permiso para listar direcciones de esta ciudad.");
        }
        return direccionServicio.listarPorCiudad(idCiudad);
    }

    // ============================================================
    // ACTUALIZAR DIRECCIÓN
    // ============================================================
    public boolean actualizarDireccion(int idUsuario, int idDireccion, String calle, String numPuerta, String numApto, int idCiudad) throws Exception {
        ValidarUsuario validar = new ValidarUsuario();
        if (!validar.tienePermisoAdminPsicoOPropietario(idUsuario)) {
            throw new SecurityException("❌ Acceso denegado: no tienes permiso para actualizar esta dirección.");
        }
        return direccionServicio.actualizarDireccion(idDireccion, calle, numPuerta, numApto, idCiudad, idUsuario);
    }

    // ============================================================
    // ELIMINAR DIRECCIÓN
    // ============================================================
    public boolean eliminarDireccion(int idUsuario, int idDireccion) throws Exception {
        ValidarUsuario validar = new ValidarUsuario();
        if (!validar.tienePermisoAdminPsicoOPropietario(idUsuario)) {
            throw new SecurityException("❌ Acceso denegado: no tienes permiso para eliminar esta dirección.");
        }
        return direccionServicio.eliminarDireccion(idDireccion);
    }
}
