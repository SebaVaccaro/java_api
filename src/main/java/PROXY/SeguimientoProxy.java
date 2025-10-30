package PROXY;

import modelo.Seguimiento;
import servicios.SeguimientoServicio;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SeguimientoProxy {

    private final SeguimientoServicio seguimientoServicio;

    public SeguimientoProxy() throws SQLException {
        this.seguimientoServicio = new SeguimientoServicio();
    }

    // ============================================================
    // 🔹 CREAR SEGUIMIENTO
    // ============================================================
    public boolean agregarSeguimiento(Integer idInforme, int idEstudiante, LocalDate fecInicio, LocalDate fecCierre, boolean estActivo) throws SQLException {
        return seguimientoServicio.agregarSeguimiento(idInforme, idEstudiante, fecInicio, fecCierre, estActivo);
    }

    public boolean agregarSeguimiento(int idEstudiante, LocalDate fecInicio, boolean estActivo) throws SQLException {
        return seguimientoServicio.agregarSeguimiento(idEstudiante, fecInicio, estActivo);
    }

    // ============================================================
    // 🔹 OBTENER SEGUIMIENTO
    // ============================================================
    public Seguimiento buscarPorId(int idSeguimiento) throws SQLException {
        return seguimientoServicio.buscarPorId(idSeguimiento);
    }

    // ============================================================
    // 🔹 LISTAR SEGUIMIENTOS
    // ============================================================
    public List<Seguimiento> listarTodos() throws SQLException {
        return seguimientoServicio.listarTodos();
    }

    // ============================================================
    // 🔹 ACTUALIZAR SEGUIMIENTO
    // ============================================================
    public boolean actualizarSeguimiento(int idSeguimiento, Integer idInforme, int idEstudiante, LocalDate fecInicio, LocalDate fecCierre, boolean estActivo) throws SQLException {
        return seguimientoServicio.actualizarSeguimiento(idSeguimiento, idInforme, idEstudiante, fecInicio, fecCierre, estActivo);
    }

    public boolean actualizarSeguimiento(int idSeguimiento, int idEstudiante, LocalDate fecInicio, boolean estActivo) throws SQLException {
        return seguimientoServicio.actualizarSeguimiento(idSeguimiento, idEstudiante, fecInicio, estActivo);
    }

    // ============================================================
    // 🔹 ELIMINAR SEGUIMIENTO
    // ============================================================
    public boolean eliminarSeguimiento(int idSeguimiento) throws SQLException {
        return seguimientoServicio.eliminarSeguimiento(idSeguimiento);
    }
}
