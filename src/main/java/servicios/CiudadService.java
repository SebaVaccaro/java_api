package servicios;

import DAO.CiudadDAO;
import modelo.Ciudad;

import java.sql.SQLException;
import java.util.List;

public class CiudadService {

    private final CiudadDAO ciudadDAO;

    public CiudadService() throws SQLException {
        this.ciudadDAO = new CiudadDAO();
    }

    // 🔹 Crear nueva ciudad
    public Ciudad crearCiudad(int codPostal, String nombre, String departamento) throws SQLException {
        Ciudad ciudad = new Ciudad(codPostal, nombre, departamento);
        return ciudadDAO.crearCiudad(ciudad);
    }

    // 🔹 Obtener ciudad por ID
    public Ciudad obtenerPorId(int idCiudad) throws SQLException {
        return ciudadDAO.obtenerCiudad(idCiudad);
    }

    // 🔹 Obtener ciudad por nombre
    public Ciudad obtenerPorNombre(String nombre) throws SQLException {
        return ciudadDAO.obtenerPorNombre(nombre);
    }

    // 🔹 Listar todas las ciudades
    public List<Ciudad> listarTodas() throws SQLException {
        return ciudadDAO.listarCiudades();
    }

    // 🔹 Listar por departamento
    public List<Ciudad> listarPorDepartamento(String departamento) throws SQLException {
        return ciudadDAO.listarPorDepartamento(departamento);
    }

    // 🔹 Actualizar ciudad
    public boolean actualizarCiudad(int idCiudad, int codPostal, String nombre, String departamento) throws SQLException {
        Ciudad ciudad = new Ciudad(idCiudad, codPostal, nombre, departamento);
        return ciudadDAO.actualizarCiudad(ciudad);
    }

    // 🔹 Eliminar ciudad
    public boolean eliminarCiudad(int idCiudad) throws SQLException {
        return ciudadDAO.eliminarCiudad(idCiudad);
    }
}
