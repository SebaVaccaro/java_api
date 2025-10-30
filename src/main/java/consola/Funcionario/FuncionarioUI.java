package consola.Funcionario;

import consola.interfaz.UIBase;
import consola.Admin.*;
import modelo.Funcionario;
import servicios.FuncionarioServicio;
import SINGLETON.LoginSingleton;

public class FuncionarioUI extends UIBase {

    private final FuncionarioServicio funcionarioServicio;
    private Funcionario funcionarioActual;

    public FuncionarioUI() {
        FuncionarioServicio tempService = null;
        try {
            tempService = new FuncionarioServicio();
        } catch (Exception e) {
            mostrarError("Error al inicializar FuncionarioService: " + e.getMessage());
        }
        this.funcionarioServicio = tempService;
    }

    @Override
    public void iniciar() {
        LoginSingleton login = LoginSingleton.getInstance();

        if (!login.haySesionActiva() || !"FUNCIONARIO".equalsIgnoreCase(login.getRolActual())) {
            mostrarError("No hay sesión activa de funcionario.");
            return;
        }

        funcionarioActual = (Funcionario) login.getUsuarioActual(); // cast seguro
        super.iniciar(); // 🔁 ejecuta el bucle del menú base

        mostrarInfo("Sesión finalizada correctamente.\n");
        login.cerrarSesion();
    }

    @Override
    protected void mostrarMenu() {
        System.out.println("\n===== MENÚ FUNCIONARIO =====");
        System.out.println("Bienvenido/a, " + funcionarioActual.getNombre() + " " + funcionarioActual.getApellido());
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

    @Override
    protected void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> new ArchivoAdjuntoAdminUI().iniciar();
                case 2 -> new CarreraAdminUI().iniciar();
                case 3 -> new CiudadAdminUI().iniciar();
                case 4 -> new DireccionAdminUI().iniciar();
                case 5 -> new EstudianteAdminUI().iniciar();
                case 6 -> new FuncionarioAdminUI().iniciar();
                case 7 -> new GrupoAdminUI().iniciar();
                case 8 -> new ITRAdminUI().iniciar();
                case 9 -> new IncidenciaAdminUI().iniciar();
                case 10 -> new InstanciaComunAdminUI().iniciar();
                case 11 -> new NotificacionAdminUI().iniciar();
                case 12 -> new ObservacionAdminUI().iniciar();
                case 13 -> new RolAdminUI().iniciar();
                case 14 -> new SeguimientoAdminUI().iniciar();
                case 15 -> new TeleUsuarioAdminUI().iniciar();
                case 16 -> new PartSeguimientoAdminUI().iniciar();
                case 17 -> new PerteneceAdminUI().iniciar();
                case 18 -> new RecibeAdminUI().iniciar();
                case 19 -> new TeleITRAdminUI().iniciar();
                case 0 -> mostrarInfo("🔒 Cerrando sesión del funcionario...");
                default -> mostrarError("Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error al ejecutar la opción: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
