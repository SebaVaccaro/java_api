package servicios;

import DAO.RolDAOImpl;
import modelo.Rol;

import java.sql.SQLException;
import java.util.List;

public class RolServicio {

    private final RolDAOImpl dao;

    // Constructor: inicializa el DAO de Rol
    public RolServicio() throws SQLException {
        this.dao = new RolDAOImpl();
    }

    // Buscar rol por ID
    public Rol buscarPorId(int idRol) throws SQLException {
        if (idRol <= 0) {
            throw new IllegalArgumentException("ID de rol inválido.");
        }
        return dao.buscarPorId(idRol);
    }

    // Listar todos los roles
    public List<Rol> listarTodos() throws SQLException {
        return dao.listarTodos();
    }
}
