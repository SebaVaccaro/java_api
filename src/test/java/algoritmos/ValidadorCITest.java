package algoritmos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidadorCITest {

    private ValidadorCI validadorCI;

    @Test
    public void validarCedulaTrue(){
        boolean result = ValidadorCI.validarCI("47692460");
        assertTrue(result);
    }

    @Test
    public void validarCedulaFalse(){
        boolean result = ValidadorCI.validarCI("41112224");
        assertFalse(result);
    }
}
