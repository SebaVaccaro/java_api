package algoritmos;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ValidadorEdadTest {

    //TEST mayor de 18 años
    @Test
    void testMayorDe18() {
        LocalDate fecha = LocalDate.now().minusYears(20);
        assertTrue(ValidadorEdad.esMayorDe18(fecha));
    }

    //TEST exactamente 18 años
    @Test
    void testExactamente18() {
        LocalDate fecha = LocalDate.now().minusYears(18);
        assertTrue(ValidadorEdad.esMayorDe18(fecha));
    }

    //TEST menor de 18 años
    @Test
    void testMenorDe18() {
        LocalDate fecha = LocalDate.now().minusYears(17);
        assertFalse(ValidadorEdad.esMayorDe18(fecha));
    }

    //TEST un día antes de cumplir 18 (debe dar false)
    @Test
    void testUnDiaMenos() {
        LocalDate fecha = LocalDate.now().minusYears(18).plusDays(1);
        assertFalse(ValidadorEdad.esMayorDe18(fecha));
    }

    //TEST un día después de cumplir 18 (debe dar true)
    @Test
    void testUnDiaDespues() {
        LocalDate fecha = LocalDate.now().minusYears(18).minusDays(1);
        assertTrue(ValidadorEdad.esMayorDe18(fecha));
    }

    //TEST fecha futura (persona que aún no nació) → false
    @Test
    void testFechaFutura() {
        LocalDate fecha = LocalDate.now().plusYears(1);
        assertFalse(ValidadorEdad.esMayorDe18(fecha));
    }

}
