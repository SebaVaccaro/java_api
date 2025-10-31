package servicios;

import DAO.PartInstanciaDAOImpl;
import modelo.PartInstancia;

import java.sql.SQLException;
import java.util.List;

public class PartInstanciaServicio {

    private final PartInstanciaDAOImpl dao;

    // Constructor: inicializa el DAO de PartInstancia
    public PartInstanciaServicio() throws SQLException {
        this.dao = new PartInstanciaDAOImpl();
    }

    // Agregar participante a una instancia
    public boolean agregarParticipante(int idParticipante, int idInstancia) throws SQLException {
        if (idParticipante <= 0 || idInstancia <= 0) {
            throw new IllegalArgumentException("ID de participante o instancia inválido.");
        }
        PartInstancia pi = new PartInstancia(idParticipante, idInstancia);
        return dao.agregarParticipante(pi);
    }

    // Eliminar participante de una instancia
    public boolean eliminarParticipante(int idParticipante, int idInstancia) throws SQLException {
        if (idParticipante <= 0 || idInstancia <= 0) {
            throw new IllegalArgumentException("ID de participante o instancia inválido.");
        }
        PartInstancia pi = new PartInstancia(idParticipante, idInstancia);
        return dao.eliminarParticipante(pi);
    }

    // Listar todas las relaciones participante-instancia
    public List<PartInstancia> listarTodos() throws SQLException {
        return dao.listarTodos();
    }

    // Listar instancias de un participante
    public List<Integer> listarInstanciasPorParticipante(int idParticipante) throws SQLException {
        if (idParticipante <= 0) {
            throw new IllegalArgumentException("ID de participante inválido.");
        }
        return dao.listarInstanciasPorParticipante(idParticipante);
    }

    // Listar participantes de una instancia
    public List<Integer> listarParticipantesPorInstancia(int idInstancia) throws SQLException {
        if (idInstancia <= 0) {
            throw new IllegalArgumentException("ID de instancia inválido.");
        }
        return dao.listarParticipantesPorInstancia(idInstancia);
    }
}
