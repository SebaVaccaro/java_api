package PROXY;

import modelo.Direccion;
import servicios.DireccionServicio;
import utils.ValidarUsuario;

import java.sql.SQLException;
import java.util.List;

public class DireccionProxy {

    private final DireccionServicio direccionServicio;
    private final ValidarUsuario validarUsuario;

    // Constructor: inicializa el servicio de direcciones y el validador de usuario
    public DireccionProxy() throws SQLException {
        this.direccionServicio = new DireccionServicio();
        this.validarUsuario = new ValidarUsuario();
    }

    // Crear dirección (solo administradores, psicólogos o el propio usuario)
    public Direccion crearDireccion(int idUsuario, String calle, String numPuerta, String numApto, int idCiudad) throws Exception {
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(idUsuario)) {
            throw new SecurityException("Solo administradores, psicólogos o el propietario pueden crear direcciones.");
        }
        return direccionServicio.crearDireccion(calle, numPuerta, numApto, idCiudad, idUsuario);
    }

    // Listar todas las direcciones (solo administradores, psicólogos)
    public List<Direccion> listarDirecciones() throws Exception {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("Solo administradores o psicólogos.");
        }
        return direccionServicio.listarTodas();
    }

    // Obtener dirección por ID (solo administradores, psicólogos o el propio usuario)
    public Direccion obtenerDireccion(int idDireccion) throws Exception {
        Direccion direccion = direccionServicio.obtenerPorId(idDireccion);
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(direccion.getIdUsuario())) {
            throw new SecurityException("Solo administradores, psicólogos o el propietario pueden ver esta dirección.");
        }
        return direccion;
    }

    // Listar direcciones de un usuario específico (solo administradores, psicólogos o el propio usuario)
    public List<Direccion> listarPorUsuario(int idUsuarioObjetivo) throws Exception {
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(idUsuarioObjetivo)) {
            throw new SecurityException("Solo administradores, psicólogos o el propio usuario pueden ver estas direcciones.");
        }
        return direccionServicio.listarPorUsuario(idUsuarioObjetivo);
    }

    // Listar direcciones de una ciudad (solo administradores o psicólogos)
    public List<Direccion> listarPorCiudad(int idUsuario, int idCiudad) throws Exception {
        if (!validarUsuario.tienePermisoAdminPsico(idUsuario)) {
            throw new SecurityException("Solo administradores o psicólogos pueden listar direcciones de esta ciudad.");
        }
        return direccionServicio.listarPorCiudad(idCiudad);
    }

    // Actualizar dirección (solo administradores, psicólogos o el propio usuario)
    public boolean actualizarDireccion(int idUsuario, int idDireccion, String calle, String numPuerta, String numApto, int idCiudad) throws Exception {
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(idUsuario)) {
            throw new SecurityException("Solo administradores, psicólogos o el propietario pueden actualizar esta dirección.");
        }
        return direccionServicio.actualizarDireccion(idDireccion, calle, numPuerta, numApto, idCiudad, idUsuario);
    }

    // Eliminar dirección (solo administradores, psicólogos o el propio usuario)
    public boolean eliminarDireccion(int idDireccion) throws Exception {
        Direccion direccion = direccionServicio.obtenerPorId(idDireccion);
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(direccion.getIdUsuario())) {
            throw new SecurityException("Solo administradores, psicólogos o el propietario pueden eliminar esta dirección.");
        }
        return direccionServicio.eliminarDireccion(idDireccion);
    }
}

