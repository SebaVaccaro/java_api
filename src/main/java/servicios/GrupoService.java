package servicios;

import DAO.GrupoDAO;
import modelo.Grupo;

import java.sql.SQLException;
import java.util.List;

public class GrupoService {

    private final GrupoDAO grupoDAO;

    public GrupoService() throws SQLException {
        this.grupoDAO = new GrupoDAO();
    }

    // 🔹 Crear nuevo grupo
    public Grupo crearGrupo(String nomGrupo, int idCarrera) throws SQLException {
        Grupo g = new Grupo(nomGrupo, idCarrera);
        return grupoDAO.crearGrupo(g);
    }

    // 🔹 Obtener grupo por ID
    public Grupo obtenerPorId(int idGrupo) throws SQLException {
        return grupoDAO.obtenerGrupo(idGrupo);
    }

    // 🔹 Listar todos los grupos
    public List<Grupo> listarTodos() throws SQLException {
        return grupoDAO.listarGrupos();
    }

    // 🔹 Listar grupos por carrera
    public List<Grupo> listarPorCarrera(int idCarrera) throws SQLException {
        return grupoDAO.listarPorCarrera(idCarrera);
    }

    // 🔹 Actualizar grupo
    public boolean actualizarGrupo(int idGrupo, String nomGrupo, int idCarrera) throws SQLException {
        Grupo g = new Grupo(idGrupo, nomGrupo, idCarrera);
        return grupoDAO.actualizarGrupo(g);
    }

    // 🔹 Eliminar grupo
    public boolean eliminarGrupo(int idGrupo) throws SQLException {
        return grupoDAO.eliminarGrupo(idGrupo);
    }
}
