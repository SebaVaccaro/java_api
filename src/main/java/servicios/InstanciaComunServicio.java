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
            throw new SQLException("Error al crear InstanciaComun: " + e.getMessage(), e);
        } finally {
            conn.setAutoCommit(true);
        }
    }

    // Obtener instancia común por ID
    public InstanciaComun obtenerInstanciaComun(int idInstancia) throws SQLException {
        return comunDao.obtenerInstanciaComun(idInstancia);
    }

    // Listar todas las instancias comunes
    public List<InstanciaComun> listarInstanciasComunes() throws SQLException {
        return comunDao.listarInstanciasComunes();
    }

    // Listar instancias comunes por seguimiento
    public List<InstanciaComun> listarPorSeguimiento(int idSeguimiento) throws SQLException {
        return comunDao.listarPorSeguimiento(idSeguimiento);
    }

    // Actualizar instancia común
    public boolean actualizarInstanciaComun(int idInstancia, String titulo, OffsetDateTime fecHora, String descripcion,
                                            boolean estActivo, int idFuncionario, int idSeguimiento) throws SQLException {
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
                return false;
            }
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    // Eliminar instancia común (baja lógica)
    public boolean eliminarInstanciaComun(int idInstancia) throws SQLException {
        return baseDao.desactivarInstancia(idInstancia);
    }
}
