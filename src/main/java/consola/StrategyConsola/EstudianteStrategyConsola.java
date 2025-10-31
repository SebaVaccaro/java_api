package consola.StrategyConsola;

import consola.EstudianteConsola.EstudianteConsola;
import consola.InterfazConsola.RolStrategy;

public class EstudianteStrategyConsola implements RolStrategy {
    @Override
    public void iniciar() {
        new EstudianteConsola().iniciar();
    }
}
