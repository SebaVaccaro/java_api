package facade;

import modelo.Pertenece;
import servicios.PerteneceService;

import java.sql.SQLException;
import java.util.List;

public class PerteneceFacade {

    private final PerteneceService perteneceService;

    public PerteneceFacade() throws SQLException {
        this.perteneceService = new PerteneceService();
    }

    // ============================================================
    // AGREGAR RELACIÓN CARRERA ↔ ITR
    // ============================================================
    public boolean agregarPertenece(int idCarrera, int idItr) throws SQLException {
        return perteneceService.agregarPertenece(idCarrera, idItr);
    }

    // ============================================================
    // ELIMINAR RELACIÓN CARRERA ↔ ITR
    // ============================================================
    public boolean eliminarPertenece(int idCarrera, int idItr) throws SQLException {
        return perteneceService.eliminarPertenece(idCarrera, idItr);
    }

    // ============================================================
    // LISTAR TODAS LAS RELACIONES
    // ============================================================
    public List<Pertenece> listarTodos() throws SQLException {
        return perteneceService.listarTodos();
    }

    // ============================================================
    // LISTAR ITRs DE UNA CARRERA
    // ============================================================
    public List<Integer> listarItrPorCarrera(int idCarrera) throws SQLException {
        return perteneceService.listarItrPorCarrera(idCarrera);
    }

    // ============================================================
    // LISTAR CARRERAS DE UN ITR
    // ============================================================
    public List<Integer> listarCarrerasPorItr(int idItr) throws SQLException {
        return perteneceService.listarCarrerasPorItr(idItr);
    }
}
