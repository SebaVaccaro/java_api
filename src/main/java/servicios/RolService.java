package servicios;

import DAO.RolDAO;
import modelo.Rol;

import java.sql.SQLException;
import java.util.List;

public class RolService {

    private final RolDAO dao;

    public RolService() throws SQLException {
        this.dao = new RolDAO();
    }

    // 🔹 Crear nuevo rol
    public boolean agregarRol(String nombre, boolean estActivo) throws SQLException {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del rol no puede estar vacío.");
        }
        Rol rol = new Rol(nombre, estActivo);
        return dao.agregar(rol);
    }

    // 🔹 Actualizar rol existente
    public boolean actualizarRol(int idRol, String nombre, boolean estActivo) throws SQLException {
        if (idRol <= 0) {
            throw new IllegalArgumentException("ID de rol inválido.");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del rol no puede estar vacío.");
        }
        Rol rol = new Rol(idRol, nombre, estActivo);
        return dao.actualizar(rol);
    }

    // 🔹 Eliminar rol
    public boolean eliminarRol(int idRol) throws SQLException {
        if (idRol <= 0) {
            throw new IllegalArgumentException("ID de rol inválido.");
        }
        return dao.eliminar(idRol);
    }

    // 🔹 Buscar rol por ID
    public Rol buscarPorId(int idRol) throws SQLException {
        if (idRol <= 0) {
            throw new IllegalArgumentException("ID de rol inválido.");
        }
        return dao.buscarPorId(idRol);
    }

    // 🔹 Listar todos los roles
    public List<Rol> listarTodos() throws SQLException {
        return dao.listarTodos();
    }
}
