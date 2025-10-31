package consola.LoginConsola;

import modelo.Usuario;
import servicios.LoginServicio;
import SINGLETON.LoginSingleton;
import consola.InterfazConsola.UIBase;

public class LoginConsola extends UIBase {

    private final LoginServicio loginServicio;
    private final LoginSingleton sesion;

    public LoginConsola() {
        this.loginServicio = new LoginServicio();
        this.sesion = LoginSingleton.getInstance();
    }

    @Override
    public void iniciar() {

        System.out.println("=======================================");
        System.out.println(" SISTEMA EDUCATIVO - LOGIN");
        System.out.println("=======================================\n");

        while (sesion.getUsuarioActual() == null && sesion.getRolActual() == null) {
            String username = leerTexto("Usuario: ");
            String password = leerTexto("Contraseña: ");

            try {
                loginServicio.login(username, password);

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
                mostrarError("Error de inicio de sesión: " + e.getMessage());
                mostrarInfo("Por favor, inténtelo nuevamente.\n");
            }
        }
    }

    @Override
    protected void mostrarMenu() {
        // No se usa en login, pero se debe implementar
    }

    @Override
    protected void manejarOpcion(int opcion) {
        // No se usa en login, pero se debe implementar
    }
}
