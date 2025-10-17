package consola.Admin;

import facade.TeleUsuarioFacade;
import modelo.TeleUsuario;
import util.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class TeleUsuarioAdminUI {

    private final Scanner scanner = new Scanner(System.in);
    private final TeleUsuarioFacade facade;

    public TeleUsuarioAdminUI() throws Exception {
        this.facade = new TeleUsuarioFacade();
    }

    public void menuTelefonosUsuario() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ TELÉFONOS DE USUARIO ---");
            System.out.println("1. Agregar teléfono");
            System.out.println("2. Listar todos");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Modificar teléfono");
            System.out.println("5. Eliminar teléfono");
            System.out.println("6. Listar por usuario");
            System.out.println("0. Volver al menú principal");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> agregarTelefono();
                case 2 -> listarTodos();
                case 3 -> buscarPorId();
                case 4 -> modificarTelefono();
                case 5 -> eliminarTelefono();
                case 6 -> listarPorUsuario();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void agregarTelefono() {
        String numero = leerTexto("Número de teléfono: ");
        int idUsuario = leerEntero("ID del usuario: ");

        try {
            TeleUsuario t = facade.crearTelefono(numero, idUsuario);
            if (t != null) System.out.println("✅ Teléfono agregado: " + t);
            else System.out.println("❌ No se pudo agregar el teléfono.");
        } catch (SQLException e) {
            System.out.println("❌ " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void listarTodos() {
        try {
            List<TeleUsuario> list = facade.listarTelefonos();
            if (list == null || list.isEmpty()) System.out.println("No hay teléfonos registrados.");
            else list.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("❌ " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void buscarPorId() {
        int id = leerEntero("ID del teléfono: ");
        try {
            TeleUsuario t = facade.obtenerTelefono(id);
            if (t != null) System.out.println(t);
            else System.out.println("No se encontró teléfono con ese ID.");
        } catch (SQLException e) {
            System.out.println("❌ " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void modificarTelefono() {
        int id = leerEntero("ID del teléfono a modificar: ");
        try {
            TeleUsuario t = facade.obtenerTelefono(id);
            if (t == null) {
                System.out.println("❌ No se encontró teléfono con ese ID.");
                return;
            }

            System.out.println("Campos modificables: numero, idUsuario");
            String campo = leerTexto("Campo a modificar: ");

            boolean exito = false;
            switch (campo.toLowerCase()) {
                case "numero" -> {
                    String nuevo = leerTexto("Nuevo número: ");
                    t.setNumero(nuevo);
                    exito = facade.actualizarTelefono(t.getIdTelefono(), t.getNumero(), t.getIdUsuario());
                }
                case "idusuario" -> {
                    int nuevo = leerEntero("Nuevo ID de usuario: ");
                    t.setIdUsuario(nuevo);
                    exito = facade.actualizarTelefono(t.getIdTelefono(), t.getNumero(), t.getIdUsuario());
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
            boolean exito = facade.eliminarTelefono(id);
            if (exito) System.out.println("✅ Teléfono eliminado.");
            else System.out.println("❌ No se pudo eliminar el teléfono.");
        } catch (SQLException e) {
            System.out.println("❌ " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void listarPorUsuario() {
        int idUsuario = leerEntero("ID del usuario: ");
        try {
            List<TeleUsuario> list = facade.listarTelefonosPorUsuario(idUsuario);
            if (list == null || list.isEmpty()) System.out.println("No hay teléfonos para este usuario.");
            else list.forEach(System.out::println);
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
