package servicios;

import DAO.SeguimientoDAO;
import modelo.Seguimiento;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SeguimientoService {

    private final SeguimientoDAO dao;

    public SeguimientoService() throws SQLException {
        this.dao = new SeguimientoDAO();
    }

    // 🔹 Crear un nuevo seguimiento
    public boolean agregarSeguimiento(int idInforme, int idEstudiante, LocalDate fecInicio, LocalDate fecCierre, boolean estActivo) throws SQLException {
        if (idEstudiante <= 0) throw new IllegalArgumentException("ID de estudiante inválido.");
        if (fecInicio == null) throw new IllegalArgumentException("Fecha de inicio requerida.");
        Seguimiento s = new Seguimiento(idInforme, idEstudiante, fecInicio, fecCierre, estActivo);
        return dao.agregar(s);
    }

    // 🔹 Actualizar un seguimiento existente
    public boolean actualizarSeguimiento(int idSeguimiento, int idInforme, int idEstudiante, LocalDate fecInicio, LocalDate fecCierre, boolean estActivo) throws SQLException {
        if (idSeguimiento <= 0) throw new IllegalArgumentException("ID de seguimiento inválido.");
        if (idEstudiante <= 0) throw new IllegalArgumentException("ID de estudiante inválido.");
        if (fecInicio == null) throw new IllegalArgumentException("Fecha de inicio requerida.");
        Seguimiento s = new Seguimiento(idSeguimiento, idInforme, idEstudiante, fecInicio, fecCierre, estActivo);
        return dao.actualizar(s);
    }

    // 🔹 Eliminar seguimiento
    public boolean eliminarSeguimiento(int idSeguimiento) throws SQLException {
        if (idSeguimiento <= 0) throw new IllegalArgumentException("ID de seguimiento inválido.");
        return dao.eliminar(idSeguimiento);
    }

    // 🔹 Buscar seguimiento por ID
    public Seguimiento buscarPorId(int idSeguimiento) throws SQLException {
        if (idSeguimiento <= 0) throw new IllegalArgumentException("ID de seguimiento inválido.");
        return dao.buscarPorId(idSeguimiento);
    }

    // 🔹 Listar todos los seguimientos
    public List<Seguimiento> listarTodos() throws SQLException {
        return dao.listarTodos();
    }
}
