package PROXY;

import modelo.Pertenece;
import servicios.PerteneceServicio;
import utils.ValidarUsuario;

import java.sql.SQLException;
import java.util.List;

public class PerteneceProxy {

    private final PerteneceServicio perteneceServicio;
    private final ValidarUsuario validarUsuario;

    // Constructor: inicializa el servicio de relaciones Carrera ↔ ITR
    public PerteneceProxy() throws SQLException {
        this.perteneceServicio = new PerteneceServicio();
        this.validarUsuario = new ValidarUsuario();
    }

    // Listar todas las relaciones
    public List<Pertenece> listarTodos() throws SQLException {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo un administrador puede listar todas las relaciones carrera–ITR.");
        }
        return perteneceServicio.listarTodos();
    }

    // Listar ITRs de una carrera específica
    public List<Integer> listarItrPorCarrera(int idCarrera) throws SQLException {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo un administrador puede consultar los ITR asociados a una carrera.");
        }
        return perteneceServicio.listarItrPorCarrera(idCarrera);
    }

    // Listar carreras de un ITR específico
    public List<Integer> listarCarrerasPorItr(int idItr) throws SQLException {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo un administrador puede consultar las carreras asociadas a un ITR.");
        }
        return perteneceServicio.listarCarrerasPorItr(idItr);
    }
}
