package PROXY;

import modelo.TeleITR;
import servicios.TeleITRServicio;
import utils.ValidarUsuario;

import java.sql.SQLException;
import java.util.List;

public class TeleITRProxy {

    private final TeleITRServicio teleITRServicio;
    private final ValidarUsuario validarUsuario;

    public TeleITRProxy() throws Exception {
        this.teleITRServicio = new TeleITRServicio();
        this.validarUsuario = new ValidarUsuario();
    }

    public boolean agregarTelefono(String numero, int idItr) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo los usuarios con rol ADMINISTRADOR pueden agregar teléfonos.");
        }
        return teleITRServicio.agregarTelefono(numero, idItr);
    }

    public boolean actualizarTelefono(int idTelefono, String numero, int idItr) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo los usuarios con rol ADMINISTRADOR pueden modificar teléfonos.");
        }
        return teleITRServicio.actualizarTelefono(idTelefono, numero, idItr);
    }

    public boolean eliminarTelefono(int idTelefono) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo los usuarios con rol ADMINISTRADOR pueden eliminar teléfonos.");
        }
        return teleITRServicio.eliminarTelefono(idTelefono);
    }

    public TeleITR buscarPorId(int idTelefono) throws SQLException {
        return teleITRServicio.buscarPorId(idTelefono);
    }

    public List<TeleITR> listarTodos() throws SQLException {
        return teleITRServicio.listarTodos();
    }
}
