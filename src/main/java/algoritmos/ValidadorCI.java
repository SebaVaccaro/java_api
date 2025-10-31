package algoritmos;

public class ValidadorCI {

    // Validar un número de cédula de identidad (CI) uruguaya
    public static boolean validarCI(String ci) {
        // Limpiar puntos y guiones del CI
        ci = ci.replace(".", "").replace("-", "");

        // Verificar que solo tenga 7 u 8 dígitos
        if (!ci.matches("\\d{7,8}")) return false;

        // Si tiene 7 dígitos, completar con un cero a la izquierda
        if (ci.length() == 7) {
            ci = "0" + ci;
        }

        // Coeficientes para el cálculo del dígito verificador
        int[] coef = {2, 9, 8, 7, 6, 3, 4};
        int suma = 0;

        // Multiplicar cada dígito por su coeficiente y sumar
        for (int i = 0; i < 7; i++) {
            int dig = Character.getNumericValue(ci.charAt(i));
            suma += dig * coef[i];
        }

        // Calcular el resto de la suma módulo 10
        int resto = suma % 10;

        // Determinar el dígito verificador
        int verificador = (10 - resto) % 10;

        // Comparar el dígito verificador calculado con el último dígito del CI
        return verificador == Character.getNumericValue(ci.charAt(7));
    }
}


