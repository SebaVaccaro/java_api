package DAO.interfaz;

import modelo.TeleITR;
import java.sql.SQLException;
import java.util.List;

public interface TeleITRDAO {

    // Agregar un teléfono ITR
    boolean agregar(TeleITR t) throws SQLException;

    // Actualizar un teléfono ITR
    boolean actualizar(TeleITR t) throws SQLException;

    // Eliminar un teléfono ITR
    boolean eliminar(int idTelefono) throws SQLException;

    // Buscar teléfono por ID
    TeleITR buscarPorId(int idTelefono) throws SQLException;

    // Listar todos los teléfonos ITR
    List<TeleITR> listarTodos() throws SQLException;
}
