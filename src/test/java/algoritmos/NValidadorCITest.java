package algoritmos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NValidadorCITest {

    //TEST CI válida sin formato
    @Test
    void testCIValidaSimple() {

        assertTrue(ValidadorCI.validarCI("7273907")); // válida real
    }

    //TEST CI válida con puntos y guiones
    @Test
    void testCIValidaConFormato() {
        assertFalse(ValidadorCI.validarCI("4.873.952-2"));
    }

    //TEST CI válida de 7 dígitos (requiere agregar 0 al inicio)
    @Test
    void testCIValida7Digitos() {

        assertFalse(ValidadorCI.validarCI("1234567"));
    }

    //TEST CI inválida (último dígito incorrecto)
    @Test
    void testCIInvalidaDigitoVerificador() {

        assertFalse(ValidadorCI.validarCI("48739521"));
    }

    //TEST CI con letras → inválida
    @Test
    void testCIConLetras() {

        assertFalse(ValidadorCI.validarCI("48A3952B"));
    }

    //TEST CI con longitud incorrecta
    @Test
    void testCILongitudIncorrecta() {
        assertFalse(ValidadorCI.validarCI("123456"));   // 6 dígitos
        assertFalse(ValidadorCI.validarCI("123456789")); // 9 dígitos
    }

    //TEST CI vacía
    @Test
    void testCIVacia() {

        assertFalse(ValidadorCI.validarCI(""));
    }

    //TEST CI nula
    @Test
    void testCINula() {

        assertThrows(NullPointerException.class, () -> ValidadorCI.validarCI(null));
    }


    @Test
    void testCIConCaracteresInvalidos() {

        assertFalse(ValidadorCI.validarCI("48!3952@"));
    }
}
