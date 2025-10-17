package facade;

import modelo.InstanciaComun;
import servicios.InstanciaComunService;

import java.sql.SQLException;
import java.util.List;

public class InstanciaComunFacade {

    private final InstanciaComunService instanciaComunService;

    public InstanciaComunFacade() throws SQLException {
        this.instanciaComunService = new InstanciaComunService();
    }

    // ============================================================
    // CREAR VÍNCULO
    // ============================================================
    public InstanciaComun crearVinculo(int idInstancia, int idSeguimiento) throws SQLException {
        return instanciaComunService.crearVinculo(idInstancia, idSeguimiento);
    }

    // ============================================================
    // OBTENER VÍNCULO POR INSTANCIA
    // ============================================================
    public InstanciaComun obtenerPorInstancia(int idInstancia) throws SQLException {
        return instanciaComunService.obtenerPorInstancia(idInstancia);
    }

    // ============================================================
    // LISTAR VÍNCULOS
    // ============================================================
    public List<InstanciaComun> listarTodos() throws SQLException {
        return instanciaComunService.listarTodos();
    }

    public List<InstanciaComun> listarPorSeguimiento(int idSeguimiento) throws SQLException {
        return instanciaComunService.listarPorSeguimiento(idSeguimiento);
    }

    // ============================================================
    // ELIMINAR VÍNCULO
    // ============================================================
    public boolean eliminarPorInstancia(int idInstancia) throws SQLException {
        return instanciaComunService.eliminarPorInstancia(idInstancia);
    }
}
