package consola.interfaz;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Scanner;

public abstract class UIBase implements UIMenu {

    protected final Scanner scanner = new Scanner(System.in);

    protected abstract void mostrarMenu();
    protected abstract void manejarOpcion(int opcion);

    @Override
    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");
            manejarOpcion(opcion);
        } while (opcion != 0);
        System.out.println("🔒 Saliendo del menú...");
    }

    // ==== MÉTODOS DE LECTURA OBLIGATORIOS ====
    protected int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("Ingrese un número válido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

    protected String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

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

    // ==== MÉTODOS DE LECTURA OPCIONALES (para actualizaciones) ====
    protected String leerTexto(String mensaje, String valorActual) {
        System.out.print(mensaje);
        String input = scanner.nextLine();
        return input.isBlank() ? valorActual : input;
    }

    protected int leerEntero(String mensaje, int valorActual) {
        System.out.print(mensaje);
        String input = scanner.nextLine();
        if (input.isBlank()) return valorActual;
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return valorActual;
        }
    }

    protected boolean leerBoolean(String mensaje, boolean valorActual) {
        System.out.print(mensaje);
        String input = scanner.nextLine().trim();
        if (input.isBlank()) return valorActual;
        if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("t") || input.equalsIgnoreCase("si") || input.equalsIgnoreCase("s"))
            return true;
        if (input.equalsIgnoreCase("false") || input.equalsIgnoreCase("f") || input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n"))
            return false;
        return valorActual; // si es inválido, devuelve el valor actual
    }

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

    // ==== MÉTODOS DE MENSAJES ====
    protected void mostrarExito(String mensaje) {
        System.out.println("✅ " + mensaje);
    }

    protected void mostrarError(String mensaje) {
        System.out.println("❌ " + mensaje);
    }

    protected void mostrarInfo(String mensaje) {
        System.out.println("ℹ️ " + mensaje);
    }
}

