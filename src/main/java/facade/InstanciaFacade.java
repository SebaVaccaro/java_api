package facade;

import modelo.Incidencia;
import modelo.Instancia;
import modelo.InstanciaComun;
import servicios.InstanciaService;

import java.sql.SQLException;
import java.util.List;

public class InstanciaFacade {

    private final InstanciaService instanciaService;

    public InstanciaFacade() throws SQLException {
        this.instanciaService = new InstanciaService();
    }

    // ============================================================
    // CREAR INSTANCIA
    // ============================================================
    public Instancia crearInstancia(Instancia instancia) throws SQLException {
        String tipo = obtenerTipo(instancia);
        return instanciaService.crearInstancia(instancia, tipo);
    }

    // ============================================================
    // OBTENER INSTANCIA
    // ============================================================
    public Instancia obtenerInstancia(int idInstancia) throws SQLException {
        return instanciaService.obtenerInstancia(idInstancia);
    }

    // ============================================================
    // LISTAR INSTANCIAS
    // ============================================================
    public List<Instancia> listarInstancias() throws SQLException {
        return instanciaService.listarInstancias();
    }

    public List<Instancia> listarPorFuncionario(int idFuncionario) throws SQLException {
        return instanciaService.listarPorFuncionario(idFuncionario);
    }

    // ============================================================
    // ACTUALIZAR INSTANCIA
    // ============================================================
    public boolean actualizarInstancia(Instancia instancia) throws SQLException {
        String tipo = obtenerTipo(instancia);
        return instanciaService.actualizarInstancia(instancia, tipo);
    }

    // ============================================================
    // ELIMINAR / DESACTIVAR INSTANCIA
    // ============================================================
    public boolean desactivarInstancia(int idInstancia) throws SQLException {
        return instanciaService.desactivarInstancia(idInstancia);
    }

    // ============================================================
    // MÉTODO AUXILIAR PARA DETECTAR TIPO
    // ============================================================
    private String obtenerTipo(Instancia instancia) {
        if (instancia instanceof InstanciaComun) {
            return "COMUN";
        } else if (instancia instanceof Incidencia) {
            return "INCIDENCIA";
        } else {
            throw new IllegalArgumentException("Tipo de instancia desconocido: " + instancia.getClass().getSimpleName());
        }
    }
}
