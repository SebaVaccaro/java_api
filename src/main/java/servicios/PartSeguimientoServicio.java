package servicios;

import DAO.PartSeguimientoDAOImpl;
import modelo.PartSeguimiento;

import java.sql.SQLException;
import java.util.List;

public class PartSeguimientoServicio {

    private final PartSeguimientoDAOImpl dao;

    public PartSeguimientoServicio() throws SQLException {
        this.dao = new PartSeguimientoDAOImpl();
    }

    // 🔹 Agregar participante a un seguimiento
    public boolean agregarParticipante(int idParticipante, int idSeguimiento) throws SQLException {
        if (idParticipante <= 0 || idSeguimiento <= 0) {
            throw new IllegalArgumentException("ID de participante o seguimiento inválido.");
        }
        PartSeguimiento ps = new PartSeguimiento(idParticipante, idSeguimiento);
        return dao.agregarParticipante(ps);
    }

    // 🔹 Eliminar participante de un seguimiento
    public boolean eliminarParticipante(int idParticipante, int idSeguimiento) throws SQLException {
        if (idParticipante <= 0 || idSeguimiento <= 0) {
            throw new IllegalArgumentException("ID de participante o seguimiento inválido.");
        }
        PartSeguimiento ps = new PartSeguimiento(idParticipante, idSeguimiento);
        return dao.eliminarParticipante(ps);
    }

    // 🔹 Listar todas las relaciones participante-seguimiento
    public List<PartSeguimiento> listarTodos() throws SQLException {
        return dao.listarTodos();
    }

    // 🔹 Listar seguimientos de un participante
    public List<Integer> listarSeguimientosPorParticipante(int idParticipante) throws SQLException {
        if (idParticipante <= 0) {
            throw new IllegalArgumentException("ID de participante inválido.");
        }
        return dao.listarSeguimientosPorParticipante(idParticipante);
    }

    // 🔹 Listar participantes de un seguimiento
    public List<Integer> listarParticipantesPorSeguimiento(int idSeguimiento) throws SQLException {
        if (idSeguimiento <= 0) {
            throw new IllegalArgumentException("ID de seguimiento inválido.");
        }
        return dao.listarParticipantesPorSeguimiento(idSeguimiento);
    }
}
