package facade;

import modelo.ITR;
import servicios.ITRService;

import java.sql.SQLException;
import java.util.List;

public class ITRFacade {

    private final ITRService itrService;

    public ITRFacade() throws SQLException {
        this.itrService = new ITRService();
    }

    // ============================================================
    // CREAR ITR
    // ============================================================
    public ITR crearITR(ITR itr) throws SQLException {
        return itrService.crearITR(itr);
    }

    // ============================================================
    // OBTENER ITR
    // ============================================================
    public ITR obtenerITR(int idItr) throws SQLException {
        return itrService.obtenerITR(idItr);
    }

    // ============================================================
    // LISTAR ITRs
    // ============================================================
    public List<ITR> listarTodos() throws SQLException {
        return itrService.listarTodos();
    }

    // ============================================================
    // ACTUALIZAR ITR
    // ============================================================
    public boolean actualizarITR(ITR itr) throws SQLException {
        return itrService.actualizarITR(itr);
    }

    // ============================================================
    // ELIMINAR ITR
    // ============================================================
    public boolean eliminarITR(int idItr) throws SQLException {
        return itrService.eliminarITR(idItr);
    }
}
