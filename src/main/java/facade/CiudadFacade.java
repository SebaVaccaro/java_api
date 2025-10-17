package facade;

import modelo.Ciudad;
import servicios.CiudadService;

import java.sql.SQLException;
import java.util.List;

public class CiudadFacade {

    private final CiudadService service;

    public CiudadFacade() throws SQLException {
        this.service = new CiudadService();
    }

    // ==== CREATE ====
    public Ciudad crearCiudad(int codPostal, String nombre, String departamento) throws SQLException {
        return service.crearCiudad(codPostal, nombre, departamento);
    }

    // ==== READ ====
    public Ciudad buscarCiudadPorId(int idCiudad) throws SQLException {
        return service.obtenerPorId(idCiudad);
    }

    public Ciudad buscarCiudadPorNombre(String nombre) throws SQLException {
        return service.obtenerPorNombre(nombre);
    }

    public List<Ciudad> listarTodas() throws SQLException {
        return service.listarTodas();
    }

    public List<Ciudad> listarPorDepartamento(String departamento) throws SQLException {
        return service.listarPorDepartamento(departamento);
    }

    // ==== UPDATE ====
    public boolean actualizarCiudad(int idCiudad, int codPostal, String nombre, String departamento) throws SQLException {
        return service.actualizarCiudad(idCiudad, codPostal, nombre, departamento);
    }

    // Métodos de actualización parcial (opcional)
    public boolean actualizarNombre(int idCiudad, String nombre) throws SQLException {
        return service.actualizarCiudad(idCiudad, 0, nombre, null);
    }

    public boolean actualizarDepartamento(int idCiudad, String departamento) throws SQLException {
        return service.actualizarCiudad(idCiudad, 0, null, departamento);
    }

    public boolean actualizarCodPostal(int idCiudad, int codPostal) throws SQLException {
        return service.actualizarCiudad(idCiudad, codPostal, null, null);
    }

    // ==== DELETE ====
    public boolean eliminarCiudad(int idCiudad) throws SQLException {
        return service.eliminarCiudad(idCiudad);
    }
}
