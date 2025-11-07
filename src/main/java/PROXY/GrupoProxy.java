package PROXY;

import modelo.Grupo;
import servicios.GrupoServicio;
import utils.ValidarUsuario;

import java.sql.SQLException;
import java.util.List;

public class GrupoProxy {

    private final GrupoServicio grupoServicio;
    private final ValidarUsuario validarUsuario;

    // Constructor: inicializa los servicios de grupo, funcionario y rol
    public GrupoProxy() throws SQLException {
        this.grupoServicio = new GrupoServicio();
        this.validarUsuario = new ValidarUsuario();
    }


    // Crear grupo (solo administradores)
    public Grupo crearGrupo(String nomGrupo, int idCarrera) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("No tiene permiso para ver este archivo.");
        }
        return grupoServicio.crearGrupo(nomGrupo, idCarrera);
    }

    // Obtener grupo por ID (Solo administradores o psicopedagogos)
    public Grupo obtenerPorId(int idGrupo) throws SQLException {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("Solo administradores o psicopedagogos pueden obtener grupos.");
        }
        return grupoServicio.obtenerPorId(idGrupo);
    }

    // Listar todos los grupos (Solo administradores o psicopedagogos)
    public List<Grupo> listarTodos() throws SQLException {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("Solo administradores o psicopedagogos pueden obtener grupos.");
        }
        return grupoServicio.listarTodos();
    }

    // Listar grupos de una carrera específica (Solo administradores o psicopedagogos)
    public List<Grupo> listarPorCarrera(int idCarrera) throws SQLException {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("Solo administradores o psicopedagogos pueden obtener grupos.");
        }
        return grupoServicio.listarPorCarrera(idCarrera);
    }

    // Actualizar grupo (solo administradores)
    public boolean actualizarGrupo(int idGrupo, String nomGrupo, int idCarrera) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo administradores pueden actualizar grupos.");
        }
        return grupoServicio.actualizarGrupo(idGrupo, nomGrupo, idCarrera);
    }

    // Eliminar grupo (solo administradores)
    public boolean eliminarGrupo(int idGrupo) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo un administrador puede eliminar físicamente grupos.");
        }
        return grupoServicio.eliminarGrupo(idGrupo);
    }
}

