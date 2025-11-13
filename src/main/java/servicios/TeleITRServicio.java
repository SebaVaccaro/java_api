package servicios;

import DAO.TeleITRDAOImpl;
import DAO.ITRDAOImpl;
import modelo.TeleITR;
import modelo.ITR;

import java.sql.SQLException;
import java.util.List;

public class TeleITRServicio {

    private final TeleITRDAOImpl dao;
    private final ITRDAOImpl itrDAO;

    // Constructor: inicializa los DAOs necesarios
    public TeleITRServicio() throws SQLException {
        this.dao = new TeleITRDAOImpl();
        this.itrDAO = new ITRDAOImpl();
    }

    // Agregar teléfono a un ITR
    public boolean agregarTelefono(String numero, int idItr) throws SQLException {
        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("El número de teléfono no puede estar vacío.");
        }

        if (!numero.matches("[0-9\\s\\-()+]+")) {
            throw new IllegalArgumentException("El número de teléfono contiene caracteres inválidos.");
        }

        if (idItr <= 0) {
            throw new IllegalArgumentException("ID de ITR inválido: debe ser mayor que 0.");
        }

        ITR itr = itrDAO.obtenerITR(idItr);
        if (itr == null) {
            throw new IllegalArgumentException("El ITR con ID " + idItr + " no existe.");
        }

        List<TeleITR> telefonos = dao.listarTodos();
        for (TeleITR tel : telefonos) {
            if (tel.getIdItr() == idItr && tel.getNumero().equals(numero.trim())) {
                throw new IllegalArgumentException("El número " + numero + " ya está registrado para el ITR " + idItr + ".");
            }
        }

        TeleITR t = new TeleITR(numero.trim(), idItr);
        return dao.agregar(t);
    }

    // Actualizar teléfono
    public boolean actualizarTelefono(int idTelefono, String numero, int idItr) throws SQLException {

        if (idTelefono <= 0) {
            throw new IllegalArgumentException("ID de teléfono inválido: debe ser mayor que 0.");
        }

        TeleITR telefonoExistente = dao.buscarPorId(idTelefono);
        if (telefonoExistente == null) {
            throw new IllegalArgumentException("El teléfono con ID " + idTelefono + " no existe.");
        }

        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("El número de teléfono no puede estar vacío.");
        }

        if (!numero.matches("[0-9\\s\\-()+]+")) {
            throw new IllegalArgumentException("El número de teléfono contiene caracteres inválidos.");
        }

        if (idItr <= 0) {
            throw new IllegalArgumentException("ID de ITR inválido: debe ser mayor que 0.");
        }

        ITR itr = itrDAO.obtenerITR(idItr);
        if (itr == null) {
            throw new IllegalArgumentException("El ITR con ID " + idItr + " no existe.");
        }

        List<TeleITR> telefonos = dao.listarTodos();
        for (TeleITR tel : telefonos) {
            if (tel.getIdTelefono() != idTelefono &&
                    tel.getIdItr() == idItr &&
                    tel.getNumero().equals(numero.trim())) {
                throw new IllegalArgumentException("El número " + numero + " ya está registrado para el ITR " + idItr + ".");
            }
        }

        TeleITR t = new TeleITR(idTelefono, numero.trim(), idItr);
        return dao.actualizar(t);
    }

    // Eliminar teléfono
    public boolean eliminarTelefono(int idTelefono) throws SQLException {

        if (idTelefono <= 0) {
            throw new IllegalArgumentException("ID de teléfono inválido: debe ser mayor que 0.");
        }

        TeleITR telefono = dao.buscarPorId(idTelefono);
        if (telefono == null) {
            throw new IllegalArgumentException("El teléfono con ID " + idTelefono + " no existe.");
        }

        return dao.eliminar(idTelefono);
    }

    // Buscar teléfono por ID
    public TeleITR buscarPorId(int idTelefono) throws SQLException {

        if (idTelefono <= 0) {
            throw new IllegalArgumentException("ID de teléfono inválido: debe ser mayor que 0.");
        }

        TeleITR telefono = dao.buscarPorId(idTelefono);
        if (telefono == null) {
            throw new IllegalArgumentException("El teléfono con ID " + idTelefono + " no existe.");
        }

        return telefono;
    }

    // Listar todos los teléfonos
    public List<TeleITR> listarTodos() throws SQLException {
        return dao.listarTodos();
    }

    // Listar teléfonos por ITR
    public List<TeleITR> listarPorITR(int idItr) throws SQLException {

        if (idItr <= 0) {
            throw new IllegalArgumentException("ID de ITR inválido: debe ser mayor que 0.");
        }

        ITR itr = itrDAO.obtenerITR(idItr);
        if (itr == null) {
            throw new IllegalArgumentException("El ITR con ID " + idItr + " no existe.");
        }

        List<TeleITR> todosTelefonos = dao.listarTodos();
        return todosTelefonos.stream()
                .filter(tel -> tel.getIdItr() == idItr)
                .toList();
    }
}