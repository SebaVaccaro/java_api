package servicios;

import DAO.PartSeguimientoDAOImpl;
import DAO.SeguimientoDAOImpl;
import DAO.FuncionarioDAOImpl;
import SINGLETON.ConexionSingleton;
import modelo.PartSeguimiento;
import modelo.Seguimiento;
import modelo.Funcionario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PartSeguimientoServicio {

    private final PartSeguimientoDAOImpl dao;
    private final SeguimientoDAOImpl seguimientoDAO;
    private final FuncionarioDAOImpl funcionarioDAO;

    // Constructor
    public PartSeguimientoServicio() throws SQLException {
        this.dao = new PartSeguimientoDAOImpl();
        this.seguimientoDAO = new SeguimientoDAOImpl();

        Connection conn = ConexionSingleton.getInstance().getConexion();
        this.funcionarioDAO = new FuncionarioDAOImpl(conn);
    }

    // Agregar participante a un seguimiento
    public boolean agregarParticipante(int idParticipante, int idSeguimiento) throws SQLException {

        if (idParticipante <= 0) {
            throw new IllegalArgumentException("ID de participante inválido: debe ser mayor que 0.");
        }
        if (idSeguimiento <= 0) {
            throw new IllegalArgumentException("ID de seguimiento inválido: debe ser mayor que 0.");
        }

        Funcionario funcionario = funcionarioDAO.obtenerFuncionario(idParticipante);
        if (funcionario == null) {
            throw new IllegalArgumentException("El funcionario con ID " + idParticipante + " no existe.");
        }

        if (!funcionario.isActivo()) {
            throw new IllegalArgumentException("El funcionario con ID " + idParticipante + " no está activo.");
        }

        Seguimiento seguimiento = seguimientoDAO.buscarPorId(idSeguimiento);
        if (seguimiento == null) {
            throw new IllegalArgumentException("El seguimiento con ID " + idSeguimiento + " no existe.");
        }

        if (!seguimiento.isEstActivo()) {
            throw new IllegalArgumentException("El seguimiento con ID " + idSeguimiento + " no está activo.");
        }

        List<Integer> seguimientos = dao.listarSeguimientosPorParticipante(idParticipante);
        if (seguimientos.contains(idSeguimiento)) {
            throw new IllegalArgumentException("El participante " + idParticipante +
                    " ya está asignado al seguimiento " + idSeguimiento + ".");
        }

        PartSeguimiento ps = new PartSeguimiento(idParticipante, idSeguimiento);
        return dao.agregarParticipante(ps);
    }

    // Eliminar participante de un seguimiento
    public boolean eliminarParticipante(int idParticipante, int idSeguimiento) throws SQLException {

        if (idParticipante <= 0) {
            throw new IllegalArgumentException("ID de participante inválido: debe ser mayor que 0.");
        }
        if (idSeguimiento <= 0) {
            throw new IllegalArgumentException("ID de seguimiento inválido: debe ser mayor que 0.");
        }

        Funcionario funcionario = funcionarioDAO.obtenerFuncionario(idParticipante);
        if (funcionario == null) {
            throw new IllegalArgumentException("El funcionario con ID " + idParticipante + " no existe.");
        }

        Seguimiento seguimiento = seguimientoDAO.buscarPorId(idSeguimiento);
        if (seguimiento == null) {
            throw new IllegalArgumentException("El seguimiento con ID " + idSeguimiento + " no existe.");
        }

        List<Integer> seguimientos = dao.listarSeguimientosPorParticipante(idParticipante);
        if (!seguimientos.contains(idSeguimiento)) {
            throw new IllegalArgumentException("El participante " + idParticipante +
                    " no está asignado al seguimiento " + idSeguimiento + ".");
        }

        PartSeguimiento ps = new PartSeguimiento(idParticipante, idSeguimiento);
        return dao.eliminarParticipante(ps);
    }

    // Listar todas las relaciones participante-seguimiento
    public List<PartSeguimiento> listarTodos() throws SQLException {
        return dao.listarTodos();
    }

    // Listar seguimientos de un participante
    public List<Integer> listarSeguimientosPorParticipante(int idParticipante) throws SQLException {
        if (idParticipante <= 0) {
            throw new IllegalArgumentException("ID de participante inválido: debe ser mayor que 0.");
        }

        Funcionario funcionario = funcionarioDAO.obtenerFuncionario(idParticipante);
        if (funcionario == null) {
            throw new IllegalArgumentException("El funcionario con ID " + idParticipante + " no existe.");
        }

        return dao.listarSeguimientosPorParticipante(idParticipante);
    }

    // Listar participantes de un seguimiento
    public List<Integer> listarParticipantesPorSeguimiento(int idSeguimiento) throws SQLException {
        if (idSeguimiento <= 0) {
            throw new IllegalArgumentException("ID de seguimiento inválido: debe ser mayor que 0.");
        }

        Seguimiento seguimiento = seguimientoDAO.buscarPorId(idSeguimiento);
        if (seguimiento == null) {
            throw new IllegalArgumentException("El seguimiento con ID " + idSeguimiento + " no existe.");
        }

        return dao.listarParticipantesPorSeguimiento(idSeguimiento);
    }
}