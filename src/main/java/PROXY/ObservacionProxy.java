package PROXY;

import modelo.Observacion;
import servicios.ObservacionServicio;
import utils.ValidarUsuario;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

public class ObservacionProxy {

    private final ObservacionServicio observacionServicio;
    private final ValidarUsuario validarUsuario;

    // Constructor: inicializa el servicio de observaciones
    public ObservacionProxy() throws SQLException {
        this.observacionServicio = new ObservacionServicio();
        this.validarUsuario = new ValidarUsuario();
    }

    // Crear observación (admin, psico o propietario)
    public Observacion crearObservacion(int idFuncionario, int idEstudiante, String titulo,
                                        String contenido, OffsetDateTime fecHora) throws SQLException {
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(idFuncionario)) {
            throw new SecurityException("No posees permiso para crear una observación.");
        }
        return observacionServicio.crearObservacion(idFuncionario, idEstudiante, titulo, contenido, fecHora);
    }

    // Obtener observación por ID (admin, psico o propietario)
    public Observacion obtenerObservacion(int id) throws SQLException {
        Observacion observacion = observacionServicio.obtenerObservacion(id);
        if (observacion == null) {
            throw new IllegalArgumentException("No se encontró observación para el ID especificado.");
        }
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(observacion.getIdFuncionario())) {
            throw new SecurityException("No tienes permiso para acceder a esta observación.");
        }
        return observacion;
    }

    // Listar todas las observaciones (admin o psico)
    public List<Observacion> listarTodas() throws SQLException {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("No tienes permiso para listar observaciones.");
        }
        return observacionServicio.listarTodas();
    }

    // Actualizar observación (admin, psico o propietario)
    public boolean actualizarObservacion(Observacion observacion) throws SQLException {
        Observacion existente = observacionServicio.obtenerObservacion(observacion.getIdObservacion());
        if (existente == null) {
            throw new IllegalArgumentException("No se encontró observación para actualizar.");
        }

        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(observacion.getIdFuncionario())) {
            throw new SecurityException("No tienes permiso para actualizar esta observación.");
        }

        return observacionServicio.actualizarObservacion(observacion);
    }

    // Desactivar observación (admin, psico o propietario)
    public boolean desactivarObservacion(int id) throws SQLException {
        Observacion observacion = observacionServicio.obtenerObservacion(id);
        if (observacion == null) {
            throw new IllegalArgumentException("No se encontró observación para desactivar.");
        }

        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(observacion.getIdFuncionario())) {
            throw new SecurityException("No tienes permiso para desactivar esta observación.");
        }

        return observacionServicio.desactivarObservacion(id);
    }
}
