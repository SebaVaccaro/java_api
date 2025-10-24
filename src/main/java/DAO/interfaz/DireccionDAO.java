package DAO.interfaz;

import modelo.Direccion;
import java.sql.SQLException;
import java.util.List;

public interface DireccionDAO {

    // Crear nueva dirección
    Direccion crearDireccion(Direccion direccion) throws SQLException;

    // Obtener dirección por ID
    Direccion obtenerDireccion(int idDireccion) throws SQLException;

    // Listar todas las direcciones
    List<Direccion> listarDirecciones() throws SQLException;

    // Listar direcciones por usuario
    List<Direccion> listarPorUsuario(int idUsuario) throws SQLException;

    // Listar direcciones por ciudad
    List<Direccion> listarPorCiudad(int idCiudad) throws SQLException;

    // Actualizar dirección
    boolean actualizarDireccion(Direccion direccion) throws SQLException;

    // Eliminar dirección
    boolean eliminarDireccion(int idDireccion) throws SQLException;
}
