package facade;

import DAO.TeleUsuarioDAO;
import modelo.TeleUsuario;

import java.sql.SQLException;
import java.util.List;

public class TeleUsuarioFacade {

    private final TeleUsuarioDAO teleUsuarioDAO;

    public TeleUsuarioFacade() throws SQLException {
        this.teleUsuarioDAO = new TeleUsuarioDAO();
    }

    // 🔹 Crear un nuevo teléfono
    public TeleUsuario crearTelefono(String numero, int idUsuario) throws SQLException {
        TeleUsuario teleUsuario = new TeleUsuario(numero, idUsuario);
        return teleUsuarioDAO.crearTeleUsuario(teleUsuario);
    }

    // 🔹 Obtener teléfono por ID
    public TeleUsuario obtenerTelefono(int idTelefono) throws SQLException {
        return teleUsuarioDAO.obtenerTeleUsuario(idTelefono);
    }

    // 🔹 Listar todos los teléfonos
    public List<TeleUsuario> listarTelefonos() throws SQLException {
        return teleUsuarioDAO.listarTodos();
    }

    // 🔹 Actualizar un teléfono
    public boolean actualizarTelefono(int idTelefono, String numero, int idUsuario) throws SQLException {
        TeleUsuario teleUsuario = new TeleUsuario(idTelefono, numero, idUsuario);
        return teleUsuarioDAO.actualizarTeleUsuario(teleUsuario);
    }

    // 🔹 Eliminar un teléfono
    public boolean eliminarTelefono(int idTelefono) throws SQLException {
        return teleUsuarioDAO.eliminarTeleUsuario(idTelefono);
    }

    // 🔹 Listar teléfonos por usuario
    public List<TeleUsuario> listarTelefonosPorUsuario(int idUsuario) throws SQLException {
        return teleUsuarioDAO.listarTodos().stream()
                .filter(t -> t.getIdUsuario() == idUsuario)
                .toList();
    }
}
