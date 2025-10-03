package main.java.algoritmos;

public class ValidadorCI {
    public static boolean validarCI(String ci) {
        // limpiar puntos y guiones
        ci = ci.replace(".", "").replace("-", "");
        if (!ci.matches("\\d{7,8}")) return false;

        // completar con cero a la izquierda si tiene 7 dígitos
        if (ci.length() == 7) {
            ci = "0" + ci;
        }

        int[] coef = {2,9,8,7,6,3,4};
        int suma = 0;
        for (int i = 0; i < 7; i++) {
            int dig = Character.getNumericValue(ci.charAt(i));
            suma += dig * coef[i];
        }
        int resto = suma % 10;
        int verificador = (10 - resto) % 10;

        return verificador == Character.getNumericValue(ci.charAt(7));
    }
}

