package servicios;

import DAO.ITRDAO;
import modelo.ITR;

import java.sql.SQLException;
import java.util.List;

public class ITRService {

    private final ITRDAO dao;

    public ITRService() throws SQLException {
        this.dao = new ITRDAO();
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
