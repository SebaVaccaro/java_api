package servicios;

import DAO.GrupoDAOImpl;
import modelo.Grupo;

import java.sql.SQLException;
import java.util.List;

public class GrupoServicio {

    private final GrupoDAOImpl grupoDAOImpl;

    // Constructor: inicializa DAO
    public GrupoServicio() throws SQLException {
        this.grupoDAOImpl = new GrupoDAOImpl();
    }

    // Crear nuevo grupo
    public Grupo crearGrupo(String nomGrupo, int idCarrera) throws SQLException {
        Grupo g = new Grupo(nomGrupo, idCarrera);
        return grupoDAOImpl.crearGrupo(g);
    }

    // Obtener grupo por ID
    public Grupo obtenerPorId(int idGrupo) throws SQLException {
        return grupoDAOImpl.obtenerGrupo(idGrupo);
    }

    // Listar todos los grupos
    public List<Grupo> listarTodos() throws SQLException {
        return grupoDAOImpl.listarGrupos();
    }

    // Listar grupos por carrera
    public List<Grupo> listarPorCarrera(int idCarrera) throws SQLException {
        return grupoDAOImpl.listarPorCarrera(idCarrera);
    }

    // Actualizar grupo
    public boolean actualizarGrupo(int idGrupo, String nomGrupo, int idCarrera) throws SQLException {
        Grupo g = new Grupo(idGrupo, nomGrupo, idCarrera);
        return grupoDAOImpl.actualizarGrupo(g);
    }

    // Eliminar grupo
    public boolean eliminarGrupo(int idGrupo) throws SQLException {
        return grupoDAOImpl.eliminarGrupo(idGrupo);
    }
}
