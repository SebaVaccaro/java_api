package consola.Strategy;

import consola.Psicopedagogo.PsicopedagogoUI;
import consola.interfaz.RolStrategy;

public class PsicopedagogoStrategy implements RolStrategy {
    @Override
    public void iniciar() {
        new PsicopedagogoUI().iniciar();
    }
}
