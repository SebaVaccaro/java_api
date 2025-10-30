package PROXY;

import modelo.ITR;
import servicios.ITRServicio;
import utils.ValidarUsuario;

import java.sql.SQLException;
import java.util.List;

public class ITRProxy {

    private final ITRServicio itrServicio;
    private final ValidarUsuario validarUsuario;

    public ITRProxy() throws Exception {
        this.itrServicio = new ITRServicio();
        this.validarUsuario = new ValidarUsuario();
    }

    public ITR crearITR(ITR itr) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo los usuarios con rol ADMINISTRADOR pueden crear ITRs.");
        }
        return itrServicio.crearITR(itr);
    }

    public ITR obtenerITR(int idItr) throws SQLException {
        return itrServicio.obtenerITR(idItr);
    }

    public List<ITR> listarTodos() throws SQLException {
        return itrServicio.listarTodos();
    }

    public boolean actualizarITR(ITR itr) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo los usuarios con rol ADMINISTRADOR pueden modificar ITRs.");
        }
        return itrServicio.actualizarITR(itr);
    }

    public boolean eliminarITR(int idItr) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo los usuarios con rol ADMINISTRADOR pueden eliminar ITRs.");
        }
        return itrServicio.eliminarITR(idItr);
    }
}
