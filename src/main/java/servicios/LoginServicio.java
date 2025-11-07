package servicios;

import algoritmos.Encriptador;
import modelo.Usuario;
import DAO.LoginDAOImpl;

public class LoginServicio {

    private final LoginDAOImpl loginDAO;

    public LoginServicio() {
        try {
            this.loginDAO = new LoginDAOImpl();
        } catch (Exception e) {
            throw new RuntimeException("Error al inicializar LoginDAO", e);
        }
    }

    public Usuario autenticar(String username, String password) throws Exception {

        if (username == null || username.isEmpty() ||
                password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Usuario y contraseña no pueden estar vacíos.");
        }

        Usuario usuario = loginDAO.obtenerUsuarioPorUsername(username);

        if (usuario == null) {
            throw new IllegalArgumentException("Usuario o contraseña incorrectos.");
        }

        String passDesencriptado = Encriptador.desencriptar(usuario.getPassword());

        if (!passDesencriptado.equals(password)) {
            throw new IllegalArgumentException("Usuario o contraseña incorrectos.");
        }

        return usuario;
    }
}
