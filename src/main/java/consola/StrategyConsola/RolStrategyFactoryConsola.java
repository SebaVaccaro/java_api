package consola.StrategyConsola;

import consola.InterfazConsola.RolStrategy;

import java.util.HashMap;
import java.util.Map;

public class RolStrategyFactoryConsola {

    private static final Map<String, RolStrategy> estrategias = new HashMap<>();

    static {
        estrategias.put("ESTUDIANTE", new EstudianteStrategyConsola());
        estrategias.put("ADMINISTRADOR", new FuncionarioStrategyConsola());
        estrategias.put("PSICOPEDAGOGO", new FuncionarioStrategyConsola());
        estrategias.put("ANALISTA EDUCATIVO", new FuncionarioStrategyConsola());
        estrategias.put("RESPONSABLE EDUCATIVO", new FuncionarioStrategyConsola());
        estrategias.put("ÁREA LEGAL", new FuncionarioStrategyConsola());
    }

    public static RolStrategy obtenerEstrategia(String rol) {
        if (rol == null) return null;
        return estrategias.get(rol.toUpperCase());
    }
}