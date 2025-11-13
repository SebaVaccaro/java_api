package servicios;

import DAO.SeguimientoDAOImpl;
import DAO.EstudianteDAOImpl;
import DAO.InformeFinalDAOImpl;
import modelo.Seguimiento;
import modelo.Estudiante;
import modelo.InformeFinal;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SeguimientoServicio {

    private final SeguimientoDAOImpl dao;
    private final EstudianteDAOImpl estudianteDAO;
    private final InformeFinalDAOImpl informeDAO;

    // Constructor: inicializa los DAOs necesarios
    public SeguimientoServicio() throws SQLException {
        this.dao = new SeguimientoDAOImpl();
        this.estudianteDAO = new EstudianteDAOImpl();
        this.informeDAO = new InformeFinalDAOImpl();
    }

    // Agregar seguimiento
    public boolean agregarSeguimiento(Integer idInforme, int idEstudiante, LocalDate fecInicio, LocalDate fecCierre, boolean estActivo) throws SQLException {
        validarCamposAgregar(idInforme, idEstudiante, fecInicio, fecCierre, estActivo);

        Seguimiento s = new Seguimiento(idInforme, idEstudiante, fecInicio, fecCierre, estActivo);
        return dao.agregar(s);
    }

    // Actualizar seguimiento
    public boolean actualizarSeguimiento(int idSeguimiento, Integer idInforme, int idEstudiante, LocalDate fecInicio, LocalDate fecCierre, boolean estActivo) throws SQLException {
        validarCamposActualizacion(idSeguimiento, idInforme, idEstudiante, fecInicio, fecCierre, estActivo);

        Seguimiento s = new Seguimiento(idSeguimiento, idInforme, idEstudiante, fecInicio, fecCierre, estActivo);
        return dao.actualizar(s);
    }

    // Eliminar seguimiento
    public boolean eliminarSeguimiento(int idSeguimiento) throws SQLException {
        if (idSeguimiento <= 0) {
            throw new IllegalArgumentException("ID de seguimiento inválido: debe ser mayor que 0.");
        }

        Seguimiento seguimiento = dao.buscarPorId(idSeguimiento);
        if (seguimiento == null) {
            throw new IllegalArgumentException("El seguimiento con ID " + idSeguimiento + " no existe.");
        }

        return dao.eliminar(idSeguimiento);
    }

    // Buscar seguimiento por ID
    public Seguimiento buscarPorId(int idSeguimiento) throws SQLException {
        if (idSeguimiento <= 0) {
            throw new IllegalArgumentException("ID de seguimiento inválido: debe ser mayor que 0.");
        }

        Seguimiento seguimiento = dao.buscarPorId(idSeguimiento);
        if (seguimiento == null) {
            throw new IllegalArgumentException("El seguimiento con ID " + idSeguimiento + " no existe.");
        }

        return seguimiento;
    }

    // Listar todos los seguimientos
    public List<Seguimiento> listarTodos() throws SQLException {
        return dao.listarTodos();
    }

    // Listar seguimientos por estudiante
    public List<Seguimiento> listarPorEstudiante(int idEstudiante) throws SQLException {
        if (idEstudiante <= 0) {
            throw new IllegalArgumentException("ID de estudiante inválido: debe ser mayor que 0.");
        }

        Estudiante estudiante = estudianteDAO.obtenerEstudiante(idEstudiante);
        if (estudiante == null) {
            throw new IllegalArgumentException("El estudiante con ID " + idEstudiante + " no existe.");
        }

        return dao.listarPorEstudiante(idEstudiante);
    }



    // Validación para agregar seguimiento
    private void validarCamposAgregar(Integer idInforme, int idEstudiante, LocalDate fecInicio, LocalDate fecCierre, boolean estActivo) throws SQLException {

        if (idEstudiante <= 0) {
            throw new IllegalArgumentException("ID de estudiante inválido: debe ser mayor que 0.");
        }

        if (fecInicio == null) {
            throw new IllegalArgumentException("La fecha de inicio es requerida.");
        }

        Estudiante estudiante = estudianteDAO.obtenerEstudiante(idEstudiante);
        if (estudiante == null) {
            throw new IllegalArgumentException("El estudiante con ID " + idEstudiante + " no existe.");
        }

        if (!estudiante.isActivo()) {
            throw new IllegalArgumentException("El estudiante con ID " + idEstudiante + " no está activo.");
        }

        if (idInforme != null && idInforme > 0) {
            InformeFinal informe = informeDAO.obtenerInformeFinal(idInforme);
            if (informe == null) {
                throw new IllegalArgumentException("El informe final con ID " + idInforme + " no existe.");
            }
        }

        if (fecCierre != null && fecCierre.isBefore(fecInicio)) {
            throw new IllegalArgumentException("La fecha de cierre no puede ser anterior a la fecha de inicio.");
        }

        if (estActivo && dao.tieneSeguimientoActivo(idEstudiante)) {
            throw new IllegalArgumentException("El estudiante con ID " + idEstudiante + " ya tiene un seguimiento activo.");
        }
    }

    // Validación para actualizar seguimiento
    private void validarCamposActualizacion(int idSeguimiento, Integer idInforme, int idEstudiante, LocalDate fecInicio, LocalDate fecCierre, boolean estActivo) throws SQLException {

        if (idSeguimiento <= 0) {
            throw new IllegalArgumentException("ID de seguimiento inválido: debe ser mayor que 0.");
        }

        Seguimiento seguimientoExistente = dao.buscarPorId(idSeguimiento);
        if (seguimientoExistente == null) {
            throw new IllegalArgumentException("El seguimiento con ID " + idSeguimiento + " no existe.");
        }

        if (idEstudiante <= 0) {
            throw new IllegalArgumentException("ID de estudiante inválido: debe ser mayor que 0.");
        }

        if (fecInicio == null) {
            throw new IllegalArgumentException("La fecha de inicio es requerida.");
        }

        Estudiante estudiante = estudianteDAO.obtenerEstudiante(idEstudiante);
        if (estudiante == null) {
            throw new IllegalArgumentException("El estudiante con ID " + idEstudiante + " no existe.");
        }

        if (!estudiante.isActivo()) {
            throw new IllegalArgumentException("El estudiante con ID " + idEstudiante + " no está activo.");
        }

        if (idInforme != null && idInforme > 0) {
            InformeFinal informe = informeDAO.obtenerInformeFinal(idInforme);
            if (informe == null) {
                throw new IllegalArgumentException("El informe final con ID " + idInforme + " no existe.");
            }
        }

        if (fecCierre != null && fecCierre.isBefore(fecInicio)) {
            throw new IllegalArgumentException("La fecha de cierre no puede ser anterior a la fecha de inicio.");
        }

        if (estActivo && idEstudiante != seguimientoExistente.getIdEstudiante()) {
            if (dao.tieneSeguimientoActivo(idEstudiante)) {
                throw new IllegalArgumentException("El estudiante con ID " + idEstudiante + " ya tiene un seguimiento activo.");
            }
        }

        if (estActivo && !seguimientoExistente.isEstActivo()) {
            if (dao.tieneSeguimientoActivo(idEstudiante)) {
                throw new IllegalArgumentException("El estudiante con ID " + idEstudiante + " ya tiene un seguimiento activo.");
            }
        }
    }
}