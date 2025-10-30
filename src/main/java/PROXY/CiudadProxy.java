package PROXY;

import modelo.Ciudad;
import servicios.CiudadServicio;
import utils.ValidarUsuario;

import java.sql.SQLException;
import java.util.List;

public class CiudadProxy {

    private final CiudadServicio service;
    private final ValidarUsuario validarUsuario;

    public CiudadProxy() throws Exception {
        this.service = new CiudadServicio();
        this.validarUsuario = new ValidarUsuario();
    }

    public Ciudad crearCiudad(int codPostal, String nombre, String departamento) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo los usuarios con rol ADMINISTRADOR pueden crear ciudades.");
        }
        return service.crearCiudad(codPostal, nombre, departamento);
    }

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

    public boolean actualizarCiudad(int idCiudad, int codPostal, String nombre, String departamento) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo los usuarios con rol ADMINISTRADOR pueden modificar ciudades.");
        }
        return service.actualizarCiudad(idCiudad, codPostal, nombre, departamento);
    }

    public boolean actualizarNombre(int idCiudad, String nombre) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo los usuarios con rol ADMINISTRADOR pueden modificar ciudades.");
        }
        return service.actualizarCiudad(idCiudad, 0, nombre, null);
    }

    public boolean actualizarDepartamento(int idCiudad, String departamento) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo los usuarios con rol ADMINISTRADOR pueden modificar ciudades.");
        }
        return service.actualizarCiudad(idCiudad, 0, null, departamento);
    }

    public boolean actualizarCodPostal(int idCiudad, int codPostal) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo los usuarios con rol ADMINISTRADOR pueden modificar ciudades.");
        }
        return service.actualizarCiudad(idCiudad, codPostal, null, null);
    }

    public boolean eliminarCiudad(int idCiudad) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo los usuarios con rol ADMINISTRADOR pueden eliminar ciudades.");
        }
        return service.eliminarCiudad(idCiudad);
    }
}
