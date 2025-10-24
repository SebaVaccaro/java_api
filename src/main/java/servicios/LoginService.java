package servicios;

import algoritmos.Encriptador;
import modelo.Usuario;
import modelo.Estudiante;
import modelo.Funcionario;
import DAO.LoginDAOImpl;

import java.sql.SQLException;

public class LoginService {

    private final LoginDAOImpl loginDAOImpl;

    public LoginService() {
        try {
            this.loginDAOImpl = new LoginDAOImpl();
        } catch (SQLException e) {
            throw new RuntimeException("Error al inicializar LoginDAO", e);
        }
    }

    public Usuario login(String username, String password) throws Exception {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Username y password no pueden estar vacíos");
        }

        Usuario usuario = loginDAOImpl.obtenerUsuarioPorUsername(username);

        if (usuario == null) {
            throw new IllegalArgumentException("Usuario o contraseña incorrectos");
        }

        String pass = Encriptador.desencriptar(usuario.getPassword());
        if (!pass.equals(password)) {
            throw new IllegalArgumentException("Usuario o contraseña incorrectos.");
        }

        String correo = usuario.getCorreo().toLowerCase();

        // ✅ Corregido: dominio correcto de estudiantes
        if (correo.endsWith("@estudiantes.utec.edu.uy")) {

            EstudianteService estService = new EstudianteService();
            Estudiante est = estService.obtenerPorId(usuario.getIdUsuario());

            if (est == null) {
                throw new IllegalArgumentException("No se encontró el estudiante en la base de datos.");
            }
            return est;

        } else if (correo.endsWith("@utec.edu.uy")) {

            FuncionarioService funcService = new FuncionarioService();
            Funcionario func = funcService.obtenerPorId(usuario.getIdUsuario());

            if (func == null) {
                throw new IllegalArgumentException("No se encontró el funcionario en la base de datos.");
            }

            return func;

        } else {
            throw new IllegalArgumentException("No se encontró un tipo de usuario válido para el correo: " + correo);
        }
    }
}
