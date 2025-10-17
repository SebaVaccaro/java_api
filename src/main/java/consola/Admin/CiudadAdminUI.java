package consola.Admin;

import facade.CiudadFacade;
import modelo.Ciudad;
import util.CapturadoraDeErrores; // ✅ Importar la clase de manejo de errores

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CiudadAdminUI {

    private final Scanner scanner = new Scanner(System.in);
    private final CiudadFacade facade;

    public CiudadAdminUI() throws SQLException {
        this.facade = new CiudadFacade();
    }

    public void menuCiudades() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ CIUDADES ---");
            System.out.println("1. Crear ciudad");
            System.out.println("2. Listar todas");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Buscar por nombre");
            System.out.println("5. Listar por departamento");
            System.out.println("6. Modificar ciudad");
            System.out.println("7. Eliminar ciudad");
            System.out.println("0. Volver al menú principal");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> crearCiudad();
                case 2 -> listarTodas();
                case 3 -> buscarPorId();
                case 4 -> buscarPorNombre();
                case 5 -> listarPorDepartamento();
                case 6 -> modificarCiudad();
                case 7 -> eliminarCiudad();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void crearCiudad() {
        int codPostal = leerEntero("Código postal: ");
        String nombre = leerTexto("Nombre de la ciudad: ");
        String departamento = leerTexto("Departamento: ");
        try {
            Ciudad c = facade.crearCiudad(codPostal, nombre, departamento);
            System.out.println("✅ Ciudad creada: " + c);
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e); // ✅ Uso de Capturadora
            System.out.println("❌ Error al crear ciudad: " + msg);
        }
    }

    private void listarTodas() {
        try {
            List<Ciudad> lista = facade.listarTodas();
            if (lista.isEmpty()) System.out.println("No hay ciudades registradas.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al listar ciudades: " + msg);
        }
    }

    private void buscarPorId() {
        int id = leerEntero("ID de la ciudad: ");
        try {
            Ciudad c = facade.buscarCiudadPorId(id);
            if (c != null) System.out.println(c);
            else System.out.println("❌ Ciudad no encontrada.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al buscar ciudad: " + msg);
        }
    }

    private void buscarPorNombre() {
        String nombre = leerTexto("Nombre de la ciudad: ");
        try {
            Ciudad c = facade.buscarCiudadPorNombre(nombre);
            if (c != null) System.out.println(c);
            else System.out.println("❌ Ciudad no encontrada.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al buscar ciudad: " + msg);
        }
    }

    private void listarPorDepartamento() {
        String departamento = leerTexto("Departamento: ");
        try {
            List<Ciudad> lista = facade.listarPorDepartamento(departamento);
            if (lista.isEmpty()) System.out.println("No hay ciudades en este departamento.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al listar ciudades por departamento: " + msg);
        }
    }

    private void modificarCiudad() {
        int id = leerEntero("ID de la ciudad a modificar: ");
        System.out.println("Campos modificables: codPostal, nombre, departamento");
        String campo = leerTexto("Campo a modificar: ");

        try {
            boolean exito = switch (campo.toLowerCase()) {
                case "codpostal" -> {
                    int codPostal = leerEntero("Nuevo código postal: ");
                    yield facade.actualizarCodPostal(id, codPostal);
                }
                case "nombre" -> {
                    String nombre = leerTexto("Nuevo nombre: ");
                    yield facade.actualizarNombre(id, nombre);
                }
                case "departamento" -> {
                    String dep = leerTexto("Nuevo departamento: ");
                    yield facade.actualizarDepartamento(id, dep);
                }
                default -> {
                    System.out.println("Campo inválido.");
                    yield false;
                }
            };
            if (exito) System.out.println("✅ Ciudad modificada.");
            else System.out.println("❌ No se pudo modificar la ciudad.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al modificar ciudad: " + msg);
        }
    }

    private void eliminarCiudad() {
        int id = leerEntero("ID de la ciudad a eliminar: ");
        try {
            boolean exito = facade.eliminarCiudad(id);
            if (exito) System.out.println("✅ Ciudad eliminada.");
            else System.out.println("❌ No se pudo eliminar la ciudad.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al eliminar ciudad: " + msg);
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
