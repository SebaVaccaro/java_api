package consola;

import consola.LoginConsola.LoginConsola;
import consola.StrategyConsola.RolStrategyFactoryConsola;
import consola.InterfazConsola.RolStrategy;
import modelo.Usuario;
import SINGLETON.LoginSingleton;

// Clase principal de la consola del sistema
public class MainConsola {

    // Constructor vacío
    public MainConsola() {}

    //Controla el flujo general del sistema en modo consola
    public void iniciar() {
        LoginConsola loginConsola = new LoginConsola(); // instancia del módulo de login

        while (true) {
            // Inicia el proceso de inicio de sesión
            loginConsola.iniciar();

            // Obtiene la sesión actual desde el Singleton
            LoginSingleton login = LoginSingleton.getInstance();
            Usuario usuario = login.getUsuarioActual();
            String rol = login.getRolActual();

            // Verifica si hay un usuario válido con rol asignado
            if (usuario == null || rol == null || rol.isEmpty()) {
                System.out.println("🔒 No hay usuario autenticado o rol no definido. Finalizando programa.");
                return;
            }

            // Mensaje de bienvenida
            System.out.println("\n👋 Bienvenido, " + usuario.getNombre() + "!");

            // Obtiene la estrategia correspondiente al rol
            RolStrategy estrategia = RolStrategyFactoryConsola.obtenerEstrategia(rol);

            // Ejecuta la interfaz del rol si existe
            if (estrategia != null) {
                estrategia.iniciar();
            } else {
                System.out.println("⚠️ Rol no reconocido. Acceso denegado.");
            }

            // Cierra la sesión actual y regresa al login
            login.cerrarSesion();
            System.out.println("\n🔁 Sesión cerrada. Volviendo al login...\n");
        }
    }
}
