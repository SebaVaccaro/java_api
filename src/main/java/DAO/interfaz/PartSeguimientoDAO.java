package DAO.interfaz;

import modelo.PartSeguimiento;
import java.sql.SQLException;
import java.util.List;

public interface PartSeguimientoDAO {

    // Agregar relación participante-seguimiento
    boolean agregarParticipante(PartSeguimiento ps) throws SQLException;

    // Eliminar relación participante-seguimiento
    boolean eliminarParticipante(PartSeguimiento ps) throws SQLException;

    // Listar todas las relaciones
    List<PartSeguimiento> listarTodos() throws SQLException;

    // Listar seguimientos de un participante
    List<Integer> listarSeguimientosPorParticipante(int idParticipante) throws SQLException;

    // Listar participantes de un seguimiento
    List<Integer> listarParticipantesPorSeguimiento(int idSeguimiento) throws SQLException;
}
