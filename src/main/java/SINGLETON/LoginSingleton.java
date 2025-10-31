package SINGLETON;

import modelo.Usuario;

public class LoginSingleton {

    // Instancia única de la clase (patrón Singleton)
    private static LoginSingleton instancia;

    // Usuario actualmente logueado
    private Usuario usuarioActual;

    // Rol del usuario actualmente logueado
    private String rol;

    // Constructor privado para evitar instanciación externa
    private LoginSingleton() {}

    // Obtener la instancia única de LoginSingleton (sincronizado para seguridad en multihilo)
    public static synchronized LoginSingleton getInstance() {
        if (instancia == null) {
            instancia = new LoginSingleton();
        }
        return instancia;
    }

    // Establecer usuario y rol actual al iniciar sesión
    public void setUsuarioActual(Usuario usuario, String rol) {
        this.usuarioActual = usuario;
        this.rol = rol;
    }

    // Obtener el usuario actualmente logueado
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    // Obtener el rol del usuario actualmente logueado
    public String getRolActual() {
        return rol;
    }

    // Verificar si hay una sesión activa
    public boolean haySesionActiva() {
        return usuarioActual != null;
    }

    // Cerrar sesión: limpiar usuario y rol
    public void cerrarSesion() {
        this.usuarioActual = null;
        this.rol = null;
    }
}
