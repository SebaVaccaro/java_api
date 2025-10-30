package consola.Analista;

import SINGLETON.LoginSingleton;
import consola.Admin.*;
import consola.Psicopedagogo.EstudiantePsicoUI;
import consola.Psicopedagogo.FuncionarioPsicoUI;
import modelo.Usuario;
import servicios.FuncionarioServicio;
import consola.interfaz.UIBase;

public class AnalistaUI extends UIBase {

    private final FuncionarioServicio funcionarioServicio;

    public AnalistaUI() {
        FuncionarioServicio tempService = null;
        try {
            tempService = new FuncionarioServicio();
        } catch (Exception e) {
            System.out.println("⚠️ Error al inicializar FuncionarioService: " + e.getMessage());
        }
        this.funcionarioServicio = tempService;
    }

    @Override
    public void mostrarMenu() {
        if (!LoginSingleton.getInstance().haySesionActiva()) {
            mostrarError("No hay sesión activa. Por favor inicia sesión.");
            return;
        }

        Usuario funcionario = LoginSingleton.getInstance().getUsuarioActual();

        System.out.println("\n===== MENÚ ANALISTA DE EQUIPO =====");
        System.out.println("Bienvenido/a, " + funcionario.getNombre() + " " + funcionario.getApellido());
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
                case 1 -> new ArchivoAdjuntoAdminUI().mostrarMenu();
                case 2 -> new CarreraAdminUI().mostrarMenu();
                case 3 -> new CiudadAdminUI().mostrarMenu();
                case 4 -> new DireccionAdminUI().mostrarMenu();
                case 5 -> new EstudiantePsicoUI().mostrarMenu();
                case 6 -> new FuncionarioPsicoUI().mostrarMenu();
                case 7 -> new GrupoAdminUI().mostrarMenu();
                case 8 -> new ITRAdminUI().mostrarMenu();
                case 9 -> new IncidenciaAdminUI().mostrarMenu();
                case 10 -> new InstanciaComunAdminUI().mostrarMenu();
                case 11 -> new NotificacionAdminUI().mostrarMenu();
                case 12 -> new ObservacionAdminUI().mostrarMenu();
                case 13 -> new RolAdminUI().mostrarMenu();
                case 14 -> new SeguimientoAdminUI().mostrarMenu();
                case 15 -> new TeleUsuarioAdminUI().mostrarMenu();
                case 16 -> new PartSeguimientoAdminUI().mostrarMenu();
                case 17 -> new PerteneceAdminUI().mostrarMenu();
                case 18 -> new RecibeAdminUI().mostrarMenu();
                case 19 -> new TeleITRAdminUI().mostrarMenu();
                case 0 -> {
                    mostrarInfo("🔒 Cerrando sesión...");
                    LoginSingleton.getInstance().cerrarSesion();
                }
                default -> mostrarError("Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("⚠️ Error al ejecutar la opción: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método de inicio simplificado usando UIBase
    public void iniciarSesion() {
        iniciar();
    }

}
