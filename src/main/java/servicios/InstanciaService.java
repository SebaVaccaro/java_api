package servicios;

import DAO.InstanciaDAO;
import modelo.Instancia;

import java.sql.SQLException;
import java.util.List;

public class InstanciaService {

    private final InstanciaDAO dao;

    public InstanciaService() throws SQLException {
        this.dao = new InstanciaDAO();
    }

    public Instancia crearInstancia(Instancia instancia) throws SQLException {
        // Aquí podrías validar que idFuncionario exista
        return dao.crearInstancia(instancia);
    }

    public Instancia obtenerInstancia(int idInstancia) throws SQLException {
        return dao.obtenerInstancia(idInstancia);
    }

    public List<Instancia> listarInstancias() throws SQLException {
        return dao.listarInstancias();
    }

    public List<Instancia> listarPorFuncionario(int idFuncionario) throws SQLException {
        return dao.listarPorFuncionario(idFuncionario);
    }

    public boolean actualizarInstancia(Instancia instancia) throws SQLException {
        return dao.actualizarInstancia(instancia);
    }

    public boolean eliminarInstancia(int idInstancia) throws SQLException {
        return dao.eliminarInstancia(idInstancia);
    }
}
