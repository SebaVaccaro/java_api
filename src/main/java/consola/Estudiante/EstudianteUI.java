package consola.Estudiante;

import facade.SeguimientoFacade;
import modelo.Estudiante;

import java.util.List;
import java.util.Scanner;

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
        System.out.println("3. Gestión de mis instancias");
        System.out.println("4. Gestionar mis teléfonos");
        System.out.println("5. Ver mis notificaciones");
        System.out.println("0. Cerrar sesión");
    }

    private void manejarOpcionPrincipal(int opcion) {
        switch (opcion) {
            case 1 -> mostrarInformacionPersonal();
            case 2 -> menuSeguimiento();
            case 3 -> gestionarMisInstancias();
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
        SeguimientoUI seguimientoUI = new SeguimientoUI(estudiante.getId());
        seguimientoUI.menuSeguimientos();
    }

    private void gestionarMisInstancias() {
        InstanciaUI instanciaUI = new InstanciaUI(estudiante.getId());
        instanciaUI.menuInstancias();
    }

    private void gestionarMisTelefonos() {
        TelefonoUI telefonoUI = new TelefonoUI(estudiante.getId());
        telefonoUI.menuTelefonos();
    }

    private void gestionarMisNotificaciones() {
        NotificacionUI notificacionUI = new NotificacionUI(estudiante.getId());
        notificacionUI.menuNotificaciones();
    }

    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("Ingrese un número válido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }
}
