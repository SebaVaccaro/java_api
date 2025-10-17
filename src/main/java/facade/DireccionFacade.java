package facade;

import modelo.Direccion;
import servicios.DireccionService;

import java.sql.SQLException;
import java.util.List;

public class DireccionFacade {

    private final DireccionService direccionService;

    public DireccionFacade() throws SQLException {
        this.direccionService = new DireccionService();
    }

    // ============================================================
    // CREAR
    // ============================================================
    public Direccion crearDireccion(String calle, String numPuerta, String numApto, int idCiudad, int idUsuario) throws SQLException {
        return direccionService.crearDireccion(calle, numPuerta, numApto, idCiudad, idUsuario);
    }

    // ============================================================
    // LISTAR TODAS
    // ============================================================
    public List<Direccion> listarDirecciones() throws SQLException {
        return direccionService.listarTodas();
    }

    // ============================================================
    // OBTENER POR ID
    // ============================================================
    public Direccion obtenerDireccion(int idDireccion) throws SQLException {
        return direccionService.obtenerPorId(idDireccion);
    }

    // ============================================================
    // LISTAR POR USUARIO
    // ============================================================
    public List<Direccion> listarPorUsuario(int idUsuario) throws SQLException {
        return direccionService.listarPorUsuario(idUsuario);
    }

    // ============================================================
    // LISTAR POR CIUDAD
    // ============================================================
    public List<Direccion> listarPorCiudad(int idCiudad) throws SQLException {
        return direccionService.listarPorCiudad(idCiudad);
    }

    // ============================================================
    // ACTUALIZAR DIRECCIÓN
    // ============================================================
    public boolean actualizarDireccion(int idDireccion, String calle, String numPuerta, String numApto, int idCiudad, int idUsuario) throws SQLException {
        return direccionService.actualizarDireccion(idDireccion, calle, numPuerta, numApto, idCiudad, idUsuario);
    }

    // ============================================================
    // ELIMINAR DIRECCIÓN
    // ============================================================
    public boolean eliminarDireccion(int idDireccion) throws SQLException {
        return direccionService.eliminarDireccion(idDireccion);
    }
}
