package PROXY;

import modelo.TeleUsuario;
import servicios.TeleUsuarioServicio;
import utils.ValidarUsuario;

import java.sql.SQLException;
import java.util.List;

public class TeleUsuarioProxy {

    private final TeleUsuarioServicio teleUsuarioServicio;
    private final ValidarUsuario validarUsuario;

    // Constructor: inicializa el servicio de teléfonos de usuario
    public TeleUsuarioProxy() throws SQLException {
        this.teleUsuarioServicio = new TeleUsuarioServicio();
        this.validarUsuario = new ValidarUsuario();
    }

    // Crear un nuevo teléfono (administrador o propietario)
    public TeleUsuario crearTelefono(String numero, int idUsuario) throws SQLException {
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(idUsuario)) {
            throw new SecurityException("Solo un administrador o el propietario pueden crear un teléfono para este usuario.");
        }
        return teleUsuarioServicio.agregarTelefono(numero, idUsuario);
    }

    // Obtener teléfono por ID (administrador o propietario)
    public TeleUsuario obtenerTelefono(int idTelefono) throws SQLException {
        TeleUsuario tel = teleUsuarioServicio.buscarPorId(idTelefono);
        if (tel == null) {
            throw new IllegalArgumentException("No se encontro este teléfono.");
        }
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(tel.getIdUsuario())) {
            throw new SecurityException("Solo un administrador o el propietario pueden consultar este teléfono.");
        }
        return tel;
    }

    // Listar todos los teléfonos (solo administrador)
    public List<TeleUsuario> listarTelefonos() throws SQLException {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo un administrador puede listar todos los teléfonos.");
        }
        return teleUsuarioServicio.listarTodos();
    }

    // Actualizar un teléfono (administrador o propietario)
    public boolean actualizarTelefono(int idTelefono, String numero, int idUsuario) throws SQLException {
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(idUsuario)) {
            throw new SecurityException("Solo un administrador o el propietario pueden actualizar este teléfono.");
        }
        return teleUsuarioServicio.actualizarTelefono(idTelefono, numero, idUsuario);
    }

    // Eliminar un teléfono (administrador o propietario)
    public boolean eliminarTelefono(int idTelefono) throws SQLException {
        TeleUsuario tel = teleUsuarioServicio.buscarPorId(idTelefono);
        if(tel == null){
            throw new IllegalArgumentException("No se encontró un numero con el ID especificado.");
        }
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(tel.getIdUsuario())) {
            throw new SecurityException("Solo un administrador o el propietario pueden eliminar este teléfono.");
        }
        return teleUsuarioServicio.eliminarTelefono(idTelefono);
    }

    // Listar teléfonos de un usuario específico (administrador o propietario)
    public List<TeleUsuario> listarTelefonosPorUsuario(int idUsuario) throws SQLException {
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(idUsuario)) {
            throw new SecurityException("Solo un administrador o el propietario pueden listar los teléfonos de este usuario.");
        }
        return teleUsuarioServicio.listarTodos().stream()
                .filter(t -> t.getIdUsuario() == idUsuario)
                .toList();
    }
}
