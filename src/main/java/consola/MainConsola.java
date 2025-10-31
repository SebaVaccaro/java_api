package consola;

import consola.LoginConsola.LoginConsola;
import consola.StrategyConsola.RolStrategyFactoryConsola;
import consola.InterfazConsola.RolStrategy;
import modelo.Usuario;
import SINGLETON.LoginSingleton;


public class MainConsola {

    public MainConsola() {}

    public void iniciar() {
        LoginConsola loginConsola = new LoginConsola();

        while (true) {

            loginConsola.iniciar();

            LoginSingleton login = LoginSingleton.getInstance();
            Usuario usuario = login.getUsuarioActual();
            String rol = login.getRolActual();

            if (usuario == null || rol == null || rol.isEmpty()) {
                System.out.println("🔒 No hay usuario autenticado o rol no definido. Finalizando programa.");
                return;
            }

            System.out.println("\n👋 Bienvenido, " + usuario.getNombre() + "!");

            RolStrategy estrategia = RolStrategyFactoryConsola.obtenerEstrategia(rol);

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
