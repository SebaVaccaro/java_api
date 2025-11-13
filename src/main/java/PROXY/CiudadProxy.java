package PROXY;

import modelo.Ciudad;
import servicios.CiudadServicio;
import utils.ValidarUsuario;

import java.sql.SQLException;
import java.util.List;

public class CiudadProxy {

    private final CiudadServicio service;
    private final ValidarUsuario validarUsuario;

    // Constructor: inicializa el servicio de ciudades y el validador de usuario
    public CiudadProxy() throws Exception {
        this.service = new CiudadServicio();
        this.validarUsuario = new ValidarUsuario();
    }

    // Buscar ciudad por ID (sin restricción de permisos)
    public Ciudad buscarCiudadPorId(int idCiudad) throws SQLException {
        return service.obtenerPorId(idCiudad);
    }

    // Buscar ciudad por nombre (sin restricción de permisos)
    public Ciudad buscarCiudadPorNombre(String nombre) throws SQLException {
        return service.obtenerPorNombre(nombre);
    }

    // Listar todas las ciudades (sin restricción de permisos)
    public List<Ciudad> listarTodas() throws SQLException {
        return service.listarTodas();
    }

    // Listar ciudades por departamento (sin restricción de permisos)
    public List<Ciudad> listarPorDepartamento(String departamento) throws SQLException {
        return service.listarPorDepartamento(departamento);
    }

}
