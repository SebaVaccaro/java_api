package PROXY;

import modelo.Rol;
import servicios.RolServicio;

import java.sql.SQLException;
import java.util.List;

public class RolProxy {

    private final RolServicio rolServicio;

    public RolProxy() throws SQLException {
        this.rolServicio = new RolServicio();
    }

    // ============================================================
    // AGREGAR NUEVO ROL
    // ============================================================
    public boolean agregarRol(String nombre, boolean estActivo) throws SQLException {
        return rolServicio.agregarRol(nombre, estActivo);
    }

    // ============================================================
    // ACTUALIZAR ROL EXISTENTE
    // ============================================================
    public boolean actualizarRol(int idRol, String nombre, boolean estActivo) throws SQLException {
        return rolServicio.actualizarRol(idRol, nombre, estActivo);
    }

    // ============================================================
    // ELIMINAR ROL
    // ============================================================
    public boolean eliminarRol(int idRol) throws SQLException {
        return rolServicio.eliminarRol(idRol);
    }

    // ============================================================
    // BUSCAR ROL POR ID
    // ============================================================
    public Rol buscarPorId(int idRol) throws SQLException {
        return rolServicio.buscarPorId(idRol);
    }

    // ============================================================
    // LISTAR TODOS LOS ROLES
    // ============================================================
    public List<Rol> listarTodos() throws SQLException {
        return rolServicio.listarTodos();
    }
}
