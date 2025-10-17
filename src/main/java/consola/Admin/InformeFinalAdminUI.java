package consola.Admin;

import facade.InformeFinalFacade;
import modelo.InformeFinal;
import util.CapturadoraDeErrores; // ✅ Importación para manejo de errores amigables

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class InformeFinalAdminUI {

    private final Scanner scanner = new Scanner(System.in);
    private final InformeFinalFacade facade;

    public InformeFinalAdminUI() throws SQLException {
        this.facade = new InformeFinalFacade();
    }

    public void menuInformes() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ INFORMES FINALES ---");
            System.out.println("1. Crear informe");
            System.out.println("2. Listar todos");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Modificar informe");
            System.out.println("5. Eliminar informe");
            System.out.println("0. Volver al menú principal");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> crearInforme();
                case 2 -> listarTodos();
                case 3 -> buscarPorId();
                case 4 -> modificarInforme();
                case 5 -> eliminarInforme();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void crearInforme() {
        String contenido = leerTexto("Contenido: ");
        int valoracion = leerEntero("Valoración (0-100): ");
        LocalDate fecha = leerFecha("Fecha de creación (YYYY-MM-DD): ");

        try {
            InformeFinal i = facade.crearInforme(contenido, valoracion, fecha);
            System.out.println("✅ Informe creado: " + i);
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e); // ✅
            System.out.println("❌ Error al crear informe: " + msg);
        }
    }

    private void listarTodos() {
        try {
            List<InformeFinal> lista = facade.listarInformes();
            if (lista.isEmpty()) System.out.println("No hay informes registrados.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e); // ✅
            System.out.println("❌ Error al listar informes: " + msg);
        }
    }

    private void buscarPorId() {
        int id = leerEntero("ID del informe: ");
        try {
            InformeFinal i = facade.obtenerInforme(id);
            if (i != null) System.out.println(i);
            else System.out.println("❌ Informe no encontrado.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e); // ✅
            System.out.println("❌ Error al buscar informe: " + msg);
        }
    }

    private void modificarInforme() {
        int id = leerEntero("ID del informe a modificar: ");
        String contenido = leerTexto("Nuevo contenido: ");
        int valoracion = leerEntero("Nueva valoración (0-100): ");
        LocalDate fecha = leerFecha("Nueva fecha de creación (YYYY-MM-DD): ");

        try {
            boolean exito = facade.actualizarInforme(id, contenido, valoracion, fecha);
            if (exito) System.out.println("✅ Informe modificado.");
            else System.out.println("❌ No se pudo modificar el informe.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e); // ✅
            System.out.println("❌ Error al modificar informe: " + msg);
        }
    }

    private void eliminarInforme() {
        int id = leerEntero("ID del informe a eliminar: ");
        try {
            boolean exito = facade.eliminarInforme(id);
            if (exito) System.out.println("✅ Informe eliminado.");
            else System.out.println("❌ No se pudo eliminar el informe.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e); // ✅
            System.out.println("❌ Error al eliminar informe: " + msg);
        }
    }

    // ==== Métodos auxiliares ====
    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("Ingrese un número válido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // limpiar buffer
        return valor;
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private LocalDate leerFecha(String mensaje) {
        System.out.print(mensaje);
        while (true) {
            try {
                String input = scanner.nextLine();
                return LocalDate.parse(input);
            } catch (Exception e) {
                System.out.print("Formato incorrecto. Ingrese fecha (YYYY-MM-DD): ");
            }
        }
    }
}
