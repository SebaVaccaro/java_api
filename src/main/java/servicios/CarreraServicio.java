package servicios;

import DAO.CarreraDAOImpl;
import modelo.Carrera;

import java.sql.SQLException;
import java.util.List;

public class CarreraServicio {

    private final CarreraDAOImpl carreraDAOImpl;

    // Constructor: inicializa el DAO de carrera
    public CarreraServicio() throws SQLException {
        this.carreraDAOImpl = new CarreraDAOImpl();
    }

    // Obtener carrera por ID
    public Carrera obtenerPorId(int idCarrera) throws SQLException {
        return carreraDAOImpl.obtenerCarrera(idCarrera);
    }

    // Obtener carrera por código
    public Carrera obtenerPorCodigo(String codigo) throws SQLException {
        return carreraDAOImpl.obtenerPorCodigo(codigo);
    }

    // Listar todas las carreras
    public List<Carrera> listarTodas() throws SQLException {
        return carreraDAOImpl.listarCarreras();
    }
}
