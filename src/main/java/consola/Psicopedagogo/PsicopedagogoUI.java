package consola.Psicopedagogo;

import consola.Admin.*;
import modelo.Funcionario;
import servicios.FuncionarioService;
import java.util.Scanner;

public class PsicopedagogoUI {

    private final Scanner scanner;
    private final Funcionario funcionarioActual;
    private final FuncionarioService funcionarioService;

    public PsicopedagogoUI(Funcionario funcionarioActual) {
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
        return new String[] { null, null };
    }

    private void mostrarMenuPrincipal(Funcionario adminActual) {
        System.out.println("\n===== MENÚ PSICOPEDAGOGO =====");
        System.out.println("Bienvenido/a, " + adminActual.getNombre() + " " + adminActual.getApellido());
        System.out.println("====================================");
        System.out.println("1. Gestión de archivos adjuntos");
        System.out.println("2. Gestión de carreras");
        System.out.println("3. Gestión de ciudades");
        System.out.println("4. Gestión de direcciones");
        System.out.println("5. Gestión de estudiantes");
        System.out.println("6. Gestión de funcionarios");
        System.out.println("7. Gestión de grupos");
        System.out.println("8. Gestión de ITRs");
        System.out.println("9. Gestión de incidencias");
        System.out.println("10. Gestión de instancias");
        System.out.println("11. Gestión de notificaciones");
        System.out.println("12. Gestión de observaciones");
        System.out.println("13. Gestión de roles");
        System.out.println("14. Gestión de seguimientos");
        System.out.println("15. Gestión de teléfonos de usuarios");
        System.out.println("16. Gestión de participantes en seguimientos");
        System.out.println("17. Gestión de pertenece (Carrera ↔ ITR)");
        System.out.println("18. Gestión de recibe (Notificación ↔ Usuario)");
        System.out.println("19. Gestión de teléfonos ITR");
        System.out.println("0. Cerrar sesión");
        System.out.println("====================================");
    }

    private void manejarOpcionPrincipal(int opcion, Funcionario adminActual) {
        try {
            switch (opcion) {
                case 1 -> new ArchivoAdjuntoAdminUI().menuArchivos();
                case 2 -> new CarreraAdminUI().menuCarreras();
                case 3 -> new CiudadAdminUI().menuCiudades();
                case 4 -> new DireccionAdminUI().menuDirecciones();
                case 5 -> new EstudiantePsicoUI().menuEstudiantes();
                case 6 -> new FuncionarioPsicoUI().menuFuncionarios();
                case 7 -> new GrupoAdminUI().menuGrupos();
                case 8 -> new ITRAdminUI().menuITR();
                case 9 -> new IncidenciaAdminUI().menuIncidencias();
                case 10 -> new InstanciaComunAdminUI().menuInstanciasComunes();
                case 11 -> new NotificacionAdminUI().menuNotificaciones();
                case 12 -> new ObservacionAdminUI().menuObservaciones();
                case 13 -> new RolAdminUI().menu();
                case 14 -> new SeguimientoAdminUI().menuSeguimientos();
                case 15 -> new TeleUsuarioAdminUI().menuTelefonosUsuario();
                case 16 -> new PartSeguimientoAdminUI().menu();
                case 17 -> new PerteneceAdminUI().menu();
                case 18 -> new RecibeAdminUI().menu();
                case 19 -> new TeleITRAdminUI().menuTelefonos();
                case 0 -> System.out.println("🔒 Cerrando sesión del administrador...");
                default -> System.out.println("❌ Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error al ejecutar la opción: " + e.getMessage());
            e.printStackTrace();
        }
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