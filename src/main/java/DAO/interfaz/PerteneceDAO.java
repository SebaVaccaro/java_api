package DAO.interfaz;

import modelo.Pertenece;
import java.sql.SQLException;
import java.util.List;

public interface PerteneceDAO {

    // Agregar relación Carrera <-> ITR
    boolean agregar(Pertenece p) throws SQLException;

    // Eliminar relación Carrera <-> ITR
    boolean eliminar(Pertenece p) throws SQLException;

    // Listar todas las relaciones
    List<Pertenece> listarTodos() throws SQLException;

    // Listar ITRs de una carrera
    List<Integer> listarItrPorCarrera(int idCarrera) throws SQLException;

    // Listar carreras de un ITR
    List<Integer> listarCarrerasPorItr(int idItr) throws SQLException;
}
