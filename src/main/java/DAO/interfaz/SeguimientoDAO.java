package DAO.interfaz;

import modelo.Seguimiento;
import java.sql.SQLException;
import java.util.List;

public interface SeguimientoDAO {

    // Agregar un nuevo seguimiento
    boolean agregar(Seguimiento s) throws SQLException;

    // Actualizar un seguimiento existente
    boolean actualizar(Seguimiento s) throws SQLException;

    // Eliminar un seguimiento
    boolean eliminar(int idSeguimiento) throws SQLException;

    // Buscar seguimiento por ID
    Seguimiento buscarPorId(int idSeguimiento) throws SQLException;

    // Listar todos los seguimientos
    List<Seguimiento> listarTodos() throws SQLException;

    // Verificar si un estudiante tiene un seguimiento activo
    boolean tieneSeguimientoActivo(int idEstudiante) throws SQLException;
}
