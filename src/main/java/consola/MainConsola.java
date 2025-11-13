package consola;

import consola.LoginConsola.LoginConsola;
import consola.StrategyConsola.RolStrategyFactoryConsola;
import consola.InterfazConsola.RolStrategy;
import modelo.Usuario;
import SINGLETON.SesionSingleton;

public class MainConsola {

    public MainConsola() {}

    public void iniciar() {
        LoginConsola loginConsola = new LoginConsola(); // instancia del módulo de login

        while (true) {
            loginConsola.iniciar();

            SesionSingleton login = SesionSingleton.getInstance();
            Usuario usuario = login.getUsuarioActual();
            String rol = login.getRolActual();

            if (usuario == null || rol == null || rol.isEmpty()) {
                System.out.println("No hay usuario autenticado o rol no definido. Finalizando programa.");
                return;
            }


            RolStrategy estrategia = RolStrategyFactoryConsola.obtenerEstrategia(rol);

            if (estrategia != null) {
                estrategia.iniciar();
            } else {
                System.out.println("Rol no reconocido. Acceso denegado.");
            }

            login.cerrarSesion();
            System.out.println("\nSesión cerrada. Volviendo al login...\n");
        }
    }
}
