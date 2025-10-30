package consola.Strategy;

import consola.Admin.AdminUI;
import consola.interfaz.RolStrategy;


public class AdminStrategy implements RolStrategy {
    @Override
    public void iniciar() {
        new AdminUI().iniciar();
    }
}
