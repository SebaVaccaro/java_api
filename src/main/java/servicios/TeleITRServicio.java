package servicios;

import DAO.TeleITRDAOImpl;
import modelo.TeleITR;

import java.sql.SQLException;
import java.util.List;

public class TeleITRServicio {

    private final TeleITRDAOImpl dao;

    // Constructor: inicializa DAO de TeleITR
    public TeleITRServicio() throws SQLException {
        this.dao = new TeleITRDAOImpl();
    }

    // Agregar teléfono a un ITR
    public boolean agregarTelefono(String numero, int idItr) throws SQLException {
        if (numero == null || numero.isBlank()) throw new IllegalArgumentException("Número no puede estar vacío.");
        if (idItr <= 0) throw new IllegalArgumentException("ID de ITR inválido.");
        TeleITR t = new TeleITR(numero, idItr);
        return dao.agregar(t);
    }

    // Actualizar teléfono
    public boolean actualizarTelefono(int idTelefono, String numero, int idItr) throws SQLException {
        if (idTelefono <= 0) throw new IllegalArgumentException("ID de teléfono inválido.");
        if (numero == null || numero.isBlank()) throw new IllegalArgumentException("Número no puede estar vacío.");
        if (idItr <= 0) throw new IllegalArgumentException("ID de ITR inválido.");
        TeleITR t = new TeleITR(idTelefono, numero, idItr);
        return dao.actualizar(t);
    }

    // Eliminar teléfono
    public boolean eliminarTelefono(int idTelefono) throws SQLException {
        if (idTelefono <= 0) throw new IllegalArgumentException("ID de teléfono inválido.");
        return dao.eliminar(idTelefono);
    }

    // Buscar teléfono por ID
    public TeleITR buscarPorId(int idTelefono) throws SQLException {
        if (idTelefono <= 0) throw new IllegalArgumentException("ID de teléfono inválido.");
        return dao.buscarPorId(idTelefono);
    }

    // Listar todos los teléfonos
    public List<TeleITR> listarTodos() throws SQLException {
        return dao.listarTodos();
    }
}
