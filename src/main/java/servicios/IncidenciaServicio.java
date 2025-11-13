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

        // Validaciones básicas
        if (titulo == null || titulo.trim().isEmpty())
            throw new IllegalArgumentException("El título no puede estar vacío.");
        if (fecHora == null)
            throw new IllegalArgumentException("La fecha y hora no pueden ser nulas.");
        if (descripcion == null || descripcion.trim().isEmpty())
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        if (lugar == null || lugar.trim().isEmpty())
            throw new IllegalArgumentException("El lugar no puede estar vacío.");

        Incidencia incidencia = new Incidencia(0, titulo, fecHora, descripcion, estActivo, idFuncionario, lugar);

        try {
            conn.setAutoCommit(false);

            int idInstancia = baseDao.insertarInstancia(incidencia);
            incidencia.setIdInstancia(idInstancia);

            incidenciaDao.insertarIncidencia(incidencia);

            conn.commit();
            return incidencia;
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw new SQLException("Error al crear incidencia: " + e.getMessage(), e);
        } finally {
            if (conn != null) conn.setAutoCommit(true);
        }
    }

    // Obtener incidencia por ID
    public Incidencia obtenerIncidencia(int idInstancia) throws SQLException {
        Incidencia incidencia = incidenciaDao.obtenerIncidencia(idInstancia);
        if (incidencia == null) {
            throw new IllegalArgumentException("La incidencia con ID " + idInstancia + " no existe.");
        }
        return incidencia;
    }

    // Listar todas las incidencias
    public List<Incidencia> listarIncidencias() throws SQLException {
        List<Incidencia> lista = incidenciaDao.listarIncidencias();
        if (lista == null || lista.isEmpty()) {
            throw new IllegalStateException("No existen incidencias registradas en el sistema.");
        }
        return lista;
    }

    // Listar incidencias por funcionario
    public List<Incidencia> listarPorFuncionario(int idFuncionario) throws SQLException {
        List<Incidencia> lista = incidenciaDao.listarPorFuncionario(idFuncionario);
        if (lista == null || lista.isEmpty()) {
            throw new IllegalStateException("El funcionario con ID " + idFuncionario + " no tiene incidencias registradas.");
        }
        return lista;
    }

    // Actualizar incidencia (verifica existencia antes de modificar)
    public boolean actualizarIncidencia(int idInstancia, String titulo, OffsetDateTime fecHora, String descripcion,
                                        boolean estActivo, int idFuncionario, String lugar) throws SQLException {

        Incidencia existente = incidenciaDao.obtenerIncidencia(idInstancia);
        if (existente == null) {
            throw new IllegalArgumentException("No se puede actualizar: la incidencia con ID " + idInstancia + " no existe.");
        }

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
                throw new SQLException("Error: no se pudo actualizar completamente la incidencia con ID " + idInstancia + ".");
            }
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
        }
    }

    // Eliminar incidencia (baja lógica, con validación previa)
    public boolean eliminarIncidencia(int idInstancia) throws SQLException {
        Incidencia existente = incidenciaDao.obtenerIncidencia(idInstancia);
        if (existente == null) {
            throw new IllegalArgumentException("No se puede eliminar: la incidencia con ID " + idInstancia + " no existe.");
        }

        try {
            return baseDao.desactivarInstancia(idInstancia);
        } catch (SQLException e) {
            throw new SQLException("Error al eliminar incidencia con ID " + idInstancia + ": " + e.getMessage(), e);
        }
    }
}
