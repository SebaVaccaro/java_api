package PROXY;

import modelo.PartInstancia;
import servicios.PartInstanciaServicio;

import java.sql.SQLException;
import java.util.List;

public class PartInstanciaProxy {

    private final PartInstanciaServicio partInstanciaServicio;

    // Constructor: inicializa el servicio de participante-instancia
    public PartInstanciaProxy() throws SQLException {
        this.partInstanciaServicio = new PartInstanciaServicio();
    }

    // Agregar participante a una instancia (sin restricción de permisos)
    public boolean agregarParticipante(int idParticipante, int idInstancia) throws SQLException {
        return partInstanciaServicio.agregarParticipante(idParticipante, idInstancia);
    }

    // Eliminar participante de una instancia (sin restricción de permisos)
    public boolean eliminarParticipante(int idParticipante, int idInstancia) throws SQLException {
        return partInstanciaServicio.eliminarParticipante(idParticipante, idInstancia);
    }

    // Listar todas las relaciones participante-instancia (sin restricción de permisos)
    public List<PartInstancia> listarTodos() throws SQLException {
        return partInstanciaServicio.listarTodos();
    }

    // Listar instancias de un participante (sin restricción de permisos)
    public List<Integer> listarInstanciasPorParticipante(int idParticipante) throws SQLException {
        return partInstanciaServicio.listarInstanciasPorParticipante(idParticipante);
    }

    // Listar participantes de una instancia (sin restricción de permisos)
    public List<Integer> listarParticipantesPorInstancia(int idInstancia) throws SQLException {
        return partInstanciaServicio.listarParticipantesPorInstancia(idInstancia);
    }
}

