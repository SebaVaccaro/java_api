package PROXY;

import modelo.InstanciaComun;
import servicios.InstanciaComunServicio;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

public class InstanciaComunProxy {

    private final InstanciaComunServicio instanciaComunServicio;

    public InstanciaComunProxy() throws SQLException {
        this.instanciaComunServicio = new InstanciaComunServicio();
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
        return instanciaComunServicio.crearInstanciaComun(titulo, fecHora, descripcion, estActivo, idFuncionario, idSeguimiento);
    }

    // ============================================================
    // OBTENER POR INSTANCIA
    // ============================================================
    public InstanciaComun obtenerInstanciaComun(int idInstancia) throws SQLException {
        return instanciaComunServicio.obtenerInstanciaComun(idInstancia);
    }

    // ============================================================
    // LISTAR
    // ============================================================
    public List<InstanciaComun> listarInstanciasComunes() throws SQLException {
        return instanciaComunServicio.listarInstanciasComunes();
    }

    public List<InstanciaComun> listarPorSeguimiento(int idSeguimiento) throws SQLException {
        return instanciaComunServicio.listarPorSeguimiento(idSeguimiento);
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
        return instanciaComunServicio.actualizarInstanciaComun(idInstancia, titulo, fecHora, descripcion, estActivo, idFuncionario, idSeguimiento);
    }

    // ============================================================
    // ELIMINAR / DESACTIVAR INSTANCIA COMÚN
    // ============================================================
    public boolean eliminarInstanciaComun(int idInstancia) throws SQLException {
        return instanciaComunServicio.eliminarInstanciaComun(idInstancia);
    }
}