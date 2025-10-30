package PROXY;

import modelo.Observacion;
import servicios.ObservacionServicio;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

public class ObservacionProxy {

    private final ObservacionServicio observacionServicio;

    public ObservacionProxy() throws SQLException {
        this.observacionServicio = new ObservacionServicio();
    }

    // ============================================================
    // CREAR OBSERVACIÓN
    // ============================================================
    public Observacion crearObservacion(int idFuncionario, int idEstudiante, String titulo,
                                        String contenido, OffsetDateTime fecHora) throws SQLException {
        return observacionServicio.crearObservacion(idFuncionario, idEstudiante, titulo, contenido, fecHora);
    }

    // ============================================================
    // OBTENER OBSERVACIÓN
    // ============================================================
    public Observacion obtenerObservacion(int id) throws SQLException {
        return observacionServicio.obtenerObservacion(id);
    }

    // ============================================================
    // LISTAR OBSERVACIONES
    // ============================================================
    public List<Observacion> listarTodas() throws SQLException {
        return observacionServicio.listarTodas();
    }

    // ============================================================
    // ACTUALIZAR OBSERVACIÓN
    // ============================================================
    public boolean actualizarObservacion(Observacion observacion) throws SQLException {
        return observacionServicio.actualizarObservacion(observacion);
    }

    // ============================================================
    // BAJA LÓGICA (DESACTIVAR OBSERVACIÓN)
    // ============================================================
    public boolean desactivarObservacion(int id) throws SQLException {
        return observacionServicio.desactivarObservacion(id);
    }
}
