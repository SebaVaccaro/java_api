package servicios;

import DAO.ObservacionDAOImpl;
import modelo.Observacion;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

public class ObservacionServicio {

    private final ObservacionDAOImpl observacionDAOImpl;

    // Constructor: inicializa el DAO de observaciones
    public ObservacionServicio() throws SQLException {
        this.observacionDAOImpl = new ObservacionDAOImpl();
    }

    // Crear observación con validaciones
    public Observacion crearObservacion(int idFuncionario, int idEstudiante, String titulo, String contenido, OffsetDateTime fecHora) throws SQLException {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }
        if (contenido == null || contenido.isBlank()) {
            throw new IllegalArgumentException("El contenido no puede estar vacío.");
        }
        if (fecHora == null) {
            fecHora = OffsetDateTime.now();
        }

        Observacion o = new Observacion(idFuncionario, idEstudiante, titulo, contenido, fecHora, true);
        return observacionDAOImpl.crearObservacion(o);
    }

    // Sobrecarga de constructor para poder setearle el dao en los tests
    public ObservacionServicio(ObservacionDAOImpl dao) {
        this.observacionDAOImpl = dao;
    }

    // Obtener observación por ID
    public Observacion obtenerObservacion(int id) throws SQLException {
        Observacion observacion = observacionDAOImpl.obtenerObservacion(id);
        if (observacion == null) {
            throw new IllegalArgumentException("No se encontró observación para el ID especificado.");
        }
        return observacion;
    }

    // Listar todas las observaciones
    public List<Observacion> listarTodas() throws SQLException {
        return observacionDAOImpl.listarTodas();
    }

    // Actualizar observación existente
    public boolean actualizarObservacion(Observacion o) throws SQLException {
        Observacion existente = observacionDAOImpl.obtenerObservacion(o.getIdObservacion());
        if (existente == null) {
            throw new IllegalArgumentException("No se encontró observación para actualizar.");
        }
        return observacionDAOImpl.actualizarObservacion(o);
    }

    // Desactivar (baja lógica) observación
    public boolean desactivarObservacion(int id) throws SQLException {
        Observacion existente = observacionDAOImpl.obtenerObservacion(id);
        if (existente == null) {
            throw new IllegalArgumentException("No se encontró observación para desactivar.");
        }
        return observacionDAOImpl.eliminarObservacion(id);
    }
}
