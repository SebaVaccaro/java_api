package PROXY;

import modelo.TeleUsuario;
import servicios.TeleUsuarioServicio;

import java.sql.SQLException;
import java.util.List;

public class TeleUsuarioProxy {

    private final TeleUsuarioServicio teleUsuarioServicio;

    // Constructor: inicializa el servicio de teléfonos de usuario
    public TeleUsuarioProxy() throws SQLException {
        this.teleUsuarioServicio = new TeleUsuarioServicio();
    }

    // Crear un nuevo teléfono (sin restricción de permisos)
    public TeleUsuario crearTelefono(String numero, int idUsuario) throws SQLException {
        return teleUsuarioServicio.agregarTelefono(numero, idUsuario);
    }

    // Obtener teléfono por ID (sin restricción de permisos)
    public TeleUsuario obtenerTelefono(int idTelefono) throws SQLException {
        return teleUsuarioServicio.buscarPorId(idTelefono);
    }

    // Listar todos los teléfonos (sin restricción de permisos)
    public List<TeleUsuario> listarTelefonos() throws SQLException {
        return teleUsuarioServicio.listarTodos();
    }

    // Actualizar un teléfono (sin restricción de permisos)
    public boolean actualizarTelefono(int idTelefono, String numero, int idUsuario) throws SQLException {
        return teleUsuarioServicio.actualizarTelefono(idTelefono, numero, idUsuario);
    }

    // Eliminar un teléfono (sin restricción de permisos)
    public boolean eliminarTelefono(int idTelefono) throws SQLException {
        return teleUsuarioServicio.eliminarTelefono(idTelefono);
    }

    // Listar teléfonos de un usuario específico (sin restricción de permisos)
    public List<TeleUsuario> listarTelefonosPorUsuario(int idUsuario) throws SQLException {
        return teleUsuarioServicio.listarTodos().stream()
                .filter(t -> t.getIdUsuario() == idUsuario)
                .toList();
    }
}

