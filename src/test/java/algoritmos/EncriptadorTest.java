package algoritmos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EncriptadorTest {

    //TEST encriptar solo números
    @Test
    void testEncriptarSoloNumeros() {
        String r = Encriptador.encriptar("012");
        assertEquals("@!~^%&#*(", r);
    }

    //TEST encriptar solo letras
    @Test
    void testEncriptarSoloLetras() {
        String r = Encriptador.encriptar("abc");
        assertEquals("/?.1.?/%7%7%~c~", r);
    }

    //TEST encriptar mezcla
    @Test
    void testEncriptarMixto() {
        String r = Encriptador.encriptar("a1z9");
        assertEquals("/?.1.?/^%&{z}()_", r);
    }

    //TEST desencriptar solo números
    @Test
    void testDesencriptarNumeros() {
        String r = Encriptador.desencriptar("@!~^%&#*(");
        assertEquals("012", r);
    }

    //TEST desencriptar solo letras
    @Test
    void testDesencriptarLetras() {
        String r = Encriptador.desencriptar("/?.1.?/%7%7%~c~");
        assertEquals("abc", r);
    }

    //TEST desencriptar mezcla
    @Test
    void testDesencriptarMixto() {
        String r = Encriptador.desencriptar("/?.1.?/^%&{z}()_");
        assertEquals("a1z9", r);
    }

    //TEST encriptar y desencriptar (idempotencia)
    @Test
    void testEncriptarYDesencriptar() {
        String original = "hola123";
        String enc = Encriptador.encriptar(original);
        String dec = Encriptador.desencriptar(enc);
        assertEquals(original, dec);
    }

    //TEST texto vacío
    @Test
    void testTextoVacio() {
        assertEquals("", Encriptador.encriptar(""));
        assertEquals("", Encriptador.desencriptar(""));
    }

    //TEST ningún reemplazo (caracteres no contemplados)
    @Test
    void testCaracteresSinReemplazo() {
        String original = "!?$%&/";
        String enc = Encriptador.encriptar(original);
        assertEquals(original, enc);

        String dec = Encriptador.desencriptar(original);
        assertEquals(original, dec);
    }
}
