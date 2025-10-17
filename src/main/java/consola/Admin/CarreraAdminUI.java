package consola.Admin;

import facade.CarreraFacade;
import modelo.Carrera;
import util.CapturadoraDeErrores; // ✅ Importamos la clase de manejo de errores

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CarreraAdminUI {

    private final Scanner scanner = new Scanner(System.in);
    private final CarreraFacade facade;

    public CarreraAdminUI() throws SQLException {
        this.facade = new CarreraFacade();
    }

    public void menuCarreras() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ CARRERAS ---");
            System.out.println("1. Crear carrera");
            System.out.println("2. Listar todas");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Buscar por código");
            System.out.println("5. Modificar carrera");
            System.out.println("6. Eliminar carrera");
            System.out.println("0. Volver al menú principal");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> crearCarrera();
                case 2 -> listarTodas();
                case 3 -> buscarPorId();
                case 4 -> buscarPorCodigo();
                case 5 -> modificarCarrera();
                case 6 -> eliminarCarrera();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void crearCarrera() {
        String codigo = leerTexto("Código de la carrera: ");
        String nombre = leerTexto("Nombre de la carrera: ");
        String plan = leerTexto("Plan de estudio: ");
        try {
            Carrera c = facade.crearCarrera(codigo, nombre, plan);
            System.out.println("✅ Carrera creada: " + c);
        } catch (SQLException e) {
            // ✅ Mensaje traducido
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al crear carrera: " + msg);
        }
    }

    private void listarTodas() {
        try {
            List<Carrera> lista = facade.listarTodas();
            if (lista.isEmpty()) System.out.println("No hay carreras registradas.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al listar carreras: " + msg);
        }
    }

    private void buscarPorId() {
        int id = leerEntero("ID de la carrera: ");
        try {
            Carrera c = facade.buscarCarreraPorId(id);
            if (c != null) System.out.println(c);
            else System.out.println("❌ Carrera no encontrada.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al buscar carrera: " + msg);
        }
    }

    private void buscarPorCodigo() {
        String codigo = leerTexto("Código de la carrera: ");
        try {
            Carrera c = facade.buscarCarreraPorCodigo(codigo);
            if (c != null) System.out.println(c);
            else System.out.println("❌ Carrera no encontrada.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al buscar carrera: " + msg);
        }
    }

    private void modificarCarrera() {
        int id = leerEntero("ID de la carrera a modificar: ");
        String codigo = leerTexto("Nuevo código: ");
        String nombre = leerTexto("Nuevo nombre: ");
        String plan = leerTexto("Nuevo plan de estudio: ");
        try {
            boolean exito = facade.actualizarCarrera(id, codigo, nombre, plan);
            if (exito) System.out.println("✅ Carrera modificada.");
            else System.out.println("❌ No se pudo modificar la carrera.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al modificar carrera: " + msg);
        }
    }

    private void eliminarCarrera() {
        int id = leerEntero("ID de la carrera a eliminar: ");
        try {
            boolean exito = facade.eliminarCarrera(id);
            if (exito) System.out.println("✅ Carrera eliminada.");
            else System.out.println("❌ No se pudo eliminar la carrera.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al eliminar carrera: " + msg);
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
