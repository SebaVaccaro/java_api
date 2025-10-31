package consola.StrategyConsola;

import consola.FuncionarioConsola.FuncionarioConsolaMain;
import consola.InterfazConsola.RolStrategy;

// Estrategia de consola para el rol de Funcionario
public class FuncionarioStrategyConsola implements RolStrategy {

    // Inicia la interfaz de consola principal del funcionario
    @Override
    public void iniciar() {
        new FuncionarioConsolaMain().iniciar();
    }
}
