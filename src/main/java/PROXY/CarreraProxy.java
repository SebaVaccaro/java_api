package PROXY;

import modelo.Carrera;
import servicios.CarreraServicio;
import utils.ValidarUsuario;

import java.sql.SQLException;
import java.util.List;

public class CarreraProxy {

    private final CarreraServicio service;
    private final ValidarUsuario validarUsuario;

    // Constructor: inicializa el servicio de carreras y el validador de usuario
    public CarreraProxy() throws Exception {
        this.service = new CarreraServicio();
        this.validarUsuario = new ValidarUsuario();
    }

    // Crear carrera (solo administradores)
    public Carrera crearCarrera(String codigo, String nombre, String plan) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo administradores pueden crear carreras.");
        }
        return service.crearCarrera(codigo, nombre, plan);
    }

    // Buscar carrera por ID (sin restricción de permisos)
    public Carrera buscarCarreraPorId(int idCarrera) throws SQLException {
        return service.obtenerPorId(idCarrera);
    }

    // Buscar carrera por código (sin restricción de permisos)
    public Carrera buscarCarreraPorCodigo(String codigo) throws SQLException {
        return service.obtenerPorCodigo(codigo);
    }

    // Listar todas las carreras (sin restricción de permisos)
    public List<Carrera> listarTodas() throws SQLException {
        return service.listarTodas();
    }

    // Actualizar carrera (solo administradores)
    public boolean actualizarCarrera(int idCarrera, String codigo, String nombre, String plan) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo administradores pueden actualizar carreras.");
        }
        return service.actualizarCarrera(idCarrera, codigo, nombre, plan);
    }

    // Eliminar carrera (solo administradores)
    public boolean eliminarCarrera(int idCarrera) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo administradores pueden eliminar carreras.");
        }
        return service.eliminarCarrera(idCarrera);
    }
}
