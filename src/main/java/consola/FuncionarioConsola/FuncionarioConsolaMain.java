package consola.FuncionarioConsola;

import SINGLETON.SesionSingleton;
import consola.InterfazConsola.UIBase;
import modelo.Funcionario;
import servicios.FuncionarioServicio;

public class FuncionarioConsolaMain extends UIBase {

    // Servicio encargado de las operaciones relacionadas con funcionarios
    private final FuncionarioServicio funcionarioServicio;

    // Referencia al funcionario actualmente logueado
    private Funcionario funcionarioActual;

    // Rol actual del usuario (por ejemplo: Administrador, Tutor, Analista)
    private String rolActual;

    // Constructor: inicializa el servicio de funcionarios
    public FuncionarioConsolaMain() {
        FuncionarioServicio tempService = null;
        try {
            tempService = new FuncionarioServicio();
        } catch (Exception e) {
            mostrarError("❌ Error al inicializar FuncionarioServicio: " + e.getMessage());
        }
        this.funcionarioServicio = tempService;
    }

    // Inicia el módulo principal del funcionario
    @Override
    public void iniciar() {
        SesionSingleton login = SesionSingleton.getInstance();

        // Verificar sesión activa
        if (!login.haySesionActiva()) {
            mostrarError("❌ No hay sesión activa.");
            return;
        }

        // Verificar tipo de usuario
        if (!(login.getUsuarioActual() instanceof Funcionario)) {
            mostrarError("⚠️ El usuario actual no es un funcionario.");
            return;
        }

        // Guardar datos de sesión
        funcionarioActual = (Funcionario) login.getUsuarioActual();
        rolActual = login.getRolActual();

        // Mostrar bienvenida
        mostrarInfo("Bienvenido/a, " + funcionarioActual.getNombre() + " " + funcionarioActual.getApellido() +
                " (" + rolActual + ")");

        // Ejecutar el menú principal
        super.iniciar();

        // Cierre de sesión
        mostrarInfo("👋 Sesión finalizada correctamente. Hasta pronto.");
        login.cerrarSesion();
    }

    // Muestra el menú principal del funcionario según su rol
    @Override
    protected void mostrarMenu() {
        System.out.println("\n🧭 ===== MENÚ PRINCIPAL - " + rolActual.toUpperCase() + " =====");
        System.out.println("1.  Gestión de archivos adjuntos");
        System.out.println("2.  Gestión de carreras");
        System.out.println("3.  Gestión de ciudades");
        System.out.println("4.  Gestión de direcciones");
        System.out.println("5.  Gestión de estudiantes");
        System.out.println("6.  Gestión de funcionarios");
        System.out.println("7.  Gestión de grupos");
        System.out.println("8.  Gestión de ITRs");
        System.out.println("9.  Gestión de incidencias");
        System.out.println("10. Gestión de instancias comunes");
        System.out.println("11. Gestión de notificaciones");
        System.out.println("12. Gestión de observaciones");
        System.out.println("13. Gestión de roles");
        System.out.println("14. Gestión de seguimientos");
        System.out.println("15. Gestión de teléfonos de usuarios");
        System.out.println("16. Gestión de participantes en seguimientos");
        System.out.println("17. Gestión de pertenece (Carrera ↔ ITR)");
        System.out.println("18. Gestión de recibe (Notificación ↔ Usuario)");
        System.out.println("19. Gestión de teléfonos de ITR");
        System.out.println("0.  Cerrar sesión");
        System.out.println("=============================================");
    }

    // Maneja la opción seleccionada por el usuario en el menú
    @Override
    protected void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> new ArchivoAdjuntoConsola().iniciar();
                case 2 -> new CarreraConsola().iniciar();
                case 3 -> new CiudadConsola().iniciar();
                case 4 -> new DireccionConsola().iniciar();
                case 5 -> new EstudianteConsola().iniciar();
                case 6 -> new FuncionarioConsola().iniciar();
                case 7 -> new GrupoConsola().iniciar();
                case 8 -> new ITRConsola().iniciar();
                case 9 -> new IncidenciaConsola().iniciar();
                case 10 -> new InstanciaComunConsola().iniciar();
                case 11 -> new NotificacionConsola().iniciar();
                case 12 -> new ObservacionConsola().iniciar();
                case 13 -> new RolConsola().iniciar();
                case 14 -> new SeguimientoConsola().iniciar();
                case 15 -> new TeleUsuarioConsola().iniciar();
                case 16 -> new PartSeguimientoConsola().iniciar();
                case 17 -> new PerteneceConsola().iniciar();
                case 18 -> new RecibeConsola().iniciar();
                case 19 -> new TeleITRConsola().iniciar();
                case 0 -> mostrarInfo("🔒 Cerrando sesión de " + rolActual + "...");
                default -> mostrarError("❌ Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("⚠️ Error al ejecutar la opción: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
