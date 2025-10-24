package DAO.interfaz;

import modelo.Ciudad;
import java.sql.SQLException;
import java.util.List;

public interface CiudadDAO {

    // Crear nueva ciudad
    Ciudad crearCiudad(Ciudad ciudad) throws SQLException;

    // Obtener ciudad por ID
    Ciudad obtenerCiudad(int idCiudad) throws SQLException;

    // Listar todas las ciudades
    List<Ciudad> listarCiudades() throws SQLException;

    // Buscar ciudad por nombre
    Ciudad obtenerPorNombre(String nombre) throws SQLException;

    // Listar ciudades por departamento
    List<Ciudad> listarPorDepartamento(String departamento) throws SQLException;

    // Actualizar ciudad
    boolean actualizarCiudad(Ciudad ciudad) throws SQLException;

    // Eliminar ciudad (borrado físico)
    boolean eliminarCiudad(int idCiudad) throws SQLException;
}
