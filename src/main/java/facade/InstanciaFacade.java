package facade;

import modelo.Instancia;
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
        return instanciaService.crearInstancia(instancia);
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
        return instanciaService.actualizarInstancia(instancia);
    }

    // ============================================================
    // ELIMINAR INSTANCIA
    // ============================================================
    public boolean eliminarInstancia(int idInstancia) throws SQLException {
        return instanciaService.eliminarInstancia(idInstancia);
    }
}
