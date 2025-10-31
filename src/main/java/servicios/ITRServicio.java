package servicios;

import DAO.ITRDAOImpl;
import modelo.ITR;

import java.sql.SQLException;
import java.util.List;

public class ITRServicio {

    private final ITRDAOImpl dao;

    // Constructor: inicializa el DAO
    public ITRServicio() throws SQLException {
        this.dao = new ITRDAOImpl();
    }

    // Crear nuevo ITR
    public ITR crearITR(ITR itr) throws SQLException {
        // Aquí podrías validar que idDireccion exista
        return dao.crearITR(itr);
    }

    // Obtener ITR por ID
    public ITR obtenerITR(int idItr) throws SQLException {
        return dao.obtenerITR(idItr);
    }

    // Listar todos los ITRs
    public List<ITR> listarTodos() throws SQLException {
        return dao.listarTodos();
    }

    // Actualizar ITR
    public boolean actualizarITR(ITR itr) throws SQLException {
        return dao.actualizarITR(itr);
    }

    // Eliminar ITR
    public boolean eliminarITR(int idItr) throws SQLException {
        return dao.eliminarITR(idItr);
    }
}
