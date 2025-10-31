package PROXY;

import modelo.ITR;
import servicios.ITRServicio;
import utils.ValidarUsuario;

import java.sql.SQLException;
import java.util.List;

public class ITRProxy {

    private final ITRServicio itrServicio;
    private final ValidarUsuario validarUsuario;

    // Constructor: inicializa el servicio de ITRs y el validador de usuario
    public ITRProxy() throws Exception {
        this.itrServicio = new ITRServicio();
        this.validarUsuario = new ValidarUsuario();
    }

    // Crear ITR (solo administradores)
    public ITR crearITR(ITR itr) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo administradores pueden crear ITRs.");
        }
        return itrServicio.crearITR(itr);
    }

    // Obtener ITR por ID (sin restricción de permisos)
    public ITR obtenerITR(int idItr) throws SQLException {
        return itrServicio.obtenerITR(idItr);
    }

    // Listar todos los ITRs (sin restricción de permisos)
    public List<ITR> listarTodos() throws SQLException {
        return itrServicio.listarTodos();
    }

    // Actualizar ITR (solo administradores)
    public boolean actualizarITR(ITR itr) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo administradores pueden actualizar ITRs.");
        }
        return itrServicio.actualizarITR(itr);
    }

    // Eliminar ITR (solo administradores)
    public boolean eliminarITR(int idItr) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo administradores pueden eliminar ITRs.");
        }
        return itrServicio.eliminarITR(idItr);
    }
}
