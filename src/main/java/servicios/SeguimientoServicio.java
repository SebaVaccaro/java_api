package servicios;

import DAO.SeguimientoDAOImpl;
import modelo.Seguimiento;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SeguimientoServicio {

    private final SeguimientoDAOImpl dao;
    private final EstudianteServicio estudianteServicio;

    // Constructor: inicializa DAO de seguimiento y servicio de estudiante
    public SeguimientoServicio() throws SQLException {
        this.dao = new SeguimientoDAOImpl();
        this.estudianteServicio = new EstudianteServicio();
    }

    // Agregar seguimiento
    public boolean agregarSeguimiento(Integer idInforme, int idEstudiante, LocalDate fecInicio, LocalDate fecCierre, boolean estActivo) throws SQLException {
        validarCampos(idEstudiante, fecInicio);
        Seguimiento s = new Seguimiento(idInforme, idEstudiante, fecInicio, fecCierre, estActivo);
        return dao.agregar(s);
    }

    // Sobrecarga sin idInforme ni fecCierre
    public boolean agregarSeguimiento(int idEstudiante, LocalDate fecInicio, boolean estActivo) throws SQLException {
        validarCampos(idEstudiante, fecInicio);
        Seguimiento s = new Seguimiento(null, idEstudiante, fecInicio, null, estActivo);
        return dao.agregar(s);
    }

    // Actualizar seguimiento
    public boolean actualizarSeguimiento(int idSeguimiento, Integer idInforme, int idEstudiante, LocalDate fecInicio, LocalDate fecCierre, boolean estActivo) throws SQLException {
        validarCamposActualizacion(idSeguimiento, idEstudiante, fecInicio);
        Seguimiento s = new Seguimiento(idSeguimiento, idInforme, idEstudiante, fecInicio, fecCierre, estActivo);
        return dao.actualizar(s);
    }

    // Sobrecarga sin idInforme ni fecCierre
    public boolean actualizarSeguimiento(int idSeguimiento, int idEstudiante, LocalDate fecInicio, boolean estActivo) throws SQLException {
        validarCamposActualizacion(idSeguimiento, idEstudiante, fecInicio);
        Seguimiento s = new Seguimiento(idSeguimiento, null, idEstudiante, fecInicio, null, estActivo);
        return dao.actualizar(s);
    }

    // Eliminar seguimiento
    public boolean eliminarSeguimiento(int idSeguimiento) throws SQLException {
        if (idSeguimiento <= 0)
            throw new IllegalArgumentException("ID de seguimiento inválido.");
        return dao.eliminar(idSeguimiento);
    }

    // Buscar seguimiento por ID
    public Seguimiento buscarPorId(int idSeguimiento) throws SQLException {
        if (idSeguimiento <= 0)
            throw new IllegalArgumentException("ID de seguimiento inválido.");
        return dao.buscarPorId(idSeguimiento);
    }

    // Listar todos los seguimientos
    public List<Seguimiento> listarTodos() throws SQLException {
        return dao.listarTodos();
    }

    // Métodos privados de validación
    private void validarCampos(int idEstudiante, LocalDate fecInicio) throws SQLException {
        if (idEstudiante <= 0)
            throw new IllegalArgumentException("ID de estudiante inválido.");
        if (fecInicio == null)
            throw new IllegalArgumentException("Fecha de inicio requerida.");

        validarEstudianteExiste(idEstudiante);
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
        if (estudianteServicio.obtenerPorId(idEstudiante) == null) {
            throw new IllegalArgumentException("El estudiante no existe.");
        }
    }
}
