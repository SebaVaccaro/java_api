package consola.Login;

import facade.LoginFacade;
import modelo.Usuario;
import java.util.Scanner;

public class LoginUI {

    private final LoginFacade loginFacade;
    private final Scanner scanner;

    // 🔑 Constructor: inicializa el facade de login y el scanner de consola
    public LoginUI() {
        this.loginFacade = new LoginFacade();
        this.scanner = new Scanner(System.in);
    }

    /**
     * 🌟 Muestra el formulario de login por consola
     *
     * @return el objeto Usuario cuando el login es exitoso
     */
    public Usuario iniciar() {
        // ╔═══════════════════════════════════════╗
        // ║           ENCABEZADO LOGIN            ║
        // ╚═══════════════════════════════════════╝
        System.out.println("=======================================");
        System.out.println("      SISTEMA EDUCATIVO - LOGIN");
        System.out.println("=======================================\n");

        // 🔁 Bucle hasta que el usuario se autentique correctamente
        while (true) {
            // 📥 Solicitar credenciales al usuario
            System.out.print("Usuario: ");
            String username = scanner.nextLine().trim();

            System.out.print("Contraseña: ");
            String password = scanner.nextLine().trim();

            try {
                Usuario usuario = loginFacade.autenticarUsuario(username, password);
                if(usuario != null) {
                    System.out.println("\n✅ Inicio de sesión exitoso.");
                    System.out.println("Bienvenido, " + usuario.getNombre() + "!\n");
                    return usuario;
                }
            } catch (Exception e) {
                System.out.println("\n❌ Error de inicio de sesión: " + e.getMessage());
                System.out.println("Por favor, inténtelo nuevamente.\n");
            }
        }
    }
}