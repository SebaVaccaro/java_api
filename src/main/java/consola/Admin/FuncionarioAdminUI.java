package consola.Admin;

import facade.FuncionarioFacade;
import modelo.Funcionario;
import util.CapturadoraDeErrores;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class FuncionarioAdminUI {

    private final Scanner scanner = new Scanner(System.in);
    private final FuncionarioFacade facade;

    public FuncionarioAdminUI() throws SQLException {
        this.facade = new FuncionarioFacade();
    }

    public void menuFuncionarios() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ FUNCIONARIOS ---");
            System.out.println("1. Crear funcionario");
            System.out.println("2. Listar todos");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Modificar funcionario");
            System.out.println("5. Desactivar funcionario");
            System.out.println("0. Volver al menú principal");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> crearFuncionario();
                case 2 -> listarTodos();
                case 3 -> buscarPorId();
                case 4 -> modificarFuncionario();
                case 5 -> desactivarFuncionario();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    // ============================================================
    // CREAR FUNCIONARIO (actualizado)
    // ============================================================
    private void crearFuncionario() {
        String cedula = leerTexto("Cédula: ");
        String nombre = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        String username = leerTexto("Username: ");
        String password = leerTexto("Password: ");
        int idRol = leerEntero("ID de rol: ");
        LocalDate fechaNacimiento = leerFecha("Fecha de nacimiento (YYYY-MM-DD): ");

        try {
            Funcionario f = facade.crearFuncionario(cedula, nombre, apellido, username, password, idRol, fechaNacimiento);
            System.out.println("✅ Funcionario creado: " + f);
        } catch (SQLException ex) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(ex);
            System.out.println("❌ Error al crear funcionario: " + msg);
        }
    }

    // ============================================================
    // LISTAR FUNCIONARIOS
    // ============================================================
    private void listarTodos() {
        try {
            List<Funcionario> lista = facade.listarTodos();
            if (lista.isEmpty()) System.out.println("No hay funcionarios registrados.");
            else lista.forEach(System.out::println);
        } catch (SQLException ex) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(ex);
            System.out.println("❌ Error al listar funcionarios: " + msg);
        }
    }

    // ============================================================
    // BUSCAR POR ID
    // ============================================================
    private void buscarPorId() {
        int id = leerEntero("ID del funcionario: ");
        try {
            Funcionario f = facade.obtenerPorId(id);
            if (f != null) System.out.println(f);
            else System.out.println("❌ Funcionario no encontrado.");
        } catch (SQLException ex) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(ex);
            System.out.println("❌ Error al buscar funcionario: " + msg);
        }
    }

    // ============================================================
    // MODIFICAR FUNCIONARIO
    // ============================================================
    private void modificarFuncionario() {
        int id = leerEntero("ID del funcionario a modificar: ");
        String cedula = leerTexto("Nueva cédula: ");
        String nombre = leerTexto("Nuevo nombre: ");
        String apellido = leerTexto("Nuevo apellido: ");
        String username = leerTexto("Nuevo username: ");
        String password = leerTexto("Nuevo password: ");
        String correo = leerTexto("Nuevo correo: ");
        int idRol = leerEntero("Nuevo ID de rol: ");
        boolean estActivo = leerBoolean("Activo (true/false): ");

        try {
            boolean exito = facade.actualizarFuncionario(id, cedula, nombre, apellido, username, password, correo, idRol, estActivo);
            if (exito) System.out.println("✅ Funcionario modificado.");
            else System.out.println("❌ No se pudo modificar el funcionario.");
        } catch (SQLException ex) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(ex);
            System.out.println("❌ Error al modificar funcionario: " + msg);
        }
    }

    // ============================================================
    // DESACTIVAR FUNCIONARIO
    // ============================================================
    private void desactivarFuncionario() {
        int id = leerEntero("ID del funcionario a desactivar: ");
        try {
            boolean exito = facade.desactivarFuncionario(id);
            if (exito) System.out.println("✅ Funcionario desactivado.");
            else System.out.println("❌ No se pudo desactivar el funcionario.");
        } catch (SQLException ex) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(ex);
            System.out.println("❌ Error al desactivar funcionario: " + msg);
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
            String entrada = scanner.nextLine().trim();
            try {
                return LocalDate.parse(entrada);
            } catch (Exception e) {
                System.out.print("Formato inválido. Use YYYY-MM-DD: ");
            }
        }
    }
}
