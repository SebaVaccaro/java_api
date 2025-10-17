package consola.Admin;

import facade.TeleITRFacade;
import modelo.TeleITR;
import util.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class TeleITRAdminUI {

    private final Scanner scanner = new Scanner(System.in);
    private final TeleITRFacade facade;

    public TeleITRAdminUI() throws SQLException {
        this.facade = new TeleITRFacade();
    }

    public void menuTelefonos() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ TELÉFONOS ITR ---");
            System.out.println("1. Agregar teléfono");
            System.out.println("2. Listar todos");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Modificar teléfono");
            System.out.println("5. Eliminar teléfono");
            System.out.println("0. Volver al menú principal");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> agregarTelefono();
                case 2 -> listarTodos();
                case 3 -> buscarPorId();
                case 4 -> modificarTelefono();
                case 5 -> eliminarTelefono();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void agregarTelefono() {
        String numero = leerTexto("Número de teléfono: ");
        int idItr = leerEntero("ID del ITR: ");

        try {
            boolean exito = facade.agregarTelefono(numero, idItr);
            if (exito) System.out.println("✅ Teléfono agregado.");
            else System.out.println("❌ No se pudo agregar el teléfono.");
        } catch (SQLException e) {
            System.out.println("❌ " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void listarTodos() {
        try {
            List<TeleITR> list = facade.listarTodos();
            if (list.isEmpty()) System.out.println("No hay teléfonos registrados.");
            else list.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("❌ " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void buscarPorId() {
        int id = leerEntero("ID del teléfono: ");
        try {
            TeleITR t = facade.buscarPorId(id);
            if (t != null) System.out.println(t);
            else System.out.println("No se encontró teléfono con ese ID.");
        } catch (SQLException e) {
            System.out.println("❌ " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void modificarTelefono() {
        int id = leerEntero("ID del teléfono a modificar: ");
        try {
            TeleITR t = facade.buscarPorId(id);
            if (t == null) {
                System.out.println("❌ No se encontró teléfono con ese ID.");
                return;
            }

            System.out.println("Campos modificables: numero, idItr");
            String campo = leerTexto("Campo a modificar: ");

            boolean exito = false;
            switch (campo.toLowerCase()) {
                case "numero" -> {
                    String nuevo = leerTexto("Nuevo número: ");
                    t.setNumero(nuevo);
                    exito = facade.actualizarTelefono(t.getIdTelefono(), t.getNumero(), t.getIdItr());
                }
                case "iditr" -> {
                    int nuevo = leerEntero("Nuevo ID ITR: ");
                    t.setIdItr(nuevo);
                    exito = facade.actualizarTelefono(t.getIdTelefono(), t.getNumero(), t.getIdItr());
                }
                default -> System.out.println("Campo inválido.");
            }

            if (exito) System.out.println("✅ Teléfono modificado.");
            else System.out.println("❌ No se pudo modificar el teléfono.");
        } catch (SQLException e) {
            System.out.println("❌ " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void eliminarTelefono() {
        int id = leerEntero("ID del teléfono a eliminar: ");
        try {
            if (facade.eliminarTelefono(id)) System.out.println("✅ Teléfono eliminado.");
            else System.out.println("❌ No se pudo eliminar el teléfono.");
        } catch (SQLException e) {
            System.out.println("❌ " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
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
}
