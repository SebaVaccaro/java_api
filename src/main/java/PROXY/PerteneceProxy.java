package PROXY;

import modelo.Pertenece;
import servicios.PerteneceServicio;

import java.sql.SQLException;
import java.util.List;

public class PerteneceProxy {

    private final PerteneceServicio perteneceServicio;

    // Constructor: inicializa el servicio de relaciones Carrera ↔ ITR
    public PerteneceProxy() throws SQLException {
        this.perteneceServicio = new PerteneceServicio();
    }

    // Agregar relación Carrera ↔ ITR (sin restricción de permisos)
    public boolean agregarPertenece(int idCarrera, int idItr) throws SQLException {
        return perteneceServicio.agregarPertenece(idCarrera, idItr);
    }

    // Eliminar relación Carrera ↔ ITR (sin restricción de permisos)
    public boolean eliminarPertenece(int idCarrera, int idItr) throws SQLException {
        return perteneceServicio.eliminarPertenece(idCarrera, idItr);
    }

    // Listar todas las relaciones (sin restricción de permisos)
    public List<Pertenece> listarTodos() throws SQLException {
        return perteneceServicio.listarTodos();
    }

    // Listar ITRs de una carrera específica (sin restricción de permisos)
    public List<Integer> listarItrPorCarrera(int idCarrera) throws SQLException {
        return perteneceServicio.listarItrPorCarrera(idCarrera);
    }

    // Listar carreras de un ITR específico (sin restricción de permisos)
    public List<Integer> listarCarrerasPorItr(int idItr) throws SQLException {
        return perteneceServicio.listarCarrerasPorItr(idItr);
    }
}

