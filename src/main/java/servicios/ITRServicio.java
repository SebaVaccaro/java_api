package servicios;

import DAO.ITRDAOImpl;
import modelo.ITR;

import java.sql.SQLException;
import java.util.List;

public class ITRServicio {

    private final ITRDAOImpl dao;

    public ITRServicio() throws SQLException {
        this.dao = new ITRDAOImpl();
    }

    public ITR crearITR(ITR itr) throws SQLException {
        // Aquí podrías validar que idDireccion exista
        return dao.crearITR(itr);
    }

    public ITR obtenerITR(int idItr) throws SQLException {
        return dao.obtenerITR(idItr);
    }

    public List<ITR> listarTodos() throws SQLException {
        return dao.listarTodos();
    }

    public boolean actualizarITR(ITR itr) throws SQLException {
        return dao.actualizarITR(itr);
    }

    public boolean eliminarITR(int idItr) throws SQLException {
        return dao.eliminarITR(idItr);
    }
}
