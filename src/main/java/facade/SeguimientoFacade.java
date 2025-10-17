package facade;

import modelo.Seguimiento;
import servicios.SeguimientoService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SeguimientoFacade {

    private final SeguimientoService seguimientoService;

    public SeguimientoFacade() throws SQLException {
        this.seguimientoService = new SeguimientoService();
    }

    // ============================================================
    // AGREGAR NUEVO SEGUIMIENTO
    // ============================================================
    public boolean agregarSeguimiento(int idInforme, int idEstudiante, LocalDate fecInicio, LocalDate fecCierre, boolean estActivo) throws SQLException {
        return seguimientoService.agregarSeguimiento(idInforme, idEstudiante, fecInicio, fecCierre, estActivo);
    }

    // ============================================================
    // ACTUALIZAR SEGUIMIENTO EXISTENTE
    // ============================================================
    public boolean actualizarSeguimiento(int idSeguimiento, int idInforme, int idEstudiante, LocalDate fecInicio, LocalDate fecCierre, boolean estActivo) throws SQLException {
        return seguimientoService.actualizarSeguimiento(idSeguimiento, idInforme, idEstudiante, fecInicio, fecCierre, estActivo);
    }

    // ============================================================
    // ELIMINAR SEGUIMIENTO
    // ============================================================
    public boolean eliminarSeguimiento(int idSeguimiento) throws SQLException {
        return seguimientoService.eliminarSeguimiento(idSeguimiento);
    }

    // ============================================================
    // BUSCAR SEGUIMIENTO POR ID
    // ============================================================
    public Seguimiento buscarPorId(int idSeguimiento) throws SQLException {
        return seguimientoService.buscarPorId(idSeguimiento);
    }

    // ============================================================
    // LISTAR TODOS LOS SEGUIMIENTOS
    // ============================================================
    public List<Seguimiento> listarTodos() throws SQLException {
        return seguimientoService.listarTodos();
    }
}
