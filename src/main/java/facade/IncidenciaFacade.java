package facade;

import modelo.Incidencia;
import servicios.IncidenciaService;

import java.sql.SQLException;
import java.util.List;

public class IncidenciaFacade {

    private final IncidenciaService incidenciaService;

    public IncidenciaFacade() throws SQLException {
        this.incidenciaService = new IncidenciaService();
    }

    // ============================================================
    // CREAR INCIDENCIA
    // ============================================================
    public Incidencia crearIncidencia(Incidencia incidencia) throws SQLException {
        return incidenciaService.crearIncidencia(incidencia);
    }

    // ============================================================
    // OBTENER INCIDENCIA
    // ============================================================
    public Incidencia obtenerIncidencia(int idInstancia) throws SQLException {
        return incidenciaService.obtenerIncidencia(idInstancia);
    }

    // ============================================================
    // LISTAR INCIDENCIAS
    // ============================================================
    public List<Incidencia> listarIncidencias() throws SQLException {
        return incidenciaService.listarIncidencias();
    }

    public List<Incidencia> listarPorFuncionario(int idFuncionario) throws SQLException {
        return incidenciaService.listarPorFuncionario(idFuncionario);
    }

    // ============================================================
    // ACTUALIZAR INCIDENCIA
    // ============================================================
    public boolean actualizarIncidencia(Incidencia incidencia) throws SQLException {
        return incidenciaService.actualizarIncidencia(incidencia);
    }

    // ============================================================
    // ELIMINAR INCIDENCIA
    // ============================================================
    public boolean eliminarIncidencia(int idInstancia) throws SQLException {
        return incidenciaService.eliminarIncidencia(idInstancia);
    }
}
