package servicios;

import DAO.InstanciaDAOImpl;
import DAO.InstanciaComunDAOImpl;
import modelo.InstanciaComun;
import SINGLETON.ConexionSingleton;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

public class InstanciaComunServicio {

    private final Connection conn;
    private final InstanciaDAOImpl baseDao;
    private final InstanciaComunDAOImpl comunDao;

    // Constructor: inicializa DAOs y conexión
    public InstanciaComunServicio() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
        this.baseDao = new InstanciaDAOImpl();
        this.comunDao = new InstanciaComunDAOImpl();
    }

    // Crear nueva instancia común
    public InstanciaComun crearInstanciaComun(String titulo, OffsetDateTime fecHora, String descripcion,
                                              boolean estActivo, int idFuncionario, int idSeguimiento) throws SQLException {

        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título de la instancia no puede estar vacío.");
        }
        if (fecHora == null) {
            throw new IllegalArgumentException("La fecha y hora de la instancia no pueden ser nulas.");
        }

        InstanciaComun instancia = new InstanciaComun(0, titulo, fecHora, descripcion, estActivo, idFuncionario, idSeguimiento);

        try {
            conn.setAutoCommit(false);

            int idInstancia = baseDao.insertarInstancia(instancia);
            instancia.setIdInstancia(idInstancia);

            comunDao.insertarInstanciaComun(instancia);

            conn.commit();
            return instancia;
        } catch (SQLException e) {
            conn.rollback();
            throw new SQLException("Error al crear la instancia común: " + e.getMessage(), e);
        } finally {
            conn.setAutoCommit(true);
        }
    }

    // Obtener instancia común por ID
    public InstanciaComun obtenerInstanciaComun(int idInstancia) throws SQLException {
        InstanciaComun instancia = comunDao.obtenerInstanciaComun(idInstancia);
        if (instancia == null) {
            throw new IllegalArgumentException("No existe una instancia común con ID " + idInstancia + ".");
        }
        return instancia;
    }

    // Listar todas las instancias comunes
    public List<InstanciaComun> listarInstanciasComunes() throws SQLException {
        List<InstanciaComun> lista = comunDao.listarInstanciasComunes();
        if (lista == null || lista.isEmpty()) {
            throw new IllegalStateException("No existen instancias comunes registradas en el sistema.");
        }
        return lista;
    }

    // Listar instancias comunes por estudiante
    public List<InstanciaComun> listarPorEstudiante(int idEstudiante) throws SQLException {
        List<InstanciaComun> lista = comunDao.listarPorEstudiante(idEstudiante);
        if (lista == null || lista.isEmpty()) {
            throw new IllegalStateException("El estudiante con ID " + idEstudiante + " no tiene instancias comunes registradas.");
        }
        return lista;
    }

    // Listar instancias comunes por seguimiento
    public List<InstanciaComun> listarPorSeguimiento(int idSeguimiento) throws SQLException {
        List<InstanciaComun> lista = comunDao.listarPorSeguimiento(idSeguimiento);
        if (lista == null || lista.isEmpty()) {
            throw new IllegalStateException("El seguimiento con ID " + idSeguimiento + " no tiene instancias comunes registradas.");
        }
        return lista;
    }

    // Actualizar instancia común (valida existencia)
    public boolean actualizarInstanciaComun(int idInstancia, String titulo, OffsetDateTime fecHora, String descripcion,
                                            boolean estActivo, int idFuncionario, int idSeguimiento) throws SQLException {

        InstanciaComun existente = comunDao.obtenerInstanciaComun(idInstancia);
        if (existente == null) {
            throw new IllegalArgumentException("No se puede actualizar: la instancia común con ID " + idInstancia + " no existe.");
        }

        InstanciaComun instancia = new InstanciaComun(idInstancia, titulo, fecHora, descripcion, estActivo, idFuncionario, idSeguimiento);

        try {
            conn.setAutoCommit(false);

            boolean baseOk = baseDao.actualizarInstancia(instancia);
            boolean comunOk = comunDao.actualizarInstanciaComun(instancia);

            if (baseOk && comunOk) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                throw new SQLException("Error: no se pudo actualizar completamente la instancia común con ID " + idInstancia + ".");
            }
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    // Eliminar instancia común (baja lógica, valida existencia)
    public boolean eliminarInstanciaComun(int idInstancia) throws SQLException {
        InstanciaComun existente = comunDao.obtenerInstanciaComun(idInstancia);
        if (existente == null) {
            throw new IllegalArgumentException("No se puede eliminar: la instancia común con ID " + idInstancia + " no existe.");
        }

        try {
            return baseDao.desactivarInstancia(idInstancia);
        } catch (SQLException e) {
            throw new SQLException("Error al eliminar la instancia común con ID " + idInstancia + ": " + e.getMessage(), e);
        }
    }
}
