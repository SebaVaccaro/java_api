package servicios;

import DAO.DireccionDAOImpl;
import modelo.Direccion;

import java.sql.SQLException;
import java.util.List;

public class DireccionServicio {

    private final DireccionDAOImpl direccionDAOImpl;

    public DireccionServicio() throws SQLException {
        this.direccionDAOImpl = new DireccionDAOImpl();
    }

    // 🔹 Crear nueva dirección
    public Direccion crearDireccion(String calle, String numPuerta, String numApto, int idCiudad, int idUsuario) throws SQLException {
        Direccion direccion = new Direccion(calle, numPuerta, numApto, idCiudad, idUsuario);
        return direccionDAOImpl.crearDireccion(direccion);
    }

    // 🔹 Obtener dirección por ID
    public Direccion obtenerPorId(int idDireccion) throws SQLException {
        return direccionDAOImpl.obtenerDireccion(idDireccion);
    }

    // 🔹 Listar todas las direcciones
    public List<Direccion> listarTodas() throws SQLException {
        return direccionDAOImpl.listarDirecciones();
    }

    // 🔹 Listar por usuario
    public List<Direccion> listarPorUsuario(int idUsuario) throws SQLException {
        return direccionDAOImpl.listarPorUsuario(idUsuario);
    }

    // 🔹 Listar por ciudad
    public List<Direccion> listarPorCiudad(int idCiudad) throws SQLException {
        return direccionDAOImpl.listarPorCiudad(idCiudad);
    }

    // 🔹 Actualizar dirección
    public boolean actualizarDireccion(int idDireccion, String calle, String numPuerta, String numApto, int idCiudad, int idUsuario) throws SQLException {
        Direccion direccion = new Direccion(idDireccion, calle, numPuerta, numApto, idCiudad, idUsuario);
        return direccionDAOImpl.actualizarDireccion(direccion);
    }

    // 🔹 Eliminar dirección
    public boolean eliminarDireccion(int idDireccion) throws SQLException {
        return direccionDAOImpl.eliminarDireccion(idDireccion);
    }
}
