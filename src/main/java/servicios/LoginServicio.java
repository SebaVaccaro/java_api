package servicios;

import algoritmos.Encriptador;
import modelo.Usuario;
import modelo.Estudiante;
import modelo.Funcionario;
import DAO.LoginDAOImpl;
import SINGLETON.LoginSingleton;

import java.sql.SQLException;

public class LoginServicio {

    private final LoginDAOImpl loginDAOImpl;

    public LoginServicio() {
        try {
            this.loginDAOImpl = new LoginDAOImpl();
        } catch (SQLException e) {
            throw new RuntimeException("Error al inicializar LoginDAO", e);
        }
    }

    public void login(String username, String password) throws Exception {
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
            EstudianteServicio estService = new EstudianteServicio();
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
                        estService.actualizarEstudiante(est);
                        System.out.println("¡Políticas aceptadas! Bienvenido.");
                        break;
                    } else if (opcion.equals("NO")) {
                        System.out.println("No puedes acceder al sistema sin aceptar las políticas.");
                    } else {
                        System.out.println("Opción inválida. Debes ingresar SI o NO.");
                    }
                }
            }

            LoginSingleton sesion = LoginSingleton.getInstance();
            sesion.setUsuarioActual(est, "estudiante");

            // ===================== FUNCIONARIO =====================
        } else if (correo.endsWith("@utec.edu.uy")) {
            FuncionarioServicio funcService = new FuncionarioServicio();
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
                        break;
                    } else if (opcion.equals("NO")) {
                        System.out.println("No puedes acceder al sistema sin aceptar las políticas.");
                    } else {
                        System.out.println("Opción inválida. Debes ingresar SI o NO.");
                    }
                }
            }

            RolServicio rolServicio = new RolServicio();
            String nombreRol;
            try {
                nombreRol = rolServicio.buscarPorId(func.getIdRol()).getNombre();
            } catch (SQLException e) {
                throw new RuntimeException("Error al obtener el rol del funcionario", e);
            }

            LoginSingleton sesion = LoginSingleton.getInstance();
            sesion.setUsuarioActual(func, nombreRol);


        } else {
            throw new IllegalArgumentException("No se encontró un tipo de usuario válido para el correo: " + correo);
        }
    }
}
