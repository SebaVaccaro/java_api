package algoritmos;

import java.time.LocalDate;
import java.time.Period;

public class ValidadorEdad {
    public static boolean esMayorDe18(LocalDate fechaNacimiento) {
        Period edad = Period.between(fechaNacimiento, LocalDate.now());
        return edad.getYears() >= 18;
    }
}

