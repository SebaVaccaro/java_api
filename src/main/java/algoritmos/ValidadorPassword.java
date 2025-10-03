package algoritmos;

public class ValidadorPassword {

    /**
     * Valida que la contraseña tenga al menos 8 caracteres.
     * @param password La contraseña a validar.
     * @return true si cumple, false si no.
     */
    public static boolean validar(String password) {
        if (password == null) return false; // chequeo básico por null
        return password.length() >= 8;
    }
}

