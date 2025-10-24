package DAO.interfaz;

import modelo.ITR;
import java.sql.SQLException;
import java.util.List;

public interface ITRDAO {

    // Crear un ITR
    ITR crearITR(ITR itr) throws SQLException;

    // Obtener ITR por ID
    ITR obtenerITR(int idItr) throws SQLException;

    // Listar todos los ITRs
    List<ITR> listarTodos() throws SQLException;

    // Actualizar ITR
    boolean actualizarITR(ITR itr) throws SQLException;

    // Eliminar ITR
    boolean eliminarITR(int idItr) throws SQLException;
}
