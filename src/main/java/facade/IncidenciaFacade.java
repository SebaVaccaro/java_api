package facade;

import modelo.Incidencia;
import servicios.IncidenciaService;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

public class IncidenciaFacade {

    private final IncidenciaService incidenciaService;

    public IncidenciaFacade() throws SQLException {
        this.incidenciaService = new IncidenciaService();
    }

    // ============================================================
    // CREAR INCIDENCIA
    // ============================================================
    public Incidencia crearIncidencia(String titulo,
                                      OffsetDateTime fecHora,
                                      String descripcion,
                                      boolean estActivo,
                                      int idFuncionario,
                                      String lugar) throws SQLException {
        return incidenciaService.crearIncidencia(titulo, fecHora, descripcion, estActivo, idFuncionario, lugar);
    }

    // ============================================================
    // OBTENER INCIDENCIA
    // ============================================================
    public Incidencia obtenerIncidencia(int idInstancia) throws SQLException {
        return incidenciaService.obtenerIncidencia(idInstancia);
    }

    // ============================================================
    // LISTAR
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
    public boolean actualizarIncidencia(int idInstancia,
                                        String titulo,
                                        OffsetDateTime fecHora,
                                        String descripcion,
                                        boolean estActivo,
                                        int idFuncionario,
                                        String lugar) throws SQLException {
        return incidenciaService.actualizarIncidencia(idInstancia, titulo, fecHora, descripcion, estActivo, idFuncionario, lugar);
    }

    // ============================================================
    // ELIMINAR / DESACTIVAR INCIDENCIA
    // ============================================================
    public boolean eliminarIncidencia(int idInstancia) throws SQLException {
        return incidenciaService.eliminarIncidencia(idInstancia);
    }
}