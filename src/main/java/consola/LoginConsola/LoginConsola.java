package consola.LoginConsola;

import modelo.Usuario;
import FACADE.SesionFacade;
import SINGLETON.SesionSingleton;
import consola.InterfazConsola.UIBase;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;

public class LoginConsola extends UIBase {

    private final SesionFacade sesionFacade;
    private final SesionSingleton sesion;

    public LoginConsola() {
        this.sesionFacade = new SesionFacade();
        this.sesion = SesionSingleton.getInstance();
    }

    @Override
    public void iniciar() {

        System.out.println("=======================================");
        System.out.println("        SISTEMA EDUCATIVO - LOGIN      ");
        System.out.println("=======================================\n");

        while (sesion.getUsuarioActual() == null) {

            String username = leerTexto("Usuario: ");
            String password = leerTexto("Contraseña: ");

            try {
                sesionFacade.login(username, password);

                Usuario usuario = sesion.getUsuarioActual();
                String rol = sesion.getRolActual();

                if (usuario != null && rol != null) {
                    mostrarInfo("\nInicio de sesión exitoso.");
                    System.out.println("---------------------------------------");
                    System.out.println("Usuario actual: " + usuario.getNombre() + " " + usuario.getApellido());
                    System.out.println("Correo: " + usuario.getCorreo());
                    System.out.println("Rol: " + rol);
                    System.out.println("---------------------------------------\n");
                } else {
                    mostrarError("Error inesperado: no se estableció la sesión.");
                }

            } catch (SQLException e) {
                mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
            } catch (Exception e) {
                mostrarError("Error inesperado: " + e.getMessage());
            }
        }
    }

    @Override
    protected void mostrarMenu() {
    }

    @Override
    protected void manejarOpcion(int opcion) {
    }
}
