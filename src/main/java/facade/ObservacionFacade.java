package facade;

import modelo.Observacion;
import servicios.ObservacionService;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

public class ObservacionFacade {

    private final ObservacionService observacionService;

    public ObservacionFacade() throws SQLException {
        this.observacionService = new ObservacionService();
    }

    // ============================================================
    // CREAR OBSERVACIÓN
    // ============================================================
    public Observacion crearObservacion(int idFuncionario, int idEstudiante, String titulo,
                                        String contenido, OffsetDateTime fecHora) throws SQLException {
        return observacionService.crearObservacion(idFuncionario, idEstudiante, titulo, contenido, fecHora);
    }

    // ============================================================
    // OBTENER OBSERVACIÓN
    // ============================================================
    public Observacion obtenerObservacion(int id) throws SQLException {
        return observacionService.obtenerObservacion(id);
    }

    // ============================================================
    // LISTAR OBSERVACIONES
    // ============================================================
    public List<Observacion> listarTodas() throws SQLException {
        return observacionService.listarTodas();
    }

    // ============================================================
    // ACTUALIZAR OBSERVACIÓN
    // ============================================================
    public boolean actualizarObservacion(Observacion observacion) throws SQLException {
        return observacionService.actualizarObservacion(observacion);
    }

    // ============================================================
    // BAJA LÓGICA (DESACTIVAR OBSERVACIÓN)
    // ============================================================
    public boolean desactivarObservacion(int id) throws SQLException {
        return observacionService.desactivarObservacion(id);
    }
}
