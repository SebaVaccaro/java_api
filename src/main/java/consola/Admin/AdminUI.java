package consola.Admin;

import modelo.Funcionario;
import servicios.FuncionarioService;
import java.util.Scanner;

public class AdminUI {

    private final Scanner scanner;
    private final Funcionario funcionarioActual;
    private final FuncionarioService funcionarioService;

    // 🔧 Constructor que recibe directamente el objeto Funcionario autenticado
    public AdminUI(Funcionario funcionarioActual) {
        this.funcionarioActual = funcionarioActual;
        this.scanner = new Scanner(System.in);

        FuncionarioService tempService = null;
        try {
            tempService = new FuncionarioService();
        } catch (Exception e) {
            System.out.println("⚠️ Error al inicializar FuncionarioService: " + e.getMessage());
        }
        this.funcionarioService = tempService;
    }

    // 🏁 Método principal que muestra el menú y gestiona las opciones
    public String[] iniciar() {
        if (funcionarioActual == null) {
            System.out.println("⚠️ Error: el funcionario recibido es nulo.");
            return new String[] { null, null };
        }

        int opcion;
        do {
            mostrarMenuPrincipal(funcionarioActual);
            opcion = leerEntero("Seleccione una opción: ");
            manejarOpcionPrincipal(opcion, funcionarioActual);
        } while (opcion != 0);

        System.out.println("Sesión finalizada correctamente.\n");
        return new String[] { null, null }; // indica que cerró sesión
    }

    // 🧭 Menú principal del administrador
    private void mostrarMenuPrincipal(Funcionario adminActual) {
        System.out.println("\n===== MENÚ ADMINISTRADOR =====");
        System.out.println("Bienvenido/a, " + adminActual.getNombre() + " " + adminActual.getApellido());
        System.out.println("====================================");
        System.out.println("1. Gestión de estudiantes");
        System.out.println("2. Gestión de carreras");
        System.out.println("3. Gestión de grupos");
        System.out.println("4. Gestión de ciudades");
        System.out.println("5. Gestión de direcciones");
        System.out.println("6. Gestión de incidencias");
        System.out.println("7. Gestión de instancias");
        System.out.println("8. Gestión de ITRs");
        System.out.println("9. Gestión de notificaciones");
        System.out.println("10. Gestión de observaciones");
        System.out.println("11. Gestión de roles");
        System.out.println("12. Gestión de seguimientos");
        System.out.println("13. Gestión de teléfonos");
        System.out.println("14. Gestión de archivos adjuntos");
        System.out.println("0. Cerrar sesión");
        System.out.println("====================================");
    }

    // ⚙️ Maneja la opción seleccionada por el administrador
    private void manejarOpcionPrincipal(int opcion, Funcionario adminActual) {
        try {
            switch (opcion) {
                case 1 -> new EstudianteAdminUI().menuEstudiantes();
                case 2 -> new CarreraAdminUI().menuCarreras();
                case 3 -> new GrupoAdminUI().menuGrupos();
                case 4 -> new CiudadAdminUI().menuCiudades();
                case 5 -> new DireccionAdminUI().menuDirecciones();
                case 6 -> new IncidenciaAdminUI().menuIncidencias();
                case 7 -> new InstanciaComunAdminUI().menuInstanciasComunes();
                case 8 -> new ITRAdminUI().menuITR();
                case 9 -> new NotificacionAdminUI().menuNotificaciones();
                case 10 -> new ObservacionAdminUI().menuObservaciones();
                case 11 -> new RolAdminUI().menu();
                case 12 -> new SeguimientoAdminUI().menuSeguimientos();
                case 13 -> new TeleUsuarioAdminUI().menuTelefonosUsuario();
                case 14 -> new ArchivoAdjuntoAdminUI().menuArchivos();
                case 0 -> System.out.println("🔒 Cerrando sesión del administrador...");
                default -> System.out.println("❌ Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error al ejecutar la opción: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // 🔢 Método auxiliar para leer enteros con validación
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
