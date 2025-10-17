package servicios;

import DAO.PerteneceDAO;
import modelo.Pertenece;

import java.sql.SQLException;
import java.util.List;

public class PerteneceService {

    private final PerteneceDAO dao;

    public PerteneceService() throws SQLException {
        this.dao = new PerteneceDAO();
    }

    // 🔹 Agregar relación Carrera ↔ ITR
    public boolean agregarPertenece(int idCarrera, int idItr) throws SQLException {
        if (idCarrera <= 0 || idItr <= 0) {
            throw new IllegalArgumentException("ID de carrera o ITR inválido.");
        }
        Pertenece p = new Pertenece(idCarrera, idItr);
        return dao.agregar(p);
    }

    // 🔹 Eliminar relación Carrera ↔ ITR
    public boolean eliminarPertenece(int idCarrera, int idItr) throws SQLException {
        if (idCarrera <= 0 || idItr <= 0) {
            throw new IllegalArgumentException("ID de carrera o ITR inválido.");
        }
        Pertenece p = new Pertenece(idCarrera, idItr);
        return dao.eliminar(p);
    }

    // 🔹 Listar todas las relaciones
    public List<Pertenece> listarTodos() throws SQLException {
        return dao.listarTodos();
    }

    // 🔹 Listar ITRs de una carrera
    public List<Integer> listarItrPorCarrera(int idCarrera) throws SQLException {
        if (idCarrera <= 0) {
            throw new IllegalArgumentException("ID de carrera inválido.");
        }
        return dao.listarItrPorCarrera(idCarrera);
    }

    // 🔹 Listar carreras de un ITR
    public List<Integer> listarCarrerasPorItr(int idItr) throws SQLException {
        if (idItr <= 0) {
            throw new IllegalArgumentException("ID de ITR inválido.");
        }
        return dao.listarCarrerasPorItr(idItr);
    }
}
