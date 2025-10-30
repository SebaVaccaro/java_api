package consola.Strategy;

import consola.Analista.AnalistaUI;
import consola.interfaz.RolStrategy;

public class AnalistaStrategy implements RolStrategy {
    @Override
    public void iniciar() {
        new AnalistaUI().iniciar();
    }
}