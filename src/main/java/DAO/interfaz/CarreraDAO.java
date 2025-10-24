package DAO.interfaz;

import modelo.Carrera;
import java.sql.SQLException;
import java.util.List;

public interface CarreraDAO {

    // Crear nueva carrera
    Carrera crearCarrera(Carrera carrera) throws SQLException;

    // Obtener carrera por ID
    Carrera obtenerCarrera(int idCarrera) throws SQLException;

    // Listar todas las carreras
    List<Carrera> listarCarreras() throws SQLException;

    // Buscar carrera por código
    Carrera obtenerPorCodigo(String codigo) throws SQLException;

    // Actualizar carrera
    boolean actualizarCarrera(Carrera carrera) throws SQLException;

    // Eliminar carrera (borrado físico)
    boolean eliminarCarrera(int idCarrera) throws SQLException;
}
