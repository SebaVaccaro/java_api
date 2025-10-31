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

    // Obtener observación por ID
    public Observacion obtenerObservacion(int id) throws SQLException {
        return observacionDAOImpl.obtenerObservacion(id);
    }

    // Listar todas las observaciones
    public List<Observacion> listarTodas() throws SQLException {
        return observacionDAOImpl.listarTodas();
    }

    // Actualizar observación existente
    public boolean actualizarObservacion(Observacion o) throws SQLException {
        if (o.getIdObservacion() <= 0) {
            throw new IllegalArgumentException("ID de observación inválido.");
        }
        return observacionDAOImpl.actualizarObservacion(o);
    }

    // Desactivar (baja lógica) observación
    public boolean desactivarObservacion(int id) throws SQLException {
        return observacionDAOImpl.eliminarObservacion(id);
    }
}

