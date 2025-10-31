package consola.StrategyConsola;

import consola.InterfazConsola.RolStrategy;
import java.util.HashMap;
import java.util.Map;

// Fábrica que devuelve la estrategia de consola correspondiente a cada rol
public class RolStrategyFactoryConsola {

    // Mapa que asocia el nombre del rol con su estrategia de consola
    private static final Map<String, RolStrategy> estrategias = new HashMap<>();

    // Inicialización estática de las estrategias disponibles
    static {
        estrategias.put("ESTUDIANTE", new EstudianteStrategyConsola());
        estrategias.put("ADMINISTRADOR", new FuncionarioStrategyConsola());
        estrategias.put("PSICOPEDAGOGO", new FuncionarioStrategyConsola());
        estrategias.put("ANALISTA EDUCATIVO", new FuncionarioStrategyConsola());
        estrategias.put("RESPONSABLE EDUCATIVO", new FuncionarioStrategyConsola());
        estrategias.put("ÁREA LEGAL", new FuncionarioStrategyConsola());
    }

    // Retorna la estrategia correspondiente según el rol recibido
    public static RolStrategy obtenerEstrategia(String rol) {
        if (rol == null) return null;
        return estrategias.get(rol.toUpperCase());
    }
}
