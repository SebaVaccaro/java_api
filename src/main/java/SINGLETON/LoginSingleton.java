package SINGLETON;

import modelo.Usuario;

public class LoginSingleton {

    private static LoginSingleton instancia;
    private Usuario usuarioActual;
    private String rol;

    private LoginSingleton() {}

    public static synchronized LoginSingleton getInstance() {
        if (instancia == null) {
            instancia = new LoginSingleton();
        }
        return instancia;
    }

    public void setUsuarioActual(Usuario usuario, String rol) {
        this.usuarioActual = usuario;
        this.rol = rol;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
    public String getRolActual(){
        return rol;
    }
    public boolean haySesionActiva() {
        return usuarioActual != null;
    }

    public void cerrarSesion() {
        this.usuarioActual = null;
        this.rol = null;
    }
}
