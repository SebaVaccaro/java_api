package servicios;

import DAO.InstanciaDAOImpl;
import modelo.Instancia;

import java.sql.SQLException;
import java.util.List;

public class InstanciaService {

    private final InstanciaDAOImpl dao;

    public InstanciaService() throws SQLException {
        this.dao = new InstanciaDAOImpl();
    }

    // 🔹 Crear instancia indicando el tipo ("COMUN" o "INCIDENCIA")
    public Instancia crearInstancia(Instancia instancia, String tipo) throws SQLException {
        // Aquí podrías validar que idFuncionario exista
        return dao.crearInstancia(instancia, tipo);
    }

    // 🔹 Obtener instancia por id (devuelve la subclase correcta)
    public Instancia obtenerInstancia(int idInstancia) throws SQLException {
        return dao.obtenerInstancia(idInstancia);
    }

    // 🔹 Listar todas las instancias
    public List<Instancia> listarInstancias() throws SQLException {
        return dao.listarInstancias();
    }

    // 🔹 Listar instancias por funcionario
    public List<Instancia> listarPorFuncionario(int idFuncionario) throws SQLException {
        return dao.listarPorFuncionario(idFuncionario);
    }

    // 🔹 Actualizar instancia (requiere tipo)
    public boolean actualizarInstancia(Instancia instancia, String tipo) throws SQLException {
        return dao.actualizarInstancia(instancia, tipo);
    }

    // 🔹 Baja lógica (activar/desactivar)
    public boolean desactivarInstancia(int idInstancia) throws SQLException {
        return dao.desactivarInstancia(idInstancia);
    }
}
