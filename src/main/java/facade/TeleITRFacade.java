package facade;

import modelo.TeleITR;
import servicios.TeleITRService;

import java.sql.SQLException;
import java.util.List;

public class TeleITRFacade {

    private final TeleITRService teleITRService;

    public TeleITRFacade() throws SQLException {
        this.teleITRService = new TeleITRService();
    }

    // ============================================================
    // AGREGAR TELÉFONO A UN ITR
    // ============================================================
    public boolean agregarTelefono(String numero, int idItr) throws SQLException {
        return teleITRService.agregarTelefono(numero, idItr);
    }

    // ============================================================
    // ACTUALIZAR TELÉFONO
    // ============================================================
    public boolean actualizarTelefono(int idTelefono, String numero, int idItr) throws SQLException {
        return teleITRService.actualizarTelefono(idTelefono, numero, idItr);
    }

    // ============================================================
    // ELIMINAR TELÉFONO
    // ============================================================
    public boolean eliminarTelefono(int idTelefono) throws SQLException {
        return teleITRService.eliminarTelefono(idTelefono);
    }

    // ============================================================
    // BUSCAR TELÉFONO POR ID
    // ============================================================
    public TeleITR buscarPorId(int idTelefono) throws SQLException {
        return teleITRService.buscarPorId(idTelefono);
    }

    // ============================================================
    // LISTAR TODOS LOS TELÉFONOS
    // ============================================================
    public List<TeleITR> listarTodos() throws SQLException {
        return teleITRService.listarTodos();
    }
}
