package SINGLETON;

import modelo.Usuario;

public class SesionSingleton {

    // Instancia única de la clase (patrón Singleton)
    private static SesionSingleton instancia;

    // Usuario actualmente logueado
    private Usuario usuarioActual;

    // Rol del usuario actualmente logueado
    private String rol;

    // Constructor privado para evitar instanciación externa
    private SesionSingleton() {}

    // Obtener la instancia única de LoginSingleton (sincronizado para seguridad en multihilo)
    public static synchronized SesionSingleton getInstance() {
        if (instancia == null) {
            instancia = new SesionSingleton();
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
