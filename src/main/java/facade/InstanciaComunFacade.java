package facade;

import modelo.InstanciaComun;
import servicios.InstanciaComunService;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

public class InstanciaComunFacade {

    private final InstanciaComunService instanciaComunService;

    public InstanciaComunFacade() throws SQLException {
        this.instanciaComunService = new InstanciaComunService();
    }

    // ============================================================
    // CREAR INSTANCIA COMÚN
    // ============================================================
    public InstanciaComun crearInstanciaComun(String titulo,
                                              OffsetDateTime fecHora,
                                              String descripcion,
                                              boolean estActivo,
                                              int idFuncionario,
                                              int idSeguimiento) throws SQLException {
        return instanciaComunService.crearInstanciaComun(titulo, fecHora, descripcion, estActivo, idFuncionario, idSeguimiento);
    }

    // ============================================================
    // OBTENER POR INSTANCIA
    // ============================================================
    public InstanciaComun obtenerPorInstancia(int idInstancia) throws SQLException {
        return instanciaComunService.obtenerPorInstancia(idInstancia);
    }

    // ============================================================
    // LISTAR
    // ============================================================
    public List<InstanciaComun> listarTodos() throws SQLException {
        return instanciaComunService.listarTodos();
    }

    public List<InstanciaComun> listarPorSeguimiento(int idSeguimiento) throws SQLException {
        return instanciaComunService.listarPorSeguimiento(idSeguimiento);
    }

    // ============================================================
    // ACTUALIZAR INSTANCIA COMÚN
    // ============================================================
    public boolean actualizarInstanciaComun(int idInstancia,
                                            String titulo,
                                            OffsetDateTime fecHora,
                                            String descripcion,
                                            boolean estActivo,
                                            int idFuncionario,
                                            int idSeguimiento) throws SQLException {
        return instanciaComunService.actualizarInstanciaComun(idInstancia, titulo, fecHora, descripcion, estActivo, idFuncionario, idSeguimiento);
    }

    // ============================================================
    // ELIMINAR POR INSTANCIA
    // ============================================================
    public boolean eliminarPorInstancia(int idInstancia) throws SQLException {
        return instanciaComunService.eliminarPorInstancia(idInstancia);
    }
}
