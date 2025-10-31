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

    // Agregar nuevo rol (sin restricción de permisos)
    public boolean agregarRol(String nombre, boolean estActivo) throws SQLException {
        return rolServicio.agregarRol(nombre, estActivo);
    }

    // Actualizar rol existente (sin restricción de permisos)
    public boolean actualizarRol(int idRol, String nombre, boolean estActivo) throws SQLException {
        return rolServicio.actualizarRol(idRol, nombre, estActivo);
    }

    // Eliminar rol (sin restricción de permisos)
    public boolean eliminarRol(int idRol) throws SQLException {
        return rolServicio.eliminarRol(idRol);
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

