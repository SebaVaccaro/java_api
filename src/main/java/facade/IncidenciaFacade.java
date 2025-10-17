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
    public Incidencia crearIncidencia(int idInstancia, int idFuncionario, String lugar) throws SQLException {
        return incidenciaService.crearIncidencia(idInstancia, idFuncionario, lugar);
    }

    // ============================================================
    // OBTENER INCIDENCIA
    // ============================================================
    public Incidencia obtenerPorInstancia(int idInstancia) throws SQLException {
        return incidenciaService.obtenerPorInstancia(idInstancia);
    }

    // ============================================================
    // LISTAR INCIDENCIAS
    // ============================================================
    public List<Incidencia> listarTodas() throws SQLException {
        return incidenciaService.listarTodas();
    }

    public List<Incidencia> listarPorFuncionario(int idFuncionario) throws SQLException {
        return incidenciaService.listarPorFuncionario(idFuncionario);
    }

    // ============================================================
    // ACTUALIZAR INCIDENCIA
    // ============================================================
    public boolean actualizarIncidencia(int idInstancia, int idFuncionario, String lugar) throws SQLException {
        return incidenciaService.actualizarIncidencia(idInstancia, idFuncionario, lugar);
    }

    // ============================================================
    // ELIMINAR INCIDENCIA
    // ============================================================
    public boolean eliminarIncidencia(int idInstancia) throws SQLException {
        return incidenciaService.eliminarIncidencia(idInstancia);
    }
}
