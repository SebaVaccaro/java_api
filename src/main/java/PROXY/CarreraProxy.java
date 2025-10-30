package PROXY;

import modelo.Carrera;
import servicios.CarreraServicio;
import utils.ValidarUsuario;

import java.sql.SQLException;
import java.util.List;

public class CarreraProxy {

    private final CarreraServicio service;
    private final ValidarUsuario validarUsuario;

    public CarreraProxy() throws Exception {
        this.service = new CarreraServicio();
        this.validarUsuario = new ValidarUsuario();
    }

    public Carrera crearCarrera(String codigo, String nombre, String plan) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo los usuarios con rol ADMINISTRADOR pueden crear carreras.");
        }
        return service.crearCarrera(codigo, nombre, plan);
    }

    public Carrera buscarCarreraPorId(int idCarrera) throws SQLException {
        return service.obtenerPorId(idCarrera);
    }

    public Carrera buscarCarreraPorCodigo(String codigo) throws SQLException {
        return service.obtenerPorCodigo(codigo);
    }

    public List<Carrera> listarTodas() throws SQLException {
        return service.listarTodas();
    }

    public boolean actualizarCarrera(int idCarrera, String codigo, String nombre, String plan) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo los usuarios con rol ADMINISTRADOR pueden modificar carreras.");
        }
        return service.actualizarCarrera(idCarrera, codigo, nombre, plan);
    }

    public boolean eliminarCarrera(int idCarrera) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo los usuarios con rol ADMINISTRADOR pueden eliminar carreras.");
        }
        return service.eliminarCarrera(idCarrera);
    }
}
