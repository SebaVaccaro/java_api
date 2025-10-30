package PROXY;

import modelo.Pertenece;
import servicios.PerteneceServicio;

import java.sql.SQLException;
import java.util.List;

public class PerteneceProxy {

    private final PerteneceServicio perteneceServicio;

    public PerteneceProxy() throws SQLException {
        this.perteneceServicio = new PerteneceServicio();
    }

    // ============================================================
    // AGREGAR RELACIÓN CARRERA ↔ ITR
    // ============================================================
    public boolean agregarPertenece(int idCarrera, int idItr) throws SQLException {
        return perteneceServicio.agregarPertenece(idCarrera, idItr);
    }

    // ============================================================
    // ELIMINAR RELACIÓN CARRERA ↔ ITR
    // ============================================================
    public boolean eliminarPertenece(int idCarrera, int idItr) throws SQLException {
        return perteneceServicio.eliminarPertenece(idCarrera, idItr);
    }

    // ============================================================
    // LISTAR TODAS LAS RELACIONES
    // ============================================================
    public List<Pertenece> listarTodos() throws SQLException {
        return perteneceServicio.listarTodos();
    }

    // ============================================================
    // LISTAR ITRs DE UNA CARRERA
    // ============================================================
    public List<Integer> listarItrPorCarrera(int idCarrera) throws SQLException {
        return perteneceServicio.listarItrPorCarrera(idCarrera);
    }

    // ============================================================
    // LISTAR CARRERAS DE UN ITR
    // ============================================================
    public List<Integer> listarCarrerasPorItr(int idItr) throws SQLException {
        return perteneceServicio.listarCarrerasPorItr(idItr);
    }
}
