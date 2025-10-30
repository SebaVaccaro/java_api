package consola;

import consola.Login.LoginUI;
import consola.Strategy.RolStrategyFactory;
import consola.interfaz.RolStrategy;
import modelo.Usuario;
import SINGLETON.LoginSingleton;


public class MainUI {

    public MainUI() {}

    public void iniciar() {
        LoginUI loginUI = new LoginUI();

        while (true) {

            loginUI.iniciar();

            LoginSingleton login = LoginSingleton.getInstance();
            Usuario usuario = login.getUsuarioActual();
            String rol = login.getRolActual();

            if (usuario == null || rol == null || rol.isEmpty()) {
                System.out.println("🔒 No hay usuario autenticado o rol no definido. Finalizando programa.");
                return;
            }

            System.out.println("\n👋 Bienvenido, " + usuario.getNombre() + "!");

            RolStrategy estrategia = RolStrategyFactory.obtenerEstrategia(rol);

            if (estrategia != null) {
                estrategia.iniciar();
            } else {
                System.out.println("⚠️ Rol no reconocido. Acceso denegado.");
            }

            login.cerrarSesion();
            System.out.println("\n🔁 Sesión cerrada. Volviendo al login...\n");
        }
    }
}
