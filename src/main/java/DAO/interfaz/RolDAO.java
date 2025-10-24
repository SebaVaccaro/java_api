package DAO.interfaz;

import modelo.Rol;
import java.sql.SQLException;
import java.util.List;

public interface RolDAO {

    // Crear un nuevo rol
    boolean agregar(Rol rol) throws SQLException;

    // Actualizar un rol existente
    boolean actualizar(Rol rol) throws SQLException;

    // Eliminar un rol (físico o lógico según implementación)
    boolean eliminar(int idRol) throws SQLException;

    // Buscar rol por ID
    Rol buscarPorId(int idRol) throws SQLException;

    // Listar todos los roles
    List<Rol> listarTodos() throws SQLException;
}
