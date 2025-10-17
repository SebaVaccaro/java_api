package facade;

import modelo.Rol;
import servicios.RolService;

import java.sql.SQLException;
import java.util.List;

public class RolFacade {

    private final RolService rolService;

    public RolFacade() throws SQLException {
        this.rolService = new RolService();
    }

    // ============================================================
    // AGREGAR NUEVO ROL
    // ============================================================
    public boolean agregarRol(String nombre, boolean estActivo) throws SQLException {
        return rolService.agregarRol(nombre, estActivo);
    }

    // ============================================================
    // ACTUALIZAR ROL EXISTENTE
    // ============================================================
    public boolean actualizarRol(int idRol, String nombre, boolean estActivo) throws SQLException {
        return rolService.actualizarRol(idRol, nombre, estActivo);
    }

    // ============================================================
    // ELIMINAR ROL
    // ============================================================
    public boolean eliminarRol(int idRol) throws SQLException {
        return rolService.eliminarRol(idRol);
    }

    // ============================================================
    // BUSCAR ROL POR ID
    // ============================================================
    public Rol buscarPorId(int idRol) throws SQLException {
        return rolService.buscarPorId(idRol);
    }

    // ============================================================
    // LISTAR TODOS LOS ROLES
    // ============================================================
    public List<Rol> listarTodos() throws SQLException {
        return rolService.listarTodos();
    }
}
