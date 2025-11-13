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

}
