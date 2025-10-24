package DAO.interfaz;

import modelo.Observacion;
import java.sql.SQLException;
import java.util.List;

public interface ObservacionDAO {

    // Crear observación
    Observacion crearObservacion(Observacion o) throws SQLException;

    // Obtener observación por ID
    Observacion obtenerObservacion(int id) throws SQLException;

    // Listar todas las observaciones
    List<Observacion> listarTodas() throws SQLException;

    // Actualizar observación
    boolean actualizarObservacion(Observacion o) throws SQLException;

    // Baja lógica (desactivar observación)
    boolean eliminarObservacion(int id) throws SQLException;
}
