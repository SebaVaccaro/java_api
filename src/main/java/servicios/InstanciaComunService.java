package servicios;

import DAO.InstanciaDAOImpl;
import DAO.InstanciaComunDAOImpl;
import modelo.InstanciaComun;

import SINGLETON.ConexionSingleton;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

public class InstanciaComunService {

    private final Connection conn;
    private final InstanciaDAOImpl baseDao;
    private final InstanciaComunDAOImpl comunDao;

    public InstanciaComunService() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
        this.baseDao = new InstanciaDAOImpl();
        this.comunDao = new InstanciaComunDAOImpl();
    }

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

    public InstanciaComun obtenerInstanciaComun(int idInstancia) throws SQLException {
        return comunDao.obtenerInstanciaComun(idInstancia);
    }

    public List<InstanciaComun> listarInstanciasComunes() throws SQLException {
        return comunDao.listarInstanciasComunes();
    }

    public List<InstanciaComun> listarPorSeguimiento(int idSeguimiento) throws SQLException {
        return comunDao.listarPorSeguimiento(idSeguimiento);
    }

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

    public boolean eliminarInstanciaComun(int idInstancia) throws SQLException {
        return baseDao.desactivarInstancia(idInstancia);
    }
}