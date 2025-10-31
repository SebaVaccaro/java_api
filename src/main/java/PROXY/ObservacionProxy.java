package PROXY;

import modelo.Observacion;
import servicios.ObservacionServicio;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

public class ObservacionProxy {

    private final ObservacionServicio observacionServicio;

    // Constructor: inicializa el servicio de observaciones
    public ObservacionProxy() throws SQLException {
        this.observacionServicio = new ObservacionServicio();
    }

    // Crear observación (sin restricción de permisos definida)
    public Observacion crearObservacion(int idFuncionario, int idEstudiante, String titulo,
                                        String contenido, OffsetDateTime fecHora) throws SQLException {
        return observacionServicio.crearObservacion(idFuncionario, idEstudiante, titulo, contenido, fecHora);
    }

    // Obtener observación por ID (sin restricción de permisos)
    public Observacion obtenerObservacion(int id) throws SQLException {
        return observacionServicio.obtenerObservacion(id);
    }

    // Listar todas las observaciones (sin restricción de permisos)
    public List<Observacion> listarTodas() throws SQLException {
        return observacionServicio.listarTodas();
    }

    // Actualizar observación (sin restricción de permisos)
    public boolean actualizarObservacion(Observacion observacion) throws SQLException {
        return observacionServicio.actualizarObservacion(observacion);
    }

    // Desactivar observación (sin restricción de permisos)
    public boolean desactivarObservacion(int id) throws SQLException {
        return observacionServicio.desactivarObservacion(id);
    }
}
