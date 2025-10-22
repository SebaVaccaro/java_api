package consola.Estudiante;

import modelo.Estudiante;
import java.util.Scanner;
import java.sql.SQLException;

public class EstudianteUI {

    private final Scanner scanner;
    private final Estudiante estudiante;

    public EstudianteUI(Estudiante estudiante) {
        this.estudiante = estudiante;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = leerEntero("Seleccione una opción: ");
            manejarOpcionPrincipal(opcion);
        } while (opcion != 0);

        System.out.println("Sesión finalizada correctamente.\n");
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n===== MENÚ ESTUDIANTE =====");
        System.out.println("Bienvenido, " + estudiante.getNombre() + " " + estudiante.getApellido());
        System.out.println("====================================");
        System.out.println("1. Ver información personal");
        System.out.println("2. Consultar seguimiento");
        System.out.println("3. Gestión de mis instancias comunes");
        System.out.println("4. Gestionar mis teléfonos");
        System.out.println("5. Ver mis notificaciones");
        System.out.println("0. Cerrar sesión");
    }

    private void manejarOpcionPrincipal(int opcion) {
        switch (opcion) {
            case 1 -> mostrarInformacionPersonal();
            case 2 -> menuSeguimiento();
            case 3 -> gestionarMisInstanciasComunes();
            case 4 -> gestionarMisTelefonos();
            case 5 -> gestionarMisNotificaciones();
            case 0 -> System.out.println("Cerrando sesión del Estudiante...");
            default -> System.out.println("Opción inválida. Intente nuevamente.");
        }
    }

    private void mostrarInformacionPersonal() {
        System.out.println("\n--- Información personal ---");
        System.out.println("ID: " + estudiante.getIdUsuario());
        System.out.println("Nombre: " + estudiante.getNombre());
        System.out.println("Apellido: " + estudiante.getApellido());
        System.out.println("Correo: " + estudiante.getCorreo());
    }

    private void menuSeguimiento() {
        try {
            SeguimientoEstudianteUI seguimientoUI = new SeguimientoEstudianteUI(estudiante.getIdUsuario());
            seguimientoUI.menuSeguimientoEstudiante();
        } catch (SQLException e) {
            System.out.println("❌ Error al acceder al módulo de seguimiento: " + e.getMessage());
        }
    }

    // =======================================
    // Actualización: usar InstanciaComunUI
    // =======================================
    private void gestionarMisInstanciasComunes() {
        try {
            InstanciaComunUI instanciaComunUI = new InstanciaComunUI();
            instanciaComunUI.menuInstanciasComunes();
        } catch (SQLException e) {
            System.out.println("❌ Error al inicializar las instancias comunes: " + e.getMessage());
        }
    }

    private void gestionarMisTelefonos() {
        try {
            TelefonoEstudianteUI telefonoEstudianteUI = new TelefonoEstudianteUI(estudiante.getIdUsuario());
            telefonoEstudianteUI.iniciar();
        } catch (SQLException e) {
            System.out.println("❌ Error al gestionar los teléfonos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void gestionarMisNotificaciones() {
        try {
            NotificacionUserUI notificacionUserUI = new NotificacionUserUI(estudiante.getIdUsuario());
            notificacionUserUI.menuNotificaciones();
        } catch (SQLException e) {
            System.out.println("❌ Error al inicializar la gestión de notificaciones: " + e.getMessage());
        }
    }

    // ==== MÉTODO AUXILIAR ====
    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("Ingrese un número válido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // limpia el buffer
        return valor;
    }
}
