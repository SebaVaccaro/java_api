package PROXY;

import modelo.Rol;
import servicios.RolServicio;

import java.sql.SQLException;
import java.util.List;

public class RolProxy {

    private final RolServicio rolServicio;

    // Constructor: inicializa el servicio de roles
    public RolProxy() throws SQLException {
        this.rolServicio = new RolServicio();
    }
    // Buscar rol por ID (sin restricción de permisos)
    public Rol buscarPorId(int idRol) throws SQLException {
        return rolServicio.buscarPorId(idRol);
    }

    // Listar todos los roles (sin restricción de permisos)
    public List<Rol> listarTodos() throws SQLException {
        return rolServicio.listarTodos();
    }
}

