package servicios;

import DAO.IncidenciaDAOImpl;
import modelo.Incidencia;

import java.sql.SQLException;
import java.util.List;

public class IncidenciaService {

    private final IncidenciaDAOImpl incidenciaDAOImpl;

    public IncidenciaService() throws SQLException {
        this.incidenciaDAOImpl = new IncidenciaDAOImpl();
    }

    // 🔹 Crear nueva incidencia (requiere objeto completo)
    public Incidencia crearIncidencia(Incidencia incidencia) throws SQLException {
        // Aquí podrías validar idFuncionario o campos obligatorios
        return incidenciaDAOImpl.crearIncidencia(incidencia);
    }

    // 🔹 Obtener incidencia por ID de instancia
    public Incidencia obtenerIncidencia(int idInstancia) throws SQLException {
        return incidenciaDAOImpl.obtenerIncidencia(idInstancia);
    }

    // 🔹 Listar todas las incidencias
    public List<Incidencia> listarIncidencias() throws SQLException {
        return incidenciaDAOImpl.listarIncidencias();
    }

    // 🔹 Listar incidencias por funcionario
    public List<Incidencia> listarPorFuncionario(int idFuncionario) throws SQLException {
        return incidenciaDAOImpl.listarPorFuncionario(idFuncionario);
    }

    // 🔹 Actualizar incidencia
    public boolean actualizarIncidencia(Incidencia incidencia) throws SQLException {
        // Validar campos si es necesario
        return incidenciaDAOImpl.actualizarIncidencia(incidencia);
    }

    // 🔹 Eliminar incidencia
    public boolean eliminarIncidencia(int idInstancia) throws SQLException {
        return incidenciaDAOImpl.eliminarIncidencia(idInstancia);
    }
}
