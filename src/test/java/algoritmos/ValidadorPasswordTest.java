package algoritmos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidadorPasswordTest {

    //TEST password válido (8 caracteres)
    @Test
    void testPasswordValido() {
        assertTrue(ValidadorPassword.validar("12345678"));
    }

    //TEST password muy largo
    @Test
    void testPasswordLargo() {
        assertTrue(ValidadorPassword.validar("password_super_seguro"));
    }

    //TEST password inválido (menos de 8 caracteres)
    @Test
    void testPasswordCorto() {
        assertFalse(ValidadorPassword.validar("abc123"));
    }

    //TEST password exactamente 8 caracteres
    @Test
    void testPasswordExacto8() {
        assertTrue(ValidadorPassword.validar("abcd1234"));
    }

    //TEST password vacío
    @Test
    void testPasswordVacio() {
        assertFalse(ValidadorPassword.validar(""));
    }

    //TEST password nulo
    @Test
    void testPasswordNull() {
        assertFalse(ValidadorPassword.validar(null));
    }
}
