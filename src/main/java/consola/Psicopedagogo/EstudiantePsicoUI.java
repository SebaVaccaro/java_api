package consola.Psicopedagogo;

import facade.EstudianteFacade;
import modelo.Estudiante;
import util.CapturadoraDeErrores;

import java.sql.SQLException;
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
            System.out.println("\n--- MENÚ ESTUDIANTES ---");
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

    private void crearEstudiante() {
        String cedula = leerTexto("Cédula: ");
        String nombre = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        String username = leerTexto("Username: ");
        String password = leerTexto("Password: ");
        String correo = leerTexto("Correo: ");
        int idGrupo = leerEntero("ID de grupo: ");
        boolean estActivo = true;

        try {
            Estudiante e = facade.crearEstudiante(cedula, nombre, apellido, username, password, correo, idGrupo, estActivo);
            System.out.println("✅ Estudiante creado: " + e);
        } catch (SQLException ex) {
            // ✅ Capturamos mensaje amigable
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(ex);
            System.out.println("❌ Error al crear estudiante: " + msg);
        }
    }

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

    private void modificarEstudiante() {
        int id = leerEntero("ID del estudiante a modificar: ");
        String cedula = leerTexto("Nueva cédula: ");
        String nombre = leerTexto("Nuevo nombre: ");
        String apellido = leerTexto("Nuevo apellido: ");
        String username = leerTexto("Nuevo username: ");
        String password = leerTexto("Nuevo password: ");
        String correo = leerTexto("Nuevo correo: ");
        int idGrupo = leerEntero("Nuevo ID de grupo: ");
        boolean estActivo = leerBoolean("Activo (true/false): ");

        try {
            boolean exito = facade.actualizarEstudiante(id, cedula, nombre, apellido, username, password, correo, idGrupo, estActivo);
            if (exito) System.out.println("✅ Estudiante modificado.");
            else System.out.println("❌ No se pudo modificar el estudiante.");
        } catch (SQLException ex) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(ex);
            System.out.println("❌ Error al modificar estudiante: " + msg);
        }
    }

    private void desactivarEstudiante() {
        int id = leerEntero("ID del estudiante a desactivar: ");
        try {
            boolean exito = facade.desactivarEstudiante(id);
            if (exito) System.out.println("✅ Estudiante desactivado.");
            else System.out.println("❌ No se pudo desactivar el estudiante.");
        } catch (SQLException ex) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(ex);
            System.out.println("❌ Error al desactivar estudiante: " + msg);
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

    private boolean leerBoolean(String mensaje) {
        System.out.print(mensaje);
        while (true) {
            String texto = scanner.nextLine().trim().toLowerCase();
            if (texto.equals("true") || texto.equals("t") || texto.equals("si") || texto.equals("s")) return true;
            if (texto.equals("false") || texto.equals("f") || texto.equals("no") || texto.equals("n")) return false;
            System.out.print("Ingrese true/false: ");
        }
    }
}
