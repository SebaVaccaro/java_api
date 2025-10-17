package consola.Admin;

import facade.ArchivoAdjuntoFacade;
import modelo.ArchivoAdjunto;
import util.CapturadoraDeErrores; // ✅ Importamos la clase de captura de errores

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ArchivoAdjuntoAdminUI {

    private final Scanner scanner = new Scanner(System.in);
    private final ArchivoAdjuntoFacade facade;

    public ArchivoAdjuntoAdminUI() throws SQLException {
        this.facade = new ArchivoAdjuntoFacade();
    }

    public void menuArchivos() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ ARCHIVOS ADJUNTOS ---");
            System.out.println("1. Crear archivo");
            System.out.println("2. Listar todos");
            System.out.println("3. Listar activos");
            System.out.println("4. Listar por estudiante");
            System.out.println("5. Modificar archivo");
            System.out.println("6. Eliminar archivo");
            System.out.println("0. Volver al menú principal");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> crearArchivo();
                case 2 -> listarTodos();
                case 3 -> listarActivos();
                case 4 -> listarPorEstudiante();
                case 5 -> modificarArchivo();
                case 6 -> eliminarArchivo();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void crearArchivo() {
        int idUsuario = leerEntero("ID Usuario: ");
        int idEstudiante = leerEntero("ID Estudiante: ");
        String ruta = leerTexto("Ruta del archivo: ");
        String categoria = leerTexto("Categoría: ");
        try {
            ArchivoAdjunto archivo = facade.crearArchivo(idUsuario, idEstudiante, ruta, categoria);
            System.out.println("✅ Archivo creado: " + archivo);
        } catch (SQLException e) {
            // ✅ Uso de la capturadora
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al crear archivo: " + msg);
        }
    }

    private void listarTodos() {
        try {
            List<ArchivoAdjunto> list = facade.listarActivos(); // O cambiar a listarTodos() si existe
            if (list.isEmpty()) System.out.println("No hay archivos registrados.");
            else list.forEach(System.out::println);
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al listar archivos: " + msg);
        }
    }

    private void listarActivos() {
        try {
            List<ArchivoAdjunto> list = facade.listarActivos();
            if (list.isEmpty()) System.out.println("No hay archivos activos.");
            else list.forEach(System.out::println);
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al listar activos: " + msg);
        }
    }

    private void listarPorEstudiante() {
        int idEstudiante = leerEntero("ID Estudiante: ");
        try {
            List<ArchivoAdjunto> list = facade.listarPorEstudiante(idEstudiante);
            if (list.isEmpty()) System.out.println("No hay archivos para este estudiante.");
            else list.forEach(System.out::println);
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al listar archivos por estudiante: " + msg);
        }
    }

    private void modificarArchivo() {
        int idArchivo = leerEntero("ID del archivo a modificar: ");
        System.out.println("Campos modificables: ruta, categoria, idUsuario, idEstudiante");
        String campo = leerTexto("Campo a modificar: ");

        try {
            boolean exito = false;
            switch (campo.toLowerCase()) {
                case "ruta" -> {
                    String nuevaRuta = leerTexto("Nueva ruta: ");
                    ArchivoAdjunto a = facade.obtenerPorId(idArchivo);
                    a.setRuta(nuevaRuta);
                    exito = facade.actualizarArchivo(a);
                }
                case "categoria" -> {
                    String nuevaCat = leerTexto("Nueva categoría: ");
                    ArchivoAdjunto a = facade.obtenerPorId(idArchivo);
                    a.setCategoria(nuevaCat);
                    exito = facade.actualizarArchivo(a);
                }
                case "idusuario" -> {
                    int nuevoId = leerEntero("Nuevo ID Usuario: ");
                    ArchivoAdjunto a = facade.obtenerPorId(idArchivo);
                    a.setIdUsuario(nuevoId);
                    exito = facade.actualizarArchivo(a);
                }
                case "idestudiante" -> {
                    int nuevoId = leerEntero("Nuevo ID Estudiante: ");
                    ArchivoAdjunto a = facade.obtenerPorId(idArchivo);
                    a.setIdEstudiante(nuevoId);
                    exito = facade.actualizarArchivo(a);
                }
                default -> System.out.println("Campo inválido.");
            }

            if (exito) System.out.println("✅ Archivo modificado.");
            else System.out.println("❌ No se pudo modificar el archivo.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al modificar archivo: " + msg);
        }
    }

    private void eliminarArchivo() {
        int idArchivo = leerEntero("ID del archivo a eliminar: ");
        try {
            if (facade.eliminar(idArchivo)) System.out.println("✅ Archivo eliminado.");
            else System.out.println("❌ No se pudo eliminar el archivo.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al eliminar archivo: " + msg);
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
