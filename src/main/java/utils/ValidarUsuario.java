package utils;

import SINGLETON.LoginSingleton;
import modelo.Usuario;

public class ValidarUsuario {

    private String getRolActual() {
        String rol = LoginSingleton.getInstance().getRolActual();
        return rol != null ? rol.toUpperCase() : "";
    }

    private Usuario getUsuarioSesion() {
        return LoginSingleton.getInstance().getUsuarioActual();
    }

    public boolean esAdministrador() {
        return "ADMINISTRADOR".equalsIgnoreCase(getRolActual());
    }

    public boolean esPsicopedagogo() {
        return "PSICOPEDAGOGO".equalsIgnoreCase(getRolActual());
    }

    public boolean esEstudiante() {
        return "ESTUDIANTE".equalsIgnoreCase(getRolActual());
    }

    public boolean esTutor() {
        return "TUTOR".equalsIgnoreCase(getRolActual());
    }


    public boolean tienePermisoAdminPsicoOPropietario(int idUsuarioPropietario) {
        Usuario usuarioSesion = getUsuarioSesion();
        if (usuarioSesion == null) return false;


        if (esAdministrador() || esPsicopedagogo()) return true;


        return usuarioSesion.getIdUsuario() == idUsuarioPropietario;
    }

    public boolean tienePermisoAdminPsico(int idUsuarioSolicitante) {

        return esAdministrador() || esPsicopedagogo();
    }


    public boolean esPropietario(int idUsuarioPropietario) {
        Usuario usuarioSesion = getUsuarioSesion();
        return usuarioSesion != null && usuarioSesion.getIdUsuario() == idUsuarioPropietario;
    }


    public boolean haySesionActiva() {
        return LoginSingleton.getInstance().haySesionActiva();
    }
}

