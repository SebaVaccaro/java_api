package PROXY;

import modelo.Seguimiento;
import servicios.SeguimientoServicio;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SeguimientoProxy {

    private final SeguimientoServicio seguimientoServicio;

    // Constructor: inicializa el servicio de seguimientos
    public SeguimientoProxy() throws SQLException {
        this.seguimientoServicio = new SeguimientoServicio();
    }

    // Crear seguimiento con informe (sin restricción de permisos)
    public boolean agregarSeguimiento(Integer idInforme, int idEstudiante, LocalDate fecInicio, LocalDate fecCierre, boolean estActivo) throws SQLException {
        return seguimientoServicio.agregarSeguimiento(idInforme, idEstudiante, fecInicio, fecCierre, estActivo);
    }

    // Crear seguimiento sin informe (sin restricción de permisos)
    public boolean agregarSeguimiento(int idEstudiante, LocalDate fecInicio, boolean estActivo) throws SQLException {
        return seguimientoServicio.agregarSeguimiento(idEstudiante, fecInicio, estActivo);
    }

    // Obtener seguimiento por ID (sin restricción de permisos)
    public Seguimiento buscarPorId(int idSeguimiento) throws SQLException {
        return seguimientoServicio.buscarPorId(idSeguimiento);
    }

    // Listar todos los seguimientos (sin restricción de permisos)
    public List<Seguimiento> listarTodos() throws SQLException {
        return seguimientoServicio.listarTodos();
    }

    // Actualizar seguimiento con informe (sin restricción de permisos)
    public boolean actualizarSeguimiento(int idSeguimiento, Integer idInforme, int idEstudiante, LocalDate fecInicio, LocalDate fecCierre, boolean estActivo) throws SQLException {
        return seguimientoServicio.actualizarSeguimiento(idSeguimiento, idInforme, idEstudiante, fecInicio, fecCierre, estActivo);
    }

    // Actualizar seguimiento sin informe (sin restricción de permisos)
    public boolean actualizarSeguimiento(int idSeguimiento, int idEstudiante, LocalDate fecInicio, boolean estActivo) throws SQLException {
        return seguimientoServicio.actualizarSeguimiento(idSeguimiento, idEstudiante, fecInicio, estActivo);
    }

    // Eliminar seguimiento (sin restricción de permisos)
    public boolean eliminarSeguimiento(int idSeguimiento) throws SQLException {
        return seguimientoServicio.eliminarSeguimiento(idSeguimiento);
    }
}

