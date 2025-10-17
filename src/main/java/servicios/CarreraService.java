package servicios;

import DAO.CarreraDAO;
import modelo.Carrera;

import java.sql.SQLException;
import java.util.List;

public class CarreraService {

    private final CarreraDAO carreraDAO;

    public CarreraService() throws SQLException {
        this.carreraDAO = new CarreraDAO();
    }

    // 🔹 Crear nueva carrera
    public Carrera crearCarrera(String codigo, String nombre, String plan) throws SQLException {
        Carrera carrera = new Carrera(codigo, nombre, plan);
        return carreraDAO.crearCarrera(carrera);
    }

    // 🔹 Obtener carrera por ID
    public Carrera obtenerPorId(int idCarrera) throws SQLException {
        return carreraDAO.obtenerCarrera(idCarrera);
    }

    // 🔹 Obtener carrera por código
    public Carrera obtenerPorCodigo(String codigo) throws SQLException {
        return carreraDAO.obtenerPorCodigo(codigo);
    }

    // 🔹 Listar todas las carreras
    public List<Carrera> listarTodas() throws SQLException {
        return carreraDAO.listarCarreras();
    }

    // 🔹 Actualizar carrera
    public boolean actualizarCarrera(int idCarrera, String codigo, String nombre, String plan) throws SQLException {
        Carrera carrera = new Carrera(idCarrera, codigo, nombre, plan);
        return carreraDAO.actualizarCarrera(carrera);
    }

    // 🔹 Eliminar carrera
    public boolean eliminarCarrera(int idCarrera) throws SQLException {
        return carreraDAO.eliminarCarrera(idCarrera);
    }
}
