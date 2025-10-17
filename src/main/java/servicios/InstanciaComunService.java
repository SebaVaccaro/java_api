package servicios;

import DAO.InstanciaComunDAO;
import modelo.InstanciaComun;

import java.sql.SQLException;
import java.util.List;

public class InstanciaComunService {

    private final InstanciaComunDAO dao;

    public InstanciaComunService() throws SQLException {
        this.dao = new InstanciaComunDAO();
    }

    // Crear vínculo
    public InstanciaComun crearVinculo(int idInstancia, int idSeguimiento) throws SQLException {
        InstanciaComun ic = new InstanciaComun(idInstancia, idSeguimiento);
        return dao.crearInstanciaComun(ic);
    }

    // Obtener vínculo por instancia
    public InstanciaComun obtenerPorInstancia(int idInstancia) throws SQLException {
        return dao.obtenerPorInstancia(idInstancia);
    }

    // Listar todos los vínculos
    public List<InstanciaComun> listarTodos() throws SQLException {
        return dao.listarTodos();
    }

    // Listar vínculos por seguimiento
    public List<InstanciaComun> listarPorSeguimiento(int idSeguimiento) throws SQLException {
        return dao.listarPorSeguimiento(idSeguimiento);
    }

    // Eliminar vínculo
    public boolean eliminarPorInstancia(int idInstancia) throws SQLException {
        return dao.eliminarPorInstancia(idInstancia);
    }
}
