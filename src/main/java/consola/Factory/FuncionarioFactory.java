package consola.Factory;

import consola.InterfazConsola.UIMenu;
import consola.FuncionarioConsola.*;

public class FuncionarioFactory {

    public static UIMenu crearConsolaPorOpcion(int opcion) throws Exception{
        return switch (opcion) {
            case 1  -> new ArchivoAdjuntoConsola();
            case 2  -> new CarreraConsola();
            case 3  -> new CiudadConsola();
            case 4  -> new DireccionConsola();
            case 5  -> new EstudianteConsola();
            case 6  -> new FuncionarioConsola();
            case 7  -> new GrupoConsola();
            case 8  -> new ITRConsola();
            case 9  -> new IncidenciaConsola();
            case 10 -> new InstanciaComunConsola();
            case 11 -> new NotificacionConsola();
            case 12 -> new ObservacionConsola();
            case 13 -> new RolConsola();
            case 14 -> new SeguimientoConsola();
            case 15 -> new TeleUsuarioConsola();
            case 16 -> new PartSeguimientoConsola();
            case 17 -> new PerteneceConsola();
            case 18 -> new RecibeConsola();
            case 19 -> new TeleITRConsola();
            case 20 -> new PartInstanciaConsola();
            case 21 -> new InformeFinalConsola();
            default -> throw new IllegalArgumentException("Opción inválida: " + opcion);
        };
    }
}
