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

        // ===================== ESTUDIANTE =====================
        if (correo.endsWith("@estudiantes.utec.edu.uy")) {
            EstudianteService estService = new EstudianteService();
            Estudiante est = estService.obtenerPorId(usuario.getIdUsuario());

            if (est == null) {
                throw new IllegalArgumentException("No se encontró el estudiante en la base de datos.");
            }

            if (!est.isActivo()) {
                java.util.Scanner sc = new java.util.Scanner(System.in);
                String opcion;
                System.out.println("Debes aceptar las políticas de UTEC para continuar.");

                while (true) {
                    System.out.print("¿Aceptas las políticas? (SI/NO): ");
                    opcion = sc.nextLine().trim().toUpperCase();

                    if (opcion.equals("SI")) {
                        est.setActivo(true);
                        estService.actualizarEstudiante(est); // ✅ actualizar objeto estudiante
                        System.out.println("¡Políticas aceptadas! Bienvenido.");
                        break; // rompe el bucle y continúa
                    } else if (opcion.equals("NO")) {
                        System.out.println("No puedes acceder al sistema sin aceptar las políticas.");
                        return null; // retorna null para volver a LoginUI
                    } else {
                        System.out.println("Opción inválida. Debes ingresar SI o NO.");
                    }
                }
            }

            return est;

            // ===================== FUNCIONARIO =====================
        } else if (correo.endsWith("@utec.edu.uy")) {
            FuncionarioService funcService = new FuncionarioService();
            Funcionario func = funcService.obtenerPorId(usuario.getIdUsuario());

            if (func == null) {
                throw new IllegalArgumentException("No se encontró el funcionario en la base de datos.");
            }

            if (!func.isActivo()) {
                java.util.Scanner sc = new java.util.Scanner(System.in);
                String opcion;
                System.out.println("Debes aceptar las políticas de UTEC para continuar.");

                while (true) {
                    System.out.print("¿Aceptas las políticas? (SI/NO): ");
                    opcion = sc.nextLine().trim().toUpperCase();

                    if (opcion.equals("SI")) {
                        func.setActivo(true);

                        // Actualizamos con todos los parámetros según actualizarFuncionario
                        funcService.actualizarFuncionario(
                                func.getIdUsuario(),
                                func.getCedula(),
                                func.getNombre(),
                                func.getApellido(),
                                func.getUsername(),
                                Encriptador.desencriptar(func.getPassword()),
                                func.getCorreo(),
                                func.getIdRol(),
                                func.isActivo()
                        );

                        System.out.println("¡Políticas aceptadas! Bienvenido.");
                        break; // rompe el bucle y continúa
                    } else if (opcion.equals("NO")) {
                        System.out.println("No puedes acceder al sistema sin aceptar las políticas.");
                        return null; // retorna null para volver a LoginUI
                    } else {
                        System.out.println("Opción inválida. Debes ingresar SI o NO.");
                    }
                }
            }

            return func;

        } else {
            throw new IllegalArgumentException("No se encontró un tipo de usuario válido para el correo: " + correo);
        }
    }
}
