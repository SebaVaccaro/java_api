package algoritmos;

public class ValidadorPassword {

    // Validar un password según criterios mínimos de seguridad
    public static boolean validar(String password) {
        if (password == null) return false; // Verificar que no sea null para evitar errores
        // Verificar que tenga al menos 8 caracteres de longitud
        return password.length() >= 8;
    }
}
