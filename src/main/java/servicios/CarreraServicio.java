package servicios;

import DAO.CarreraDAOImpl;
import modelo.Carrera;

import java.sql.SQLException;
import java.util.List;

public class CarreraServicio {

    private final CarreraDAOImpl carreraDAOImpl;

    public CarreraServicio() throws SQLException {
        this.carreraDAOImpl = new CarreraDAOImpl();
    }

    // 🔹 Crear nueva carrera
    public Carrera crearCarrera(String codigo, String nombre, String plan) throws SQLException {
        Carrera carrera = new Carrera(codigo, nombre, plan);
        return carreraDAOImpl.crearCarrera(carrera);
    }

    // 🔹 Obtener carrera por ID
    public Carrera obtenerPorId(int idCarrera) throws SQLException {
        return carreraDAOImpl.obtenerCarrera(idCarrera);
    }

    // 🔹 Obtener carrera por código
    public Carrera obtenerPorCodigo(String codigo) throws SQLException {
        return carreraDAOImpl.obtenerPorCodigo(codigo);
    }

    // 🔹 Listar todas las carreras
    public List<Carrera> listarTodas() throws SQLException {
        return carreraDAOImpl.listarCarreras();
    }

    // 🔹 Actualizar carrera
    public boolean actualizarCarrera(int idCarrera, String codigo, String nombre, String plan) throws SQLException {
        Carrera carrera = new Carrera(idCarrera, codigo, nombre, plan);
        return carreraDAOImpl.actualizarCarrera(carrera);
    }

    // 🔹 Eliminar carrera
    public boolean eliminarCarrera(int idCarrera) throws SQLException {
        return carreraDAOImpl.eliminarCarrera(idCarrera);
    }
}
