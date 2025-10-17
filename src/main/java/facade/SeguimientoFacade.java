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
    // CREAR SEGUIMIENTO
    // ============================================================
    public boolean agregarSeguimiento(int idInforme, int idEstudiante, LocalDate fecInicio, LocalDate fecCierre, boolean estActivo) throws SQLException {
        return seguimientoService.agregarSeguimiento(idInforme, idEstudiante, fecInicio, fecCierre, estActivo);
    }

    // ============================================================
    // OBTENER SEGUIMIENTO
    // ============================================================
    public Seguimiento buscarPorId(int idSeguimiento) throws SQLException {
        return seguimientoService.buscarPorId(idSeguimiento);
    }

    // ============================================================
    // LISTAR SEGUIMIENTOS
    // ============================================================
    public List<Seguimiento> listarTodos() throws SQLException {
        return seguimientoService.listarTodos();
    }

    // ============================================================
    // ACTUALIZAR SEGUIMIENTO
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
}
