package FACADE;

import modelo.Usuario;
import servicios.LoginServicio;
import servicios.UsuarioServicio;
import SINGLETON.SesionSingleton;

public class SesionFacade {

    private final LoginServicio loginServicio;
    private final UsuarioServicio usuarioServicio;
    private final SesionSingleton sesion;

    public SesionFacade() {
        this.loginServicio = new LoginServicio();
        try {
            this.usuarioServicio = new UsuarioServicio();
        } catch (Exception e) {
            throw new RuntimeException("Error al inicializar UsuarioServicio", e);
        }
        this.sesion = SesionSingleton.getInstance();
    }

    // LOGIN
    public void login(String username, String password) throws Exception {
        Usuario base = loginServicio.autenticar(username, password);
        usuarioServicio.cargarUsuario(base);
    }

    // LOGOUT
    public void logout() {
        sesion.cerrarSesion();
    }
}
