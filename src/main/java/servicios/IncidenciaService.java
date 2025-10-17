package servicios;

import DAO.IncidenciaDAO;
import modelo.Incidencia;

import java.sql.SQLException;
import java.util.List;

public class IncidenciaService {

    private final IncidenciaDAO incidenciaDAO;

    public IncidenciaService() throws SQLException {
        this.incidenciaDAO = new IncidenciaDAO();
    }

    // 🔹 Crear nueva incidencia
    public Incidencia crearIncidencia(int idInstancia, int idFuncionario, String lugar) throws SQLException {
        Incidencia incidencia = new Incidencia(idInstancia, idFuncionario, lugar);
        return incidenciaDAO.crearIncidencia(incidencia);
    }

    // 🔹 Obtener incidencia por ID de instancia
    public Incidencia obtenerPorInstancia(int idInstancia) throws SQLException {
        return incidenciaDAO.obtenerIncidencia(idInstancia);
    }

    // 🔹 Listar todas las incidencias
    public List<Incidencia> listarTodas() throws SQLException {
        return incidenciaDAO.listarIncidencias();
    }

    // 🔹 Listar incidencias por funcionario
    public List<Incidencia> listarPorFuncionario(int idFuncionario) throws SQLException {
        return incidenciaDAO.listarPorFuncionario(idFuncionario);
    }

    // 🔹 Actualizar incidencia
    public boolean actualizarIncidencia(int idInstancia, int idFuncionario, String lugar) throws SQLException {
        Incidencia incidencia = new Incidencia(idInstancia, idFuncionario, lugar);
        return incidenciaDAO.actualizarIncidencia(incidencia);
    }

    // 🔹 Eliminar incidencia
    public boolean eliminarIncidencia(int idInstancia) throws SQLException {
        return incidenciaDAO.eliminarIncidencia(idInstancia);
    }
}
