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
    // 🔹 CREAR SEGUIMIENTO
    // ============================================================
    public boolean agregarSeguimiento(Integer idInforme, int idEstudiante, LocalDate fecInicio, LocalDate fecCierre, boolean estActivo) throws SQLException {
        return seguimientoService.agregarSeguimiento(idInforme, idEstudiante, fecInicio, fecCierre, estActivo);
    }

    public boolean agregarSeguimiento(int idEstudiante, LocalDate fecInicio, boolean estActivo) throws SQLException {
        return seguimientoService.agregarSeguimiento(idEstudiante, fecInicio, estActivo);
    }

    // ============================================================
    // 🔹 OBTENER SEGUIMIENTO
    // ============================================================
    public Seguimiento buscarPorId(int idSeguimiento) throws SQLException {
        return seguimientoService.buscarPorId(idSeguimiento);
    }

    // ============================================================
    // 🔹 LISTAR SEGUIMIENTOS
    // ============================================================
    public List<Seguimiento> listarTodos() throws SQLException {
        return seguimientoService.listarTodos();
    }

    // ============================================================
    // 🔹 ACTUALIZAR SEGUIMIENTO
    // ============================================================
    public boolean actualizarSeguimiento(int idSeguimiento, Integer idInforme, int idEstudiante, LocalDate fecInicio, LocalDate fecCierre, boolean estActivo) throws SQLException {
        return seguimientoService.actualizarSeguimiento(idSeguimiento, idInforme, idEstudiante, fecInicio, fecCierre, estActivo);
    }

    public boolean actualizarSeguimiento(int idSeguimiento, int idEstudiante, LocalDate fecInicio, boolean estActivo) throws SQLException {
        return seguimientoService.actualizarSeguimiento(idSeguimiento, idEstudiante, fecInicio, estActivo);
    }

    // ============================================================
    // 🔹 ELIMINAR SEGUIMIENTO
    // ============================================================
    public boolean eliminarSeguimiento(int idSeguimiento) throws SQLException {
        return seguimientoService.eliminarSeguimiento(idSeguimiento);
    }
}
