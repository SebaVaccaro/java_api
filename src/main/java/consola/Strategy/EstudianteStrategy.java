package consola.Strategy;

import consola.Estudiante.EstudianteUI;
import consola.interfaz.RolStrategy;

public class EstudianteStrategy implements RolStrategy {
    @Override
    public void iniciar() {
        new EstudianteUI().iniciar();
    }
}
