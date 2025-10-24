package consola.Psicopedagogo;

import facade.EstudianteFacade;
import modelo.Estudiante;
import util.CapturadoraDeErrores;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class EstudiantePsicoUI {
    private final Scanner scanner = new Scanner(System.in);
    private final EstudianteFacade facade;

    public EstudiantePsicoUI() throws SQLException {
        this.facade = new EstudianteFacade();
    }

    public void menuEstudiantes() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ ESTUDIANTES (PSICOPEDAGOGO) ---");
            System.out.println("1. Listar todos");
            System.out.println("2. Buscar por ID");
            System.out.println("3. Modificar estudiante");
            System.out.println("4. Desactivar estudiante");
            System.out.println("0. Volver al menú principal");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> listarTodos();
                case 2 -> buscarPorId();
                case 3 -> modificarEstudiante();
                case 4 -> desactivarEstudiante();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    // ============================================================
    // LISTAR ESTUDIANTES
    // ============================================================
    private void listarTodos() {
        try {
            List<Estudiante> lista = facade.listarTodos();
            if (lista.isEmpty()) System.out.println("No hay estudiantes registrados.");
            else lista.forEach(System.out::println);
        } catch (SQLException ex) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(ex);
            System.out.println("❌ Error al listar estudiantes: " + msg);
        }
    }

    // ============================================================
    // BUSCAR ESTUDIANTE
    // ============================================================
    private void buscarPorId() {
        int id = leerEntero("ID del estudiante: ");
        try {
            Estudiante e = facade.obtenerPorId(id);
            if (e != null) System.out.println(e);
            else System.out.println("❌ Estudiante no encontrado.");
        } catch (SQLException ex) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(ex);
            System.out.println("❌ Error al buscar estudiante: " + msg);
        }
    }

    // ============================================================
    // MODIFICAR ESTUDIANTE
    // ============================================================
    private void modificarEstudiante() {
        int id = leerEntero("ID del estudiante a modificar: ");
        String cedula = leerTexto("Nueva cédula: ");
        String nombre = leerTexto("Nuevo nombre: ");
        String apellido = leerTexto("Nuevo apellido: ");
        String username = leerTexto("Nuevo username: ");
        String password = leerTexto("Nuevo password: ");
        int idGrupo = leerEntero("Nuevo ID de grupo: ");
        boolean activo = leerBoolean("¿Activo? (true/false): ");

        try {
            // Creamos el objeto Estudiante con los nuevos datos
            Estudiante e = new Estudiante(id, cedula, nombre, apellido, username, password, null, idGrupo, activo);

            boolean exito = facade.actualizarEstudiante(e);
            if (exito) System.out.println("✅ Estudiante modificado correctamente.");
            else System.out.println("❌ No se pudo modificar el estudiante.");
        } catch (SQLException ex) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(ex);
            System.out.println("❌ Error al modificar estudiante: " + msg);
        }
    }

    // ============================================================
    // DESACTIVAR ESTUDIANTE
    // ============================================================
    private void desactivarEstudiante() {
        int id = leerEntero("ID del estudiante a desactivar: ");
        try {
            boolean exito = facade.desactivarEstudiante(id);
            if (exito) System.out.println("✅ Estudiante desactivado correctamente.");
            else System.out.println("❌ No se pudo desactivar el estudiante.");
        } catch (SQLException ex) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(ex);
            System.out.println("❌ Error al desactivar estudiante: " + msg);
        }
    }

    // ============================================================
    // MÉTODOS AUXILIARES
    // ============================================================
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

    private boolean leerBoolean(String mensaje) {
        System.out.print(mensaje);
        while (true) {
            String texto = scanner.nextLine().trim().toLowerCase();
            if (texto.equals("true") || texto.equals("t") || texto.equals("si") || texto.equals("s")) return true;
            if (texto.equals("false") || texto.equals("f") || texto.equals("no") || texto.equals("n")) return false;
            System.out.print("Ingrese true/false: ");
        }
    }

    private LocalDate leerFecha(String mensaje) {
        System.out.print(mensaje);
        while (true) {
            try {
                String input = scanner.nextLine();
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.print("Formato inválido. Ingrese fecha en formato YYYY-MM-DD: ");
            }
        }
    }
}
