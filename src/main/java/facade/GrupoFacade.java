package facade;

import modelo.Grupo;
import servicios.GrupoService;

import java.sql.SQLException;
import java.util.List;

public class GrupoFacade {

    private final GrupoService grupoService;

    public GrupoFacade() throws SQLException {
        this.grupoService = new GrupoService();
    }

    // ============================================================
    // CREAR GRUPO
    // ============================================================
    public Grupo crearGrupo(String nomGrupo, int idCarrera) throws SQLException {
        return grupoService.crearGrupo(nomGrupo, idCarrera);
    }

    // ============================================================
    // OBTENER GRUPO
    // ============================================================
    public Grupo obtenerPorId(int idGrupo) throws SQLException {
        return grupoService.obtenerPorId(idGrupo);
    }

    // ============================================================
    // LISTAR GRUPOS
    // ============================================================
    public List<Grupo> listarTodos() throws SQLException {
        return grupoService.listarTodos();
    }

    public List<Grupo> listarPorCarrera(int idCarrera) throws SQLException {
        return grupoService.listarPorCarrera(idCarrera);
    }

    // ============================================================
    // ACTUALIZAR GRUPO
    // ============================================================
    public boolean actualizarGrupo(int idGrupo, String nomGrupo, int idCarrera) throws SQLException {
        return grupoService.actualizarGrupo(idGrupo, nomGrupo, idCarrera);
    }

    // ============================================================
    // ELIMINAR GRUPO
    // ============================================================
    public boolean eliminarGrupo(int idGrupo) throws SQLException {
        return grupoService.eliminarGrupo(idGrupo);
    }
}
