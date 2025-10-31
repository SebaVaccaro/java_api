package algoritmos;

import java.time.LocalDate;
import java.time.Period;

public class ValidadorEdad {

    // Verificar si una persona es mayor de 18 años a partir de su fecha de nacimiento
    public static boolean esMayorDe18(LocalDate fechaNacimiento) {
        // Calcular la diferencia entre la fecha de nacimiento y la fecha actual
        Period edad = Period.between(fechaNacimiento, LocalDate.now());

        // Retornar true si los años completos son 18 o más, false en caso contrario
        return edad.getYears() >= 18;
    }
}

