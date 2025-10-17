package servicios;

import DAO.DireccionDAO;
import modelo.Direccion;

import java.sql.SQLException;
import java.util.List;

public class DireccionService {

    private final DireccionDAO direccionDAO;

    public DireccionService() throws SQLException {
        this.direccionDAO = new DireccionDAO();
    }

    // 🔹 Crear nueva dirección
    public Direccion crearDireccion(String calle, String numPuerta, String numApto, int idCiudad, int idUsuario) throws SQLException {
        Direccion direccion = new Direccion(calle, numPuerta, numApto, idCiudad, idUsuario);
        return direccionDAO.crearDireccion(direccion);
    }

    // 🔹 Obtener dirección por ID
    public Direccion obtenerPorId(int idDireccion) throws SQLException {
        return direccionDAO.obtenerDireccion(idDireccion);
    }

    // 🔹 Listar todas las direcciones
    public List<Direccion> listarTodas() throws SQLException {
        return direccionDAO.listarDirecciones();
    }

    // 🔹 Listar por usuario
    public List<Direccion> listarPorUsuario(int idUsuario) throws SQLException {
        return direccionDAO.listarPorUsuario(idUsuario);
    }

    // 🔹 Listar por ciudad
    public List<Direccion> listarPorCiudad(int idCiudad) throws SQLException {
        return direccionDAO.listarPorCiudad(idCiudad);
    }

    // 🔹 Actualizar dirección
    public boolean actualizarDireccion(int idDireccion, String calle, String numPuerta, String numApto, int idCiudad, int idUsuario) throws SQLException {
        Direccion direccion = new Direccion(idDireccion, calle, numPuerta, numApto, idCiudad, idUsuario);
        return direccionDAO.actualizarDireccion(direccion);
    }

    // 🔹 Eliminar dirección
    public boolean eliminarDireccion(int idDireccion) throws SQLException {
        return direccionDAO.eliminarDireccion(idDireccion);
    }
}
