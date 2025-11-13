package servicios;

import DAO.PerteneceDAOImpl;
import DAO.CarreraDAOImpl;
import DAO.ITRDAOImpl;
import modelo.Pertenece;
import modelo.Carrera;
import modelo.ITR;

import java.sql.SQLException;
import java.util.List;

public class PerteneceServicio {

    private final PerteneceDAOImpl dao;
    private final CarreraDAOImpl carreraDAO;
    private final ITRDAOImpl itrDAO;

    // Constructor: inicializa los DAOs necesarios
    public PerteneceServicio() throws SQLException {
        this.dao = new PerteneceDAOImpl();
        this.carreraDAO = new CarreraDAOImpl();
        this.itrDAO = new ITRDAOImpl();
    }

    // Agregar relación Carrera ↔ ITR
    public boolean agregarPertenece(int idCarrera, int idItr) throws SQLException {

        if (idCarrera <= 0) {
            throw new IllegalArgumentException("ID de carrera inválido: debe ser mayor que 0.");
        }
        if (idItr <= 0) {
            throw new IllegalArgumentException("ID de ITR inválido: debe ser mayor que 0.");
        }

        Carrera carrera = carreraDAO.obtenerCarrera(idCarrera);
        if (carrera == null) {
            throw new IllegalArgumentException("La carrera con ID " + idCarrera + " no existe.");
        }

        ITR itr = itrDAO.obtenerITR(idItr);
        if (itr == null) {
            throw new IllegalArgumentException("El ITR con ID " + idItr + " no existe.");
        }

        List<Integer> itrs = dao.listarItrPorCarrera(idCarrera);
        if (itrs.contains(idItr)) {
            throw new IllegalArgumentException("La carrera " + idCarrera +
                    " ya está asociada al ITR " + idItr + ".");
        }

        Pertenece p = new Pertenece(idCarrera, idItr);
        return dao.agregar(p);
    }

    // Eliminar relación Carrera ↔ ITR
    public boolean eliminarPertenece(int idCarrera, int idItr) throws SQLException {

        if (idCarrera <= 0) {
            throw new IllegalArgumentException("ID de carrera inválido: debe ser mayor que 0.");
        }
        if (idItr <= 0) {
            throw new IllegalArgumentException("ID de ITR inválido: debe ser mayor que 0.");
        }

        Carrera carrera = carreraDAO.obtenerCarrera(idCarrera);
        if (carrera == null) {
            throw new IllegalArgumentException("La carrera con ID " + idCarrera + " no existe.");
        }

        ITR itr = itrDAO.obtenerITR(idItr);
        if (itr == null) {
            throw new IllegalArgumentException("El ITR con ID " + idItr + " no existe.");
        }

        List<Integer> itrs = dao.listarItrPorCarrera(idCarrera);
        if (!itrs.contains(idItr)) {
            throw new IllegalArgumentException("La carrera " + idCarrera +
                    " no está asociada al ITR " + idItr + ".");
        }

        Pertenece p = new Pertenece(idCarrera, idItr);
        return dao.eliminar(p);
    }

    // Listar todas las relaciones
    public List<Pertenece> listarTodos() throws SQLException {
        return dao.listarTodos();
    }

    // Listar ITRs de una carrera
    public List<Integer> listarItrPorCarrera(int idCarrera) throws SQLException {
        if (idCarrera <= 0) {
            throw new IllegalArgumentException("ID de carrera inválido: debe ser mayor que 0.");
        }

        Carrera carrera = carreraDAO.obtenerCarrera(idCarrera);
        if (carrera == null) {
            throw new IllegalArgumentException("La carrera con ID " + idCarrera + " no existe.");
        }

        return dao.listarItrPorCarrera(idCarrera);
    }

    // Listar carreras de un ITR
    public List<Integer> listarCarrerasPorItr(int idItr) throws SQLException {
        if (idItr <= 0) {
            throw new IllegalArgumentException("ID de ITR inválido: debe ser mayor que 0.");
        }

        ITR itr = itrDAO.obtenerITR(idItr);
        if (itr == null) {
            throw new IllegalArgumentException("El ITR con ID " + idItr + " no existe.");
        }

        return dao.listarCarrerasPorItr(idItr);
    }
}
