package algoritmos;

public class Encriptador {

    // Encriptar un texto completo reemplazando números y letras por símbolos específicos
    public static String encriptar(String texto) {
        return texto
                // Primero reemplazo de números por combinaciones de símbolos
                .replace("0", "@!~")
                .replace("1", "^%&")
                .replace("2", "#*(")
                .replace("3", "+=$")
                .replace("4", "?/>")
                .replace("5", "[]:")
                .replace("6", "{}|")
                .replace("7", "<>;")
                .replace("8", "~`*")
                .replace("9", "()_")
                // Luego reemplazo de letras minúsculas por cadenas simbólicas únicas
                .replace("a", "/?.1.?/")
                .replace("b", "%7%7%")
                .replace("c", "~c~")
                .replace("d", "#d#")
                .replace("e", "\\#2#\\")
                .replace("f", "!f!")
                .replace("g", "*g*")
                .replace("h", "+h+")
                .replace("i", "!?3!?")
                .replace("j", "^j^")
                .replace("k", "&k&")
                .replace("l", "-l-")
                .replace("m", "=m=")
                .replace("n", "_n_")
                .replace("o", "*4*4*")
                .replace("p", "%p%")
                .replace("q", "?q?")
                .replace("r", ":r:")
                .replace("s", "$/6/$/")
                .replace("t", "+8+8+")
                .replace("u", "&5&5&")
                .replace("v", "/v/")
                .replace("w", "|w|")
                .replace("x", "<x>")
                .replace("y", ">y<")
                .replace("z", "{z}");
    }

    // Desencriptar un texto previamente encriptado
    public static String desencriptar(String texto) {
        return texto
                // Primero revertir números a su valor original
                .replace("@!~", "0")
                .replace("^%&", "1")
                .replace("#*(", "2")
                .replace("+=$", "3")
                .replace("?/>", "4")
                .replace("[]:", "5")
                .replace("{}|", "6")
                .replace("<>;", "7")
                .replace("~`*", "8")
                .replace("()_", "9")
                // Luego revertir letras a su valor original
                .replace("/?.1.?/", "a")
                .replace("%7%7%", "b")
                .replace("~c~", "c")
                .replace("#d#", "d")
                .replace("\\#2#\\", "e")
                .replace("!f!", "f")
                .replace("*g*", "g")
                .replace("+h+", "h")
                .replace("!?3!?", "i")
                .replace("^j^", "j")
                .replace("&k&", "k")
                .replace("-l-", "l")
                .replace("=m=", "m")
                .replace("_n_", "n")
                .replace("*4*4*", "o")
                .replace("%p%", "p")
                .replace("?q?", "q")
                .replace(":r:", "r")
                .replace("$/6/$/", "s")
                .replace("+8+8+", "t")
                .replace("&5&5&", "u")
                .replace("/v/", "v")
                .replace("|w|", "w")
                .replace("<x>", "x")
                .replace(">y<", "y")
                .replace("{z}", "z");
    }

}

