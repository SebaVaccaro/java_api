package PROXY;

import SINGLETON.LoginSingleton;
import modelo.Funcionario;
import modelo.Rol;
import modelo.Usuario;
import modelo.Grupo;
import servicios.GrupoServicio;
import servicios.FuncionarioServicio;
import servicios.RolServicio;

import java.sql.SQLException;
import java.util.List;

public class GrupoProxy {

    private final GrupoServicio grupoServicio;
    private final FuncionarioServicio funcionarioServicio;
    private final RolServicio rolServicio;

    // Constructor: inicializa los servicios de grupo, funcionario y rol
    public GrupoProxy() throws SQLException {
        this.grupoServicio = new GrupoServicio();
        this.funcionarioServicio = new FuncionarioServicio();
        this.rolServicio = new RolServicio();
    }

    // Método auxiliar: verifica que el usuario actual sea administrador antes de realizar una acción
    private void verificarPermisosAdministrador(String accion) throws Exception {
        Usuario usuarioSesion = LoginSingleton.getInstance().getUsuarioActual();

        if (usuarioSesion == null) {
            throw new SecurityException("Debe iniciar sesión para " + accion + ".");
        }

        if (!(usuarioSesion instanceof Funcionario)) {
            throw new SecurityException("Solo los funcionarios pueden " + accion + ".");
        }

        Funcionario ejecutor = (Funcionario) usuarioSesion;
        Rol rolEjecutor = rolServicio.buscarPorId(ejecutor.getIdRol());

        if (rolEjecutor == null) {
            throw new Exception("El usuario no tiene un rol asignado.");
        }

        if (!rolEjecutor.getNombre().equalsIgnoreCase("ADMINISTRADOR")) {
            throw new SecurityException("Solo los administradores pueden " + accion + ".");
        }
    }

    // Crear grupo (solo administradores)
    public Grupo crearGrupo(String nomGrupo, int idCarrera) throws Exception {
        verificarPermisosAdministrador("crear grupos");
        return grupoServicio.crearGrupo(nomGrupo, idCarrera);
    }

    // Obtener grupo por ID (sin restricción de permisos)
    public Grupo obtenerPorId(int idGrupo) throws SQLException {
        return grupoServicio.obtenerPorId(idGrupo);
    }

    // Listar todos los grupos (sin restricción de permisos)
    public List<Grupo> listarTodos() throws SQLException {
        return grupoServicio.listarTodos();
    }

    // Listar grupos de una carrera específica (sin restricción de permisos)
    public List<Grupo> listarPorCarrera(int idCarrera) throws SQLException {
        return grupoServicio.listarPorCarrera(idCarrera);
    }

    // Actualizar grupo (solo administradores)
    public boolean actualizarGrupo(int idGrupo, String nomGrupo, int idCarrera) throws Exception {
        verificarPermisosAdministrador("modificar grupos");
        return grupoServicio.actualizarGrupo(idGrupo, nomGrupo, idCarrera);
    }

    // Eliminar grupo (solo administradores)
    public boolean eliminarGrupo(int idGrupo) throws Exception {
        verificarPermisosAdministrador("eliminar grupos");
        return grupoServicio.eliminarGrupo(idGrupo);
    }
}

