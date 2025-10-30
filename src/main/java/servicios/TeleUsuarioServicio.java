package servicios;

import DAO.TeleUsuarioDAOImpl;
import modelo.TeleUsuario;

import java.sql.SQLException;
import java.util.List;

public class TeleUsuarioServicio {

    private final TeleUsuarioDAOImpl dao;

    public TeleUsuarioServicio() throws SQLException {
        this.dao = new TeleUsuarioDAOImpl();
    }

    // ============================================================
    // AGREGAR TELÉFONO A UN USUARIO
    // ============================================================
    public TeleUsuario agregarTelefono(String numero, int idUsuario) throws SQLException {
        if (numero == null || numero.isBlank())
            throw new IllegalArgumentException("Número no puede estar vacío.");
        if (idUsuario <= 0)
            throw new IllegalArgumentException("ID de usuario inválido.");

        TeleUsuario t = new TeleUsuario(numero, idUsuario);
        return dao.crearTeleUsuario(t); // ✅ Cambiado a método real del DAO
    }

    // ============================================================
    // ACTUALIZAR TELÉFONO
    // ============================================================
    public boolean actualizarTelefono(int idTelefono, String numero, int idUsuario) throws SQLException {
        if (idTelefono <= 0)
            throw new IllegalArgumentException("ID de teléfono inválido.");
        if (numero == null || numero.isBlank())
            throw new IllegalArgumentException("Número no puede estar vacío.");
        if (idUsuario <= 0)
            throw new IllegalArgumentException("ID de usuario inválido.");

        TeleUsuario t = new TeleUsuario(idTelefono, numero, idUsuario);
        return dao.actualizarTeleUsuario(t); // ✅ Cambiado
    }

    // ============================================================
    // ELIMINAR TELÉFONO
    // ============================================================
    public boolean eliminarTelefono(int idTelefono) throws SQLException {
        if (idTelefono <= 0)
            throw new IllegalArgumentException("ID de teléfono inválido.");
        return dao.eliminarTeleUsuario(idTelefono); // ✅ Cambiado
    }

    // ============================================================
    // BUSCAR TELÉFONO POR ID
    // ============================================================
    public TeleUsuario buscarPorId(int idTelefono) throws SQLException {
        if (idTelefono <= 0)
            throw new IllegalArgumentException("ID de teléfono inválido.");
        return dao.obtenerTeleUsuario(idTelefono); // ✅ Cambiado
    }

    // ============================================================
    // LISTAR TODOS LOS TELÉFONOS
    // ============================================================
    public List<TeleUsuario> listarTodos() throws SQLException {
        return dao.listarTodos(); // ✅ Este ya estaba bien
    }
}
