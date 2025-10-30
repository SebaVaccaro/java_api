package PROXY;

import modelo.TeleUsuario;
import servicios.TeleUsuarioServicio;

import java.sql.SQLException;
import java.util.List;

public class TeleUsuarioProxy {

    private final TeleUsuarioServicio teleUsuarioServicio;

    public TeleUsuarioProxy() throws SQLException {
        this.teleUsuarioServicio = new TeleUsuarioServicio();
    }

    // 🔹 Crear un nuevo teléfono
    public TeleUsuario crearTelefono(String numero, int idUsuario) throws SQLException {
        return teleUsuarioServicio.agregarTelefono(numero, idUsuario);
    }

    // 🔹 Obtener teléfono por ID
    public TeleUsuario obtenerTelefono(int idTelefono) throws SQLException {
        return teleUsuarioServicio.buscarPorId(idTelefono);
    }

    // 🔹 Listar todos los teléfonos
    public List<TeleUsuario> listarTelefonos() throws SQLException {
        return teleUsuarioServicio.listarTodos();
    }

    // 🔹 Actualizar un teléfono
    public boolean actualizarTelefono(int idTelefono, String numero, int idUsuario) throws SQLException {
        return teleUsuarioServicio.actualizarTelefono(idTelefono, numero, idUsuario);
    }

    // 🔹 Eliminar un teléfono
    public boolean eliminarTelefono(int idTelefono) throws SQLException {
        return teleUsuarioServicio.eliminarTelefono(idTelefono);
    }

    // 🔹 Listar teléfonos por usuario
    public List<TeleUsuario> listarTelefonosPorUsuario(int idUsuario) throws SQLException {
        return teleUsuarioServicio.listarTodos().stream()
                .filter(t -> t.getIdUsuario() == idUsuario)
                .toList();
    }
}
