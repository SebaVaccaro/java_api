package consola.InterfazConsola;

// Interfaz base para las estrategias de rol en la consola
// Cada rol (funcionario, tutor, estudiante, etc.) implementará su propio comportamiento
public interface RolStrategy {

    // Inicia la ejecución de la consola según el rol del usuario
    void iniciar();
}
