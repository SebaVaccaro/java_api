package consola.Strategy;

import consola.interfaz.RolStrategy;

import java.util.HashMap;
import java.util.Map;

public class RolStrategyFactory {

    private static final Map<String, RolStrategy> estrategias = new HashMap<>();

    static {
        estrategias.put("ESTUDIANTE", new EstudianteStrategy());
        estrategias.put("ADMINISTRADOR", new AdminStrategy());
        estrategias.put("PSICOPEDAGOGO", new PsicopedagogoStrategy());
        estrategias.put("ANALISTA EDUCATIVO", new AnalistaStrategy());
        estrategias.put("RESPONSABLE EDUCATIVO", new FuncionarioStrategy());
        estrategias.put("ÁREA LEGAL", new FuncionarioStrategy());
    }

    public static RolStrategy obtenerEstrategia(String rol) {
        if (rol == null) return null;
        return estrategias.get(rol.toUpperCase());
    }
}