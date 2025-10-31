package servicios;

import DAO.CiudadDAOImpl;
import modelo.Ciudad;

import java.sql.SQLException;
import java.util.List;

public class CiudadServicio {

    private final CiudadDAOImpl ciudadDAOImpl;

    // Constructor: inicializa el DAO de ciudad
    public CiudadServicio() throws SQLException {
        this.ciudadDAOImpl = new CiudadDAOImpl();
    }

    // Crear nueva ciudad
    public Ciudad crearCiudad(int codPostal, String nombre, String departamento) throws SQLException {
        Ciudad ciudad = new Ciudad(codPostal, nombre, departamento);
        return ciudadDAOImpl.crearCiudad(ciudad);
    }

    // Obtener ciudad por ID
    public Ciudad obtenerPorId(int idCiudad) throws SQLException {
        return ciudadDAOImpl.obtenerCiudad(idCiudad);
    }

    // Obtener ciudad por nombre
    public Ciudad obtenerPorNombre(String nombre) throws SQLException {
        return ciudadDAOImpl.obtenerPorNombre(nombre);
    }

    // Listar todas las ciudades
    public List<Ciudad> listarTodas() throws SQLException {
        return ciudadDAOImpl.listarCiudades();
    }

    // Listar ciudades por departamento
    public List<Ciudad> listarPorDepartamento(String departamento) throws SQLException {
        return ciudadDAOImpl.listarPorDepartamento(departamento);
    }

    // Actualizar ciudad
    public boolean actualizarCiudad(int idCiudad, int codPostal, String nombre, String departamento) throws SQLException {
        Ciudad ciudad = new Ciudad(idCiudad, codPostal, nombre, departamento);
        return ciudadDAOImpl.actualizarCiudad(ciudad);
    }

    // Eliminar ciudad
    public boolean eliminarCiudad(int idCiudad) throws SQLException {
        return ciudadDAOImpl.eliminarCiudad(idCiudad);
    }
}

