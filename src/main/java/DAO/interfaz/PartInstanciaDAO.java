package DAO.interfaz;

import modelo.PartInstancia;
import java.sql.SQLException;
import java.util.List;

public interface PartInstanciaDAO {

    // Agregar relación participante-instancia
    boolean agregarParticipante(PartInstancia pi) throws SQLException;

    // Eliminar relación participante-instancia
    boolean eliminarParticipante(PartInstancia pi) throws SQLException;

    // Listar todas las relaciones
    List<PartInstancia> listarTodos() throws SQLException;

    // Listar instancias de un participante
    List<Integer> listarInstanciasPorParticipante(int idParticipante) throws SQLException;

    // Listar participantes de una instancia
    List<Integer> listarParticipantesPorInstancia(int idInstancia) throws SQLException;
}
