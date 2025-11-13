package servicios;

import DAO.PartInstanciaDAOImpl;
import DAO.InstanciaComunDAOImpl;
import DAO.FuncionarioDAOImpl;
import modelo.PartInstancia;
import SINGLETON.ConexionSingleton;

import java.sql.SQLException;
import java.util.List;

public class PartInstanciaServicio {

    private final PartInstanciaDAOImpl dao;
    private final InstanciaComunDAOImpl instanciaDAO;
    private final FuncionarioDAOImpl funcionarioDAO;

    // Constructor
    public PartInstanciaServicio() throws SQLException {
        this.dao = new PartInstanciaDAOImpl();
        this.instanciaDAO = new InstanciaComunDAOImpl();
        this.funcionarioDAO = new FuncionarioDAOImpl(ConexionSingleton.getInstance().getConexion());
    }

    // Agregar participante a una instancia
    public boolean agregarParticipante(int idParticipante, int idInstancia) throws SQLException {
        if (idParticipante <= 0 || idInstancia <= 0) {
            throw new IllegalArgumentException("ID de participante o instancia inválido.");
        }

        if (funcionarioDAO.obtenerFuncionario(idParticipante) == null) {
            throw new IllegalArgumentException("El participante con ID " + idParticipante + " no existe.");
        }

        if (instanciaDAO.obtenerInstanciaComun(idInstancia) == null) {
            throw new IllegalArgumentException("La instancia con ID " + idInstancia + " no existe.");
        }

        List<Integer> participantesExistentes = dao.listarParticipantesPorInstancia(idInstancia);
        if (participantesExistentes.contains(idParticipante)) {
            throw new IllegalArgumentException("El participante con ID " + idParticipante +
                    " ya está agregado a la instancia con ID " + idInstancia + ".");
        }

        PartInstancia pi = new PartInstancia(idParticipante, idInstancia);
        return dao.agregarParticipante(pi);
    }

    // Eliminar participante de una instancia
    public boolean eliminarParticipante(int idParticipante, int idInstancia) throws SQLException {
        if (idParticipante <= 0 || idInstancia <= 0) {
            throw new IllegalArgumentException("ID de participante o instancia inválido.");
        }

        if (funcionarioDAO.obtenerFuncionario(idParticipante) == null) {
            throw new IllegalArgumentException("El participante con ID " + idParticipante + " no existe.");
        }

        if (instanciaDAO.obtenerInstanciaComun(idInstancia) == null) {
            throw new IllegalArgumentException("La instancia con ID " + idInstancia + " no existe.");
        }

        List<Integer> participantesExistentes = dao.listarParticipantesPorInstancia(idInstancia);
        if (!participantesExistentes.contains(idParticipante)) {
            throw new IllegalArgumentException("El participante con ID " + idParticipante +
                    " no está agregado a la instancia con ID " + idInstancia + ".");
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

        if (funcionarioDAO.obtenerFuncionario(idParticipante) == null) {
            throw new IllegalArgumentException("El participante con ID " + idParticipante + " no existe.");
        }

        return dao.listarInstanciasPorParticipante(idParticipante);
    }
    // Listar participantes de una instancia
    public List<Integer> listarParticipantesPorInstancia(int idInstancia) throws SQLException {
        if (idInstancia <= 0) {
            throw new IllegalArgumentException("ID de instancia inválido.");
        }

        if (instanciaDAO.obtenerInstanciaComun(idInstancia) == null) {
            throw new IllegalArgumentException("La instancia con ID " + idInstancia + " no existe.");
        }

        return dao.listarParticipantesPorInstancia(idInstancia);
    }
}