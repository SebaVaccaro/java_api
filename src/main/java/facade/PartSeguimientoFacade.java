package facade;

import modelo.PartSeguimiento;
import servicios.PartSeguimientoService;

import java.sql.SQLException;
import java.util.List;

public class PartSeguimientoFacade {

    private final PartSeguimientoService partSeguimientoService;

    public PartSeguimientoFacade() throws SQLException {
        this.partSeguimientoService = new PartSeguimientoService();
    }

    // ============================================================
    // AGREGAR PARTICIPANTE A UN SEGUIMIENTO
    // ============================================================
    public boolean agregarParticipante(int idParticipante, int idSeguimiento) throws SQLException {
        return partSeguimientoService.agregarParticipante(idParticipante, idSeguimiento);
    }

    // ============================================================
    // ELIMINAR PARTICIPANTE DE UN SEGUIMIENTO
    // ============================================================
    public boolean eliminarParticipante(int idParticipante, int idSeguimiento) throws SQLException {
        return partSeguimientoService.eliminarParticipante(idParticipante, idSeguimiento);
    }

    // ============================================================
    // LISTAR TODAS LAS RELACIONES PARTICIPANTE-SEGUIMIENTO
    // ============================================================
    public List<PartSeguimiento> listarTodos() throws SQLException {
        return partSeguimientoService.listarTodos();
    }

    // ============================================================
    // LISTAR SEGUIMIENTOS DE UN PARTICIPANTE
    // ============================================================
    public List<Integer> listarSeguimientosPorParticipante(int idParticipante) throws SQLException {
        return partSeguimientoService.listarSeguimientosPorParticipante(idParticipante);
    }

    // ============================================================
    // LISTAR PARTICIPANTES DE UN SEGUIMIENTO
    // ============================================================
    public List<Integer> listarParticipantesPorSeguimiento(int idSeguimiento) throws SQLException {
        return partSeguimientoService.listarParticipantesPorSeguimiento(idSeguimiento);
    }
}
