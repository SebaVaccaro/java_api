package utils;

import SINGLETON.LoginSingleton;
import modelo.Usuario;

public class ValidarUsuario {

    // Obtener el rol del usuario actualmente logueado en mayúsculas
    private String getRolActual() {
        String rol = LoginSingleton.getInstance().getRolActual();
        return rol != null ? rol.toUpperCase() : ""; // Retorna cadena vacía si no hay rol
    }

    // Obtener el usuario actualmente logueado
    private Usuario getUsuarioSesion() {
        return LoginSingleton.getInstance().getUsuarioActual();
    }

    // Verificar si el usuario actual es Administrador
    public boolean esAdministrador() {
        return "ADMINISTRADOR".equalsIgnoreCase(getRolActual());
    }

    // Verificar si el usuario actual es Psicopedagogo
    public boolean esPsicopedagogo() {
        return "PSICOPEDAGOGO".equalsIgnoreCase(getRolActual());
    }

    // Verificar si el usuario actual es Estudiante
    public boolean esEstudiante() {
        return "ESTUDIANTE".equalsIgnoreCase(getRolActual());
    }

    // Verificar si el usuario actual es Tutor
    public boolean esTutor() {
        return "TUTOR".equalsIgnoreCase(getRolActual());
    }

    // Verificar si el usuario tiene permisos de Administrador, Psicopedagogo o es propietario del recurso
    public boolean tienePermisoAdminPsicoOPropietario(int idUsuarioPropietario) {
        Usuario usuarioSesion = getUsuarioSesion();
        if (usuarioSesion == null) return false; // No hay sesión activa, no hay permisos

        // Permiso concedido si es Administrador o Psicopedagogo
        if (esAdministrador() || esPsicopedagogo()) return true;

        // Permiso concedido si el usuario es el propietario del recurso
        return usuarioSesion.getIdUsuario() == idUsuarioPropietario;
    }

    // Verificar si el usuario tiene permisos de Administrador o Psicopedagogo
    public boolean tienePermisoAdminPsico(int idUsuarioSolicitante) {
        return esAdministrador() || esPsicopedagogo();
    }

    // Verificar si el usuario actual es propietario de un recurso específico
    public boolean esPropietario(int idUsuarioPropietario) {
        Usuario usuarioSesion = getUsuarioSesion();
        return usuarioSesion != null && usuarioSesion.getIdUsuario() == idUsuarioPropietario;
    }

    // Verificar si hay una sesión activa
    public boolean haySesionActiva() {
        return LoginSingleton.getInstance().haySesionActiva();
    }
}
