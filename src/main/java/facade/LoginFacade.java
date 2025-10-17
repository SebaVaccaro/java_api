package facade;

import modelo.Usuario;
import servicios.LoginService;


public class LoginFacade {

    private final LoginService loginService;

    public LoginFacade() {
        this.loginService = new LoginService();
    }


    public Usuario autenticarUsuario(String username, String password) throws Exception {
        return loginService.login(username, password);
    }

}
