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

    // Crear ciudad (solo administradores)
    public Ciudad crearCiudad(int codPostal, String nombre, String departamento) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo administradores pueden crear ciudades.");
        }
        return service.crearCiudad(codPostal, nombre, departamento);
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

    // Actualizar ciudad completa (solo administradores)
    public boolean actualizarCiudad(int idCiudad, int codPostal, String nombre, String departamento) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo administradores pueden actualizar ciudades.");
        }
        return service.actualizarCiudad(idCiudad, codPostal, nombre, departamento);
    }

    // Actualizar solo el nombre de la ciudad (solo administradores)
    public boolean actualizarNombre(int idCiudad, String nombre) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo administradores pueden actualizar ciudades.");
        }
        return service.actualizarCiudad(idCiudad, 0, nombre, null);
    }

    // Actualizar solo el departamento de la ciudad (solo administradores)
    public boolean actualizarDepartamento(int idCiudad, String departamento) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo administradores pueden actualizar ciudades.");
        }
        return service.actualizarCiudad(idCiudad, 0, null, departamento);
    }

    // Actualizar solo el código postal de la ciudad (solo administradores)
    public boolean actualizarCodPostal(int idCiudad, int codPostal) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo administradores pueden actualizar ciudades.");
        }
        return service.actualizarCiudad(idCiudad, codPostal, null, null);
    }

    // Eliminar ciudad (solo administradores)
    public boolean eliminarCiudad(int idCiudad) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo administradores pueden eliminar ciudades.");
        }
        return service.eliminarCiudad(idCiudad);
    }
}
