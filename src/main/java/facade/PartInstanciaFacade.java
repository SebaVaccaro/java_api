package facade;

import modelo.PartInstancia;
import servicios.PartInstanciaService;

import java.sql.SQLException;
import java.util.List;

public class PartInstanciaFacade {

    private final PartInstanciaService partInstanciaService;

    public PartInstanciaFacade() throws SQLException {
        this.partInstanciaService = new PartInstanciaService();
    }

    // ============================================================
    // AGREGAR PARTICIPANTE A UNA INSTANCIA
    // ============================================================
    public boolean agregarParticipante(int idParticipante, int idInstancia) throws SQLException {
        return partInstanciaService.agregarParticipante(idParticipante, idInstancia);
    }

    // ============================================================
    // ELIMINAR PARTICIPANTE DE UNA INSTANCIA
    // ============================================================
    public boolean eliminarParticipante(int idParticipante, int idInstancia) throws SQLException {
        return partInstanciaService.eliminarParticipante(idParticipante, idInstancia);
    }

    // ============================================================
    // LISTAR TODAS LAS RELACIONES PARTICIPANTE-INSTANCIA
    // ============================================================
    public List<PartInstancia> listarTodos() throws SQLException {
        return partInstanciaService.listarTodos();
    }

    // ============================================================
    // LISTAR INSTANCIAS DE UN PARTICIPANTE
    // ============================================================
    public List<Integer> listarInstanciasPorParticipante(int idParticipante) throws SQLException {
        return partInstanciaService.listarInstanciasPorParticipante(idParticipante);
    }

    // ============================================================
    // LISTAR PARTICIPANTES DE UNA INSTANCIA
    // ============================================================
    public List<Integer> listarParticipantesPorInstancia(int idInstancia) throws SQLException {
        return partInstanciaService.listarParticipantesPorInstancia(idInstancia);
    }
}
