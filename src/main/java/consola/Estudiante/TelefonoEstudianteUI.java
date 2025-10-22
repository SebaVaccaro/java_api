package consola.Estudiante;

import facade.TeleUsuarioFacade;
import modelo.TeleUsuario;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class TelefonoEstudianteUI {

    private final TeleUsuarioFacade teleUsuarioFacade;
    private final Scanner scanner;
    private final int idUsuario; // ⚡ ID del usuario actual

    // ============================================================
    // CONSTRUCTOR CON ID DE USUARIO
    // ============================================================
    public TelefonoEstudianteUI(int idUsuario) throws SQLException {
        this.teleUsuarioFacade = new TeleUsuarioFacade();
        this.scanner = new Scanner(System.in);
        this.idUsuario = idUsuario;
    }

    // ============================================================
    // MÉTODO PRINCIPAL DEL MENÚ
    // ============================================================
    public void iniciar() {
        int opcion;

        do {
            System.out.println("\n==============================");
            System.out.println("📱 GESTIÓN DE TELÉFONOS DEL USUARIO ID: " + idUsuario);
            System.out.println("==============================");
            System.out.println("1. Crear teléfono");
            System.out.println("2. Listar mis teléfonos");
            System.out.println("3. Actualizar teléfono");
            System.out.println("4. Eliminar teléfono");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            try {
                switch (opcion) {
                    case 1 -> crearTelefono();
                    case 2 -> listarTelefonos();
                    case 3 -> actualizarTelefono();
                    case 4 -> eliminarTelefono();
                    case 0 -> System.out.println("Saliendo del sistema...");
                    default -> System.out.println("⚠️ Opción inválida. Intente de nuevo.");
                }
            } catch (SQLException e) {
                System.out.println("❌ Error en la base de datos: " + e.getMessage());
            }

        } while (opcion != 0);
    }

    // ============================================================
    // OPCIONES DEL MENÚ (CRUD PARA ESTE USUARIO)
    // ============================================================

    private void crearTelefono() throws SQLException {
        System.out.print("Ingrese el número de teléfono: ");
        String numero = scanner.nextLine();

        TeleUsuario nuevo = teleUsuarioFacade.crearTelefono(numero, idUsuario);
        System.out.println("✅ Teléfono creado con éxito. ID generado: " + nuevo.getIdTelefono());
    }

    private void listarTelefonos() throws SQLException {
        List<TeleUsuario> lista = teleUsuarioFacade.listarTelefonosPorUsuario(idUsuario);

        if (lista.isEmpty()) {
            System.out.println("No tienes teléfonos registrados.");
        } else {
            System.out.println("\n📋 MIS TELÉFONOS");
            for (TeleUsuario t : lista) {
                System.out.println("ID: " + t.getIdTelefono() + " | Número: " + t.getNumero());
            }
        }
    }

    private void actualizarTelefono() throws SQLException {
        System.out.print("Ingrese el ID del teléfono a actualizar: ");
        int idTel = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Ingrese el nuevo número: ");
        String numero = scanner.nextLine();

        boolean actualizado = teleUsuarioFacade.actualizarTelefono(idTel, numero, idUsuario);

        if (actualizado) {
            System.out.println("✅ Teléfono actualizado correctamente.");
        } else {
            System.out.println("⚠️ No se pudo actualizar el teléfono. Verifica que el ID pertenezca a ti.");
        }
    }

    private void eliminarTelefono() throws SQLException {
        System.out.print("Ingrese el ID del teléfono a eliminar: ");
        int idTel = scanner.nextInt();
        scanner.nextLine();

        boolean eliminado = teleUsuarioFacade.eliminarTelefono(idTel);

        if (eliminado) {
            System.out.println("🗑️ Teléfono eliminado correctamente.");
        } else {
            System.out.println("⚠️ No se encontró el teléfono o no pertenece a ti.");
        }
    }

    // ============================================================
    // MAIN PARA PRUEBAS
    // ============================================================
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese su ID de usuario: ");
        int idUsuario = sc.nextInt();
        sc.nextLine();

        try {
            TelefonoEstudianteUI ui = new TelefonoEstudianteUI(idUsuario);
            ui.iniciar();
        } catch (SQLException e) {
            System.out.println("❌ Error al iniciar la interfaz: " + e.getMessage());
        }
    }
}
