package facade;

import modelo.Carrera;
import servicios.CarreraService;

import java.sql.SQLException;
import java.util.List;

public class CarreraFacade {

    private final CarreraService service;

    public CarreraFacade() throws SQLException {
        this.service = new CarreraService();
    }

    // ==== CREATE ====
    public Carrera crearCarrera(String codigo, String nombre, String plan) throws SQLException {
        return service.crearCarrera(codigo, nombre, plan);
    }

    // ==== READ ====
    public Carrera buscarCarreraPorId(int idCarrera) throws SQLException {
        return service.obtenerPorId(idCarrera);
    }

    public Carrera buscarCarreraPorCodigo(String codigo) throws SQLException {
        return service.obtenerPorCodigo(codigo);
    }

    public List<Carrera> listarTodas() throws SQLException {
        return service.listarTodas();
    }

    // ==== UPDATE ====
    public boolean actualizarCarrera(int idCarrera, String codigo, String nombre, String plan) throws SQLException {
        return service.actualizarCarrera(idCarrera, codigo, nombre, plan);
    }

    // ==== DELETE ====
    public boolean eliminarCarrera(int idCarrera) throws SQLException {
        return service.eliminarCarrera(idCarrera);
    }
}
