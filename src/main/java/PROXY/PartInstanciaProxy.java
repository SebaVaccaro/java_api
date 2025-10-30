package PROXY;

import modelo.PartInstancia;
import servicios.PartInstanciaServicio;

import java.sql.SQLException;
import java.util.List;

public class PartInstanciaProxy {

    private final PartInstanciaServicio partInstanciaServicio;

    public PartInstanciaProxy() throws SQLException {
        this.partInstanciaServicio = new PartInstanciaServicio();
    }

    // ============================================================
    // AGREGAR PARTICIPANTE A UNA INSTANCIA
    // ============================================================
    public boolean agregarParticipante(int idParticipante, int idInstancia) throws SQLException {
        return partInstanciaServicio.agregarParticipante(idParticipante, idInstancia);
    }

    // ============================================================
    // ELIMINAR PARTICIPANTE DE UNA INSTANCIA
    // ============================================================
    public boolean eliminarParticipante(int idParticipante, int idInstancia) throws SQLException {
        return partInstanciaServicio.eliminarParticipante(idParticipante, idInstancia);
    }

    // ============================================================
    // LISTAR TODAS LAS RELACIONES PARTICIPANTE-INSTANCIA
    // ============================================================
    public List<PartInstancia> listarTodos() throws SQLException {
        return partInstanciaServicio.listarTodos();
    }

    // ============================================================
    // LISTAR INSTANCIAS DE UN PARTICIPANTE
    // ============================================================
    public List<Integer> listarInstanciasPorParticipante(int idParticipante) throws SQLException {
        return partInstanciaServicio.listarInstanciasPorParticipante(idParticipante);
    }

    // ============================================================
    // LISTAR PARTICIPANTES DE UNA INSTANCIA
    // ============================================================
    public List<Integer> listarParticipantesPorInstancia(int idInstancia) throws SQLException {
        return partInstanciaServicio.listarParticipantesPorInstancia(idInstancia);
    }
}
