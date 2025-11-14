package consola.InterfazConsola;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Scanner;

// Clase base para todas las interfaces de consola
// Proporciona métodos comunes de lectura y manejo de menús
public abstract class UIBase implements UIMenu {

    // Scanner para leer datos desde la consola
    protected final Scanner scanner = new Scanner(System.in);

    // Métodos abstractos que deben implementar las subclases
    protected abstract void mostrarMenu();
    protected abstract void manejarOpcion(int opcion);

    @Override
    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEnteroNoNull("Seleccione una opción: ");
            manejarOpcion(opcion);
        } while (opcion != 0);
        System.out.println("Saliendo del menú...");
    }

    // Lee un número entero del usuario
    protected Integer leerEntero(String mensaje) {
        System.out.print(mensaje);
        String entrada = scanner.nextLine().trim();

        while (true) {
            if (!entrada.isEmpty()) {
                try {
                    return Integer.parseInt(entrada);
                } catch (NumberFormatException e) {
                    System.out.print("Ingrese un número válido: ");
                }
            } else {
                System.out.print("El valor no puede estar vacío. Intente nuevamente: ");
            }

            entrada = scanner.nextLine().trim();
        }
    }
    protected int leerEnteroNoNull(String mensaje) {
        int valor;
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();

            if (entrada.isEmpty()) {
                System.out.println("El valor no puede estar vacío. Intente de nuevo.");
                continue;
            }

            try {
                valor = Integer.parseInt(entrada);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        }
        return valor;
    }

    // Lee texto ingresado por el usuario
    protected String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    // Leer texto obligatorio
    protected String leerTextoNoNull(String mensaje) {
        String input;
        do {
            System.out.print(mensaje);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Este campo no puede estar vacío. Intente de nuevo.");
            }
        } while (input.isEmpty());
        return input;
    }

    protected String leerTextoNoNullNoNumero(String mensaje) {
        String input;
        do {
            System.out.print(mensaje);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Este campo no puede estar vacío. Intente de nuevo.");
                continue;
            }
            // Verifica si es solo un número
            if (input.matches("\\d+")) {
                System.out.println("El texto no puede ser solo números. Intente de nuevo.");
                continue;
            }
            break;
        } while (true);
        return input;
    }

    protected String leerNombreApellido(String mensaje) {
        String input;
        do {
            System.out.print(mensaje);
            input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Este campo no puede estar vacío. Intente de nuevo.");
                continue;
            }

            if (!input.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                System.out.println("El nombre o apellido solo puede contener letras. Intente de nuevo.");
                continue;
            }

            input = input.replaceAll(" ", "").toLowerCase();

            break;
        } while (true);

        return input;
    }


    // Lee un valor booleano (true/false)
    protected boolean leerBoolean(String mensaje) {
        System.out.print(mensaje);
        while (true) {
            String texto = scanner.nextLine().trim();
            if (texto.equalsIgnoreCase("true") || texto.equalsIgnoreCase("t") || texto.equalsIgnoreCase("si") || texto.equalsIgnoreCase("s"))
                return true;
            if (texto.equalsIgnoreCase("false") || texto.equalsIgnoreCase("f") || texto.equalsIgnoreCase("no") || texto.equalsIgnoreCase("n"))
                return false;
            System.out.print("Ingrese true/false: ");
        }
    }

    // Lee una fecha y hora en formato ISO (YYYY-MM-DDTHH:MM)
    protected OffsetDateTime leerFechaHora(String mensaje) {
        System.out.print(mensaje);
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return OffsetDateTime.parse(input + ":00Z");
            } catch (Exception e) {
                System.out.print("Formato inválido. Use YYYY-MM-DDTHH:MM : ");
            }
        }
    }

    // Lee una fecha simple (YYYY-MM-DD)
    protected LocalDate leerFecha(String mensaje) {
        System.out.print(mensaje);
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return LocalDate.parse(input);
            } catch (Exception e) {
                System.out.print("Formato inválido. Ingrese fecha (YYYY-MM-DD): ");
            }
        }
    }

    // Lee texto opcional (si está vacío, mantiene el valor actual)
    protected String leerTexto(String mensaje, String valorActual) {
        System.out.print(mensaje);
        String input = scanner.nextLine();
        return input.isBlank() ? valorActual : input;
    }

    // Lee entero opcional
    protected Integer leerEntero(String mensaje, Integer valorActual) {
        System.out.print(mensaje);
        String input = scanner.nextLine();
        if (input.isBlank()) return valorActual; // conserva null si valorActual es null
        try {
            return Integer.valueOf(input.trim());
        } catch (NumberFormatException e) {
            return valorActual;
        }
    }

    // Lee boolean opcional
    protected boolean leerBoolean(String mensaje, boolean valorActual) {
        System.out.print(mensaje);
        String input = scanner.nextLine().trim();
        if (input.isBlank()) return valorActual;
        if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("t") || input.equalsIgnoreCase("si") || input.equalsIgnoreCase("s"))
            return true;
        if (input.equalsIgnoreCase("false") || input.equalsIgnoreCase("f") || input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n"))
            return false;
        return valorActual;
    }

    // Lee fecha opcional
    protected LocalDate leerFecha(String mensaje, LocalDate valorActual) {
        System.out.print(mensaje);
        String input = scanner.nextLine().trim();
        if (input.isBlank()) return valorActual;
        try {
            return LocalDate.parse(input);
        } catch (Exception e) {
            return valorActual;
        }
    }

    // Lee fecha y hora opcional
    protected OffsetDateTime leerFechaHora(String mensaje, OffsetDateTime valorActual) {
        System.out.print(mensaje);
        String input = scanner.nextLine().trim();
        if (input.isBlank()) return valorActual;
        try {
            return OffsetDateTime.parse(input + ":00Z");
        } catch (Exception e) {
            return valorActual;
        }
    }

    // Muestra un mensaje de éxito
    protected void mostrarExito(String mensaje) {
        System.out.println(mensaje);
    }

    // Muestra un mensaje de error
    protected void mostrarError(String mensaje) {
        System.out.println(mensaje);
    }

    // Muestra un mensaje informativo
    protected void mostrarInfo(String mensaje) {
        System.out.println(mensaje);
    }
}
