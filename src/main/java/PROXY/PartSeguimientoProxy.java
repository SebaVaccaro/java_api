package PROXY;

import modelo.PartSeguimiento;
import servicios.PartSeguimientoServicio;

import java.sql.SQLException;
import java.util.List;

public class PartSeguimientoProxy {

    private final PartSeguimientoServicio partSeguimientoServicio;

    public PartSeguimientoProxy() throws SQLException {
        this.partSeguimientoServicio = new PartSeguimientoServicio();
    }

    // ============================================================
    // AGREGAR PARTICIPANTE A UN SEGUIMIENTO
    // ============================================================
    public boolean agregarParticipante(int idParticipante, int idSeguimiento) throws SQLException {
        return partSeguimientoServicio.agregarParticipante(idParticipante, idSeguimiento);
    }

    // ============================================================
    // ELIMINAR PARTICIPANTE DE UN SEGUIMIENTO
    // ============================================================
    public boolean eliminarParticipante(int idParticipante, int idSeguimiento) throws SQLException {
        return partSeguimientoServicio.eliminarParticipante(idParticipante, idSeguimiento);
    }

    // ============================================================
    // LISTAR TODAS LAS RELACIONES PARTICIPANTE-SEGUIMIENTO
    // ============================================================
    public List<PartSeguimiento> listarTodos() throws SQLException {
        return partSeguimientoServicio.listarTodos();
    }

    // ============================================================
    // LISTAR SEGUIMIENTOS DE UN PARTICIPANTE
    // ============================================================
    public List<Integer> listarSeguimientosPorParticipante(int idParticipante) throws SQLException {
        return partSeguimientoServicio.listarSeguimientosPorParticipante(idParticipante);
    }

    // ============================================================
    // LISTAR PARTICIPANTES DE UN SEGUIMIENTO
    // ============================================================
    public List<Integer> listarParticipantesPorSeguimiento(int idSeguimiento) throws SQLException {
        return partSeguimientoServicio.listarParticipantesPorSeguimiento(idSeguimiento);
    }
}
