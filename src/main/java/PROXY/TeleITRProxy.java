package PROXY;

import modelo.TeleITR;
import servicios.TeleITRServicio;
import utils.ValidarUsuario;

import java.sql.SQLException;
import java.util.List;

public class TeleITRProxy {

    private final TeleITRServicio teleITRServicio;
    private final ValidarUsuario validarUsuario;

    // Constructor: inicializa el servicio de teléfonos de ITR y el validador de usuario
    public TeleITRProxy() throws Exception {
        this.teleITRServicio = new TeleITRServicio();
        this.validarUsuario = new ValidarUsuario();
    }

    // Agregar teléfono a un ITR (solo administradores)
    public boolean agregarTelefono(String numero, int idItr) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo los usuarios con rol ADMINISTRADOR pueden agregar teléfonos.");
        }
        return teleITRServicio.agregarTelefono(numero, idItr);
    }

    // Actualizar teléfono de un ITR (solo administradores)
    public boolean actualizarTelefono(int idTelefono, String numero, int idItr) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo los usuarios con rol ADMINISTRADOR pueden modificar teléfonos.");
        }
        return teleITRServicio.actualizarTelefono(idTelefono, numero, idItr);
    }

    // Eliminar teléfono de un ITR (solo administradores)
    public boolean eliminarTelefono(int idTelefono) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo los usuarios con rol ADMINISTRADOR pueden eliminar teléfonos.");
        }
        return teleITRServicio.eliminarTelefono(idTelefono);
    }

    // Buscar teléfono por ID (sin restricción de permisos)
    public TeleITR buscarPorId(int idTelefono) throws SQLException {
        return teleITRServicio.buscarPorId(idTelefono);
    }

    // Listar todos los teléfonos (sin restricción de permisos)
    public List<TeleITR> listarTodos() throws SQLException {
        return teleITRServicio.listarTodos();
    }
}

