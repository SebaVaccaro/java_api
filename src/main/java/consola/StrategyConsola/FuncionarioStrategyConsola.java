package consola.StrategyConsola;

import consola.FuncionarioConsola.FuncionarioConsolaMain;
import consola.InterfazConsola.RolStrategy;

public class FuncionarioStrategyConsola implements RolStrategy {
    @Override
    public void iniciar() {
        new FuncionarioConsolaMain().iniciar();
    }
}