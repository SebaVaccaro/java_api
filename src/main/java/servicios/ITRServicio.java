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

    // Obtener ITR por ID
    public ITR obtenerITR(int idItr) throws SQLException {
        return dao.obtenerITR(idItr);
    }

    // Listar todos los ITRs
    public List<ITR> listarTodos() throws SQLException {
        return dao.listarTodos();
    }
}
