package servicios;

import DAO.SeguimientoDAOImpl;
import modelo.Seguimiento;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SeguimientoService {

    private final SeguimientoDAOImpl dao;
    private final EstudianteService estudianteService;

    public SeguimientoService() throws SQLException {
        this.dao = new SeguimientoDAOImpl();
        this.estudianteService = new EstudianteService();
    }

    // ==========================================================
    // 🔹 AGREGAR SEGUIMIENTO
    // ==========================================================
    public boolean agregarSeguimiento(Integer idInforme, int idEstudiante, LocalDate fecInicio, LocalDate fecCierre, boolean estActivo) throws SQLException {
        validarCampos(idEstudiante, fecInicio);
        Seguimiento s = new Seguimiento(idInforme, idEstudiante, fecInicio, fecCierre, estActivo);
        return dao.agregar(s);
    }

    // ➤ Sobrecarga sin idInforme ni fecCierre
    public boolean agregarSeguimiento(int idEstudiante, LocalDate fecInicio, boolean estActivo) throws SQLException {
        validarCampos(idEstudiante, fecInicio);
        Seguimiento s = new Seguimiento(null, idEstudiante, fecInicio, null, estActivo);
        return dao.agregar(s);
    }

    // ==========================================================
    // 🔹 ACTUALIZAR SEGUIMIENTO
    // ==========================================================
    public boolean actualizarSeguimiento(int idSeguimiento, Integer idInforme, int idEstudiante, LocalDate fecInicio, LocalDate fecCierre, boolean estActivo) throws SQLException {
        validarCamposActualizacion(idSeguimiento, idEstudiante, fecInicio);
        Seguimiento s = new Seguimiento(idSeguimiento, idInforme, idEstudiante, fecInicio, fecCierre, estActivo);
        return dao.actualizar(s);
    }

    // ➤ Sobrecarga sin idInforme ni fecCierre
    public boolean actualizarSeguimiento(int idSeguimiento, int idEstudiante, LocalDate fecInicio, boolean estActivo) throws SQLException {
        validarCamposActualizacion(idSeguimiento, idEstudiante, fecInicio);
        Seguimiento s = new Seguimiento(idSeguimiento, null, idEstudiante, fecInicio, null, estActivo);
        return dao.actualizar(s);
    }

    // ==========================================================
    // 🔹 ELIMINAR / BUSCAR / LISTAR
    // ==========================================================
    public boolean eliminarSeguimiento(int idSeguimiento) throws SQLException {
        if (idSeguimiento <= 0)
            throw new IllegalArgumentException("ID de seguimiento inválido.");
        return dao.eliminar(idSeguimiento);
    }

    public Seguimiento buscarPorId(int idSeguimiento) throws SQLException {
        if (idSeguimiento <= 0)
            throw new IllegalArgumentException("ID de seguimiento inválido.");
        return dao.buscarPorId(idSeguimiento);
    }

    public List<Seguimiento> listarTodos() throws SQLException {
        return dao.listarTodos();
    }

    // ==========================================================
    // 🔹 MÉTODOS PRIVADOS DE VALIDACIÓN
    // ==========================================================
    private void validarCampos(int idEstudiante, LocalDate fecInicio) throws SQLException {
        if (idEstudiante <= 0)
            throw new IllegalArgumentException("ID de estudiante inválido.");
        if (fecInicio == null)
            throw new IllegalArgumentException("Fecha de inicio requerida.");

        // Verificar que el estudiante exista
        validarEstudianteExiste(idEstudiante);

        // Verificar que no tenga seguimiento activo
        validarNoTieneSeguimientoActivo(idEstudiante);
    }

    private void validarCamposActualizacion(int idSeguimiento, int idEstudiante, LocalDate fecInicio) throws SQLException {
        if (idSeguimiento <= 0)
            throw new IllegalArgumentException("ID de seguimiento inválido.");
        validarCampos(idEstudiante, fecInicio);
    }

    private void validarNoTieneSeguimientoActivo(int idEstudiante) throws SQLException {
        if (dao.tieneSeguimientoActivo(idEstudiante)) {
            throw new IllegalArgumentException("El estudiante ya tiene un seguimiento activo.");
        }
    }

    private void validarEstudianteExiste(int idEstudiante) throws SQLException {
        if (estudianteService.obtenerPorId(idEstudiante) == null) {
            throw new IllegalArgumentException("El estudiante no existe.");
        }
    }
}
