package consola.StrategyConsola;

import consola.EstudianteConsola.EstudianteConsola;
import consola.InterfazConsola.RolStrategy;

// Estrategia de consola para el rol de Estudiante
public class EstudianteStrategyConsola implements RolStrategy {

    // Inicia la interfaz de consola específica para estudiantes
    @Override
    public void iniciar() {
        new EstudianteConsola().iniciar();
    }
}
