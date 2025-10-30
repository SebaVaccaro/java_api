package consola.Strategy;

import consola.Funcionario.FuncionarioUI;
import consola.interfaz.RolStrategy;

public class FuncionarioStrategy implements RolStrategy {
    @Override
    public void iniciar() {
        new FuncionarioUI().iniciar();
    }
}