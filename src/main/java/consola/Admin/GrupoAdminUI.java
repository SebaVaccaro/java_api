package consola.Admin;

import facade.GrupoFacade;
import modelo.Grupo;
import util.CapturadoraDeErrores; // ✅ Importar para manejo de errores amigables

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class GrupoAdminUI {

    private final Scanner scanner = new Scanner(System.in);
    private final GrupoFacade facade;

    public GrupoAdminUI() throws SQLException {
        this.facade = new GrupoFacade();
    }

    public void menuGrupos() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ GRUPOS ---");
            System.out.println("1. Crear grupo");
            System.out.println("2. Listar todos");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Listar por carrera");
            System.out.println("5. Modificar grupo");
            System.out.println("6. Eliminar grupo");
            System.out.println("0. Volver al menú principal");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> crearGrupo();
                case 2 -> listarTodos();
                case 3 -> buscarPorId();
                case 4 -> listarPorCarrera();
                case 5 -> modificarGrupo();
                case 6 -> eliminarGrupo();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void crearGrupo() {
        String nombre = leerTexto("Nombre del grupo: ");
        int idCarrera = leerEntero("ID de la carrera: ");
        try {
            Grupo g = facade.crearGrupo(nombre, idCarrera);
            System.out.println("✅ Grupo creado: " + g);
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e); // ✅
            System.out.println("❌ Error al crear grupo: " + msg);
        }
    }

    private void listarTodos() {
        try {
            List<Grupo> lista = facade.listarTodos();
            if (lista.isEmpty()) System.out.println("No hay grupos registrados.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e); // ✅
            System.out.println("❌ Error al listar grupos: " + msg);
        }
    }

    private void buscarPorId() {
        int id = leerEntero("ID del grupo: ");
        try {
            Grupo g = facade.obtenerPorId(id);
            if (g != null) System.out.println(g);
            else System.out.println("❌ Grupo no encontrado.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e); // ✅
            System.out.println("❌ Error al buscar grupo: " + msg);
        }
    }

    private void listarPorCarrera() {
        int idCarrera = leerEntero("ID de la carrera: ");
        try {
            List<Grupo> lista = facade.listarPorCarrera(idCarrera);
            if (lista.isEmpty()) System.out.println("No hay grupos para esta carrera.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e); // ✅
            System.out.println("❌ Error al listar grupos por carrera: " + msg);
        }
    }

    private void modificarGrupo() {
        int id = leerEntero("ID del grupo a modificar: ");
        String nombre = leerTexto("Nuevo nombre: ");
        int idCarrera = leerEntero("Nuevo ID de carrera: ");
        try {
            boolean exito = facade.actualizarGrupo(id, nombre, idCarrera);
            if (exito) System.out.println("✅ Grupo modificado.");
            else System.out.println("❌ No se pudo modificar el grupo.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e); // ✅
            System.out.println("❌ Error al modificar grupo: " + msg);
        }
    }

    private void eliminarGrupo() {
        int id = leerEntero("ID del grupo a eliminar: ");
        try {
            boolean exito = facade.eliminarGrupo(id);
            if (exito) System.out.println("✅ Grupo eliminado.");
            else System.out.println("❌ No se pudo eliminar el grupo.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e); // ✅
            System.out.println("❌ Error al eliminar grupo: " + msg);
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
