package consola.Factory;

import consola.InterfazConsola.UIMenu;
import consola.EstudianteConsola.*;

public class EstudianteFactory {


    public static UIMenu crearConsolaPorOpcion(int opcion) throws Exception {
        return switch (opcion) {
            case 2 -> new SeguimientoConsola();
            case 3 -> new InstanciaComunConsola();
            case 4 -> new TelefonoConsola();
            case 5 -> new NotificacionConsola();
            case 6 -> new DireccionConsola();
            default -> throw new IllegalArgumentException("Opción inválida: " + opcion);
        };
    }
}
