package consola.LoginConsola;

import modelo.Usuario;
import servicios.LoginServicio;
import SINGLETON.LoginSingleton;
import consola.InterfazConsola.UIBase;

// Clase que gestiona el inicio de sesión desde la consola
public class LoginConsola extends UIBase {

    // Servicio encargado de validar credenciales
    private final LoginServicio loginServicio;

    // Singleton que mantiene la sesión activa del usuario
    private final LoginSingleton sesion;

    // Constructor que inicializa el servicio de login y la sesión
    public LoginConsola() {
        this.loginServicio = new LoginServicio();
        this.sesion = LoginSingleton.getInstance();
    }

    // Inicia el proceso de inicio de sesión en la consola
    @Override
    public void iniciar() {

        System.out.println("=======================================");
        System.out.println(" SISTEMA EDUCATIVO - LOGIN");
        System.out.println("=======================================\n");

        // Bucle hasta que el usuario y el rol sean válidos
        while (sesion.getUsuarioActual() == null && sesion.getRolActual() == null) {
            String username = leerTexto("Usuario: ");
            String password = leerTexto("Contraseña: ");

            try {
                // Se intenta iniciar sesión con las credenciales ingresadas
                loginServicio.login(username, password);

                // Verifica si el inicio de sesión fue exitoso
                if (sesion.getUsuarioActual() != null && sesion.getRolActual() != null) {
                    Usuario usuario = sesion.getUsuarioActual();

                    mostrarInfo("\n✅ Inicio de sesión exitoso.");
                    System.out.println("---------------------------------------");
                    System.out.println("Usuario actual: " + usuario.getNombre() + " " + usuario.getApellido());
                    System.out.println("Correo: " + usuario.getCorreo());
                    System.out.println("Rol: " + sesion.getRolActual());
                    System.out.println("---------------------------------------\n");
                } else {
                    mostrarError("No se pudo iniciar sesión (usuario no válido o no aceptó políticas).");
                }

            } catch (Exception e) {
                // Captura de errores en el proceso de login
                mostrarError("Error de inicio de sesión: " + e.getMessage());
                mostrarInfo("Por favor, inténtelo nuevamente.\n");
            }
        }
    }

    // No se usa en login, pero debe implementarse por la clase base
    @Override
    protected void mostrarMenu() {
    }

    // No se usa en login, pero debe implementarse por la clase base
    @Override
    protected void manejarOpcion(int opcion) {
    }
}
