package consola.FuncionarioConsola;

import SINGLETON.LoginSingleton;
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
            mostrarError("Error al inicializar FuncionarioServicio: " + e.getMessage());
        }
        this.funcionarioServicio = tempService;
    }

    // Inicia el módulo principal del funcionario
    @Override
    public void iniciar() {
        // Se obtiene la instancia del login (Singleton)
        LoginSingleton login = LoginSingleton.getInstance();

        // Verifica si hay una sesión activa
        if (!login.haySesionActiva()) {
            mostrarError("No hay sesión activa.");
            return;
        }

        // Verifica que el usuario actual sea un funcionario
        if (!(login.getUsuarioActual() instanceof Funcionario)) {
            mostrarError("El usuario actual no es un funcionario.");
            return;
        }

        // Guarda los datos del funcionario actual y su rol
        funcionarioActual = (Funcionario) login.getUsuarioActual();
        rolActual = login.getRolActual();

        // Llama al método iniciar() de la clase base para mostrar el menú
        super.iniciar();

        // Cierra la sesión al finalizar
        mostrarInfo("Sesión finalizada correctamente.");
        login.cerrarSesion();
    }

    // Muestra el menú principal del funcionario según su rol
    @Override
    protected void mostrarMenu() {
        System.out.println("\n===== MENÚ " + rolActual.toUpperCase() + " =====");
        System.out.println("Bienvenido/a, " + funcionarioActual.getNombre() + " " + funcionarioActual.getApellido());
        System.out.println("Rol: " + rolActual);
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

    // Maneja la opción seleccionada por el usuario en el menú
    @Override
    protected void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> new ArchivoAdjuntoConsola().iniciar();   // Módulo de archivos adjuntos
                case 2 -> new CarreraConsola().iniciar();          // Módulo de carreras
                case 3 -> new CiudadConsola().iniciar();           // Módulo de ciudades
                case 4 -> new DireccionConsola().iniciar();        // Módulo de direcciones
                case 5 -> new EstudianteConsola().iniciar();       // Módulo de estudiantes
                case 6 -> new FuncionarioConsola().iniciar();      // Módulo de funcionarios
                case 7 -> new GrupoConsola().iniciar();            // Módulo de grupos
                case 8 -> new ITRConsola().iniciar();              // Módulo de ITRs
                case 9 -> new IncidenciaConsola().iniciar();       // Módulo de incidencias
                case 10 -> new InstanciaComunConsola().iniciar();  // Módulo de instancias comunes
                case 11 -> new NotificacionConsola().iniciar();    // Módulo de notificaciones
                case 12 -> new ObservacionConsola().iniciar();     // Módulo de observaciones
                case 13 -> new RolConsola().iniciar();             // Módulo de roles
                case 14 -> new SeguimientoConsola().iniciar();     // Módulo de seguimientos
                case 15 -> new TeleUsuarioConsola().iniciar();     // Módulo de teléfonos de usuarios
                case 16 -> new PartSeguimientoConsola().iniciar(); // Módulo de participantes en seguimientos
                case 17 -> new PerteneceConsola().iniciar();       // Módulo de pertenece (Carrera ↔ ITR)
                case 18 -> new RecibeConsola().iniciar();          // Módulo de recibe (Notificación ↔ Usuario)
                case 19 -> new TeleITRConsola().iniciar();         // Módulo de teléfonos de ITR
                case 0 -> mostrarInfo("Cerrando sesión de " + rolActual + "..."); // Cierra sesión
                default -> mostrarError("Opción inválida. Intente nuevamente.");  // Opción incorrecta
            }
        } catch (Exception e) {
            // Maneja cualquier error que ocurra al ejecutar un módulo
            mostrarError("Error al ejecutar la opción: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
