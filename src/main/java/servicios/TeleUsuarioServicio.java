package servicios;

import DAO.TeleUsuarioDAOImpl;
import modelo.TeleUsuario;

import java.sql.SQLException;
import java.util.List;

public class TeleUsuarioServicio {

    private final TeleUsuarioDAOImpl dao;

    // Constructor: inicializa DAO de TeleUsuario
    public TeleUsuarioServicio() throws SQLException {
        this.dao = new TeleUsuarioDAOImpl();
    }

    // Agregar teléfono a un usuario
    public TeleUsuario agregarTelefono(String numero, int idUsuario) throws SQLException {
        if (numero == null || numero.isBlank())
            throw new IllegalArgumentException("Número no puede estar vacío.");
        if (idUsuario <= 0)
            throw new IllegalArgumentException("ID de usuario inválido.");

        TeleUsuario t = new TeleUsuario(numero, idUsuario);
        return dao.crearTeleUsuario(t);
    }

    // Actualizar teléfono
    public boolean actualizarTelefono(int idTelefono, String numero, int idUsuario) throws SQLException {
        if (idTelefono <= 0)
            throw new IllegalArgumentException("ID de teléfono inválido.");
        if (numero == null || numero.isBlank())
            throw new IllegalArgumentException("Número no puede estar vacío.");
        if (idUsuario <= 0)
            throw new IllegalArgumentException("ID de usuario inválido.");

        TeleUsuario t = new TeleUsuario(idTelefono, numero, idUsuario);
        return dao.actualizarTeleUsuario(t);
    }

    // Eliminar teléfono
    public boolean eliminarTelefono(int idTelefono) throws SQLException {
        if (idTelefono <= 0)
            throw new IllegalArgumentException("ID de teléfono inválido.");
        return dao.eliminarTeleUsuario(idTelefono);
    }

    // Buscar teléfono por ID
    public TeleUsuario buscarPorId(int idTelefono) throws SQLException {
        if (idTelefono <= 0)
            throw new IllegalArgumentException("ID de teléfono inválido.");
        return dao.obtenerTeleUsuario(idTelefono);
    }

    // Listar todos los teléfonos
    public List<TeleUsuario> listarTodos() throws SQLException {
        return dao.listarTodos();
    }
}
