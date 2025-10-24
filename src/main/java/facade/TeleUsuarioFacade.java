package facade;

import DAO.TeleUsuarioDAOImpl;
import modelo.TeleUsuario;

import java.sql.SQLException;
import java.util.List;

public class TeleUsuarioFacade {

    private final TeleUsuarioDAOImpl teleUsuarioDAOImpl;

    public TeleUsuarioFacade() throws SQLException {
        this.teleUsuarioDAOImpl = new TeleUsuarioDAOImpl();
    }

    // 🔹 Crear un nuevo teléfono
    public TeleUsuario crearTelefono(String numero, int idUsuario) throws SQLException {
        TeleUsuario teleUsuario = new TeleUsuario(numero, idUsuario);
        return teleUsuarioDAOImpl.crearTeleUsuario(teleUsuario);
    }

    // 🔹 Obtener teléfono por ID
    public TeleUsuario obtenerTelefono(int idTelefono) throws SQLException {
        return teleUsuarioDAOImpl.obtenerTeleUsuario(idTelefono);
    }

    // 🔹 Listar todos los teléfonos
    public List<TeleUsuario> listarTelefonos() throws SQLException {
        return teleUsuarioDAOImpl.listarTodos();
    }

    // 🔹 Actualizar un teléfono
    public boolean actualizarTelefono(int idTelefono, String numero, int idUsuario) throws SQLException {
        TeleUsuario teleUsuario = new TeleUsuario(idTelefono, numero, idUsuario);
        return teleUsuarioDAOImpl.actualizarTeleUsuario(teleUsuario);
    }

    // 🔹 Eliminar un teléfono
    public boolean eliminarTelefono(int idTelefono) throws SQLException {
        return teleUsuarioDAOImpl.eliminarTeleUsuario(idTelefono);
    }

    // 🔹 Listar teléfonos por usuario
    public List<TeleUsuario> listarTelefonosPorUsuario(int idUsuario) throws SQLException {
        return teleUsuarioDAOImpl.listarTodos().stream()
                .filter(t -> t.getIdUsuario() == idUsuario)
                .toList();
    }
}
