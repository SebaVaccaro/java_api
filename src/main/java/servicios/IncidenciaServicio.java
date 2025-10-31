package servicios;

import DAO.IncidenciaDAOImpl;
import DAO.InstanciaDAOImpl;
import modelo.Incidencia;

import SINGLETON.ConexionSingleton;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

public class IncidenciaServicio {

    private final Connection conn;
    private final InstanciaDAOImpl baseDao;
    private final IncidenciaDAOImpl incidenciaDao;

    // Constructor: inicializa DAOs y conexión
    public IncidenciaServicio() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
        this.baseDao = new InstanciaDAOImpl();
        this.incidenciaDao = new IncidenciaDAOImpl();
    }

    // Crear nueva incidencia
    public Incidencia crearIncidencia(String titulo, OffsetDateTime fecHora, String descripcion,
                                      boolean estActivo, int idFuncionario, String lugar) throws SQLException {
        Incidencia incidencia = new Incidencia(0, titulo, fecHora, descripcion, estActivo, idFuncionario, lugar);

        try {
            conn.setAutoCommit(false);

            int idInstancia = baseDao.insertarInstancia(incidencia);
            incidencia.setIdInstancia(idInstancia);

            incidenciaDao.insertarIncidencia(incidencia);

            conn.commit();
            return incidencia;
        } catch (SQLException e) {
            conn.rollback();
            throw new SQLException("Error al crear Incidencia: " + e.getMessage(), e);
        } finally {
            conn.setAutoCommit(true);
        }
    }

    // Obtener incidencia por ID
    public Incidencia obtenerIncidencia(int idInstancia) throws SQLException {
        return incidenciaDao.obtenerIncidencia(idInstancia);
    }

    // Listar todas las incidencias
    public List<Incidencia> listarIncidencias() throws SQLException {
        return incidenciaDao.listarIncidencias();
    }

    // Listar incidencias por funcionario
    public List<Incidencia> listarPorFuncionario(int idFuncionario) throws SQLException {
        return incidenciaDao.listarPorFuncionario(idFuncionario);
    }

    // Actualizar incidencia
    public boolean actualizarIncidencia(int idInstancia, String titulo, OffsetDateTime fecHora, String descripcion,
                                        boolean estActivo, int idFuncionario, String lugar) throws SQLException {
        Incidencia incidencia = new Incidencia(idInstancia, titulo, fecHora, descripcion, estActivo, idFuncionario, lugar);
        try {
            conn.setAutoCommit(false);

            boolean baseOk = baseDao.actualizarInstancia(incidencia);
            boolean incOk = incidenciaDao.actualizarIncidencia(incidencia);

            if (baseOk && incOk) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    // Eliminar incidencia (baja lógica)
    public boolean eliminarIncidencia(int idInstancia) throws SQLException {
        return baseDao.desactivarInstancia(idInstancia);
    }
}
