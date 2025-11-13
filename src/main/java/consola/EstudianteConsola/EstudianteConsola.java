package consola.EstudianteConsola;

import consola.InterfazConsola.UIBase;
import modelo.Estudiante;
import SINGLETON.SesionSingleton;
import FACADE.SesionFacade;

import java.sql.SQLException;

public class EstudianteConsola extends UIBase {

    private Estudiante estudiante;

    public EstudianteConsola() {
        // Constructor vacío
    }

    @Override
    public void iniciar() {
        SesionSingleton sesion = SesionSingleton.getInstance();

        if (!sesion.haySesionActiva()) {
            mostrarError("No hay sesión activa.");
            return;
        }

        if (!"ESTUDIANTE".equalsIgnoreCase(sesion.getRolActual())) {
            mostrarError("El usuario actual no tiene rol de estudiante.");
            return;
        }

        // Inicializamos estudiante desde la sesión.
        this.estudiante = (Estudiante) sesion.getUsuarioActual();


        // Ejecutar menú heredado
        super.iniciar();

        // Cerrar sesión usando la FACADE
        SesionFacade facade = new SesionFacade();
        facade.logout();

        mostrarInfo("Sesión del estudiante finalizada.\n");
    }

    @Override
    protected void mostrarMenu() {
        System.out.println("\n===== MENÚ ESTUDIANTE =====");
        System.out.println("Bienvenido, " + estudiante.getNombre()  + " " + estudiante.getApellido());
        System.out.println("====================================");
        System.out.println("1. Ver información personal");
        System.out.println("2. Consultar seguimiento");
        System.out.println("3. Gestión de mis instancias comunes");
        System.out.println("4. Gestionar mis teléfonos");
        System.out.println("5. Ver mis notificaciones");
        System.out.println("0. Cerrar sesión");
        System.out.println("====================================");
    }

    @Override
    protected void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> mostrarInformacionPersonal();
                case 2 -> menuSeguimiento();
                case 3 -> gestionarMisInstanciasComunes();
                case 4 -> gestionarMisTelefonos();
                case 5 -> gestionarMisNotificaciones();
                case 0 -> mostrarInfo("Cerrando sesión del estudiante...");
                default -> mostrarError("Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error al ejecutar la opción: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void mostrarInformacionPersonal() {
        mostrarInfo("--- Información personal ---");
        System.out.println("ID: " + estudiante.getIdUsuario());
        System.out.println("Nombre: " + estudiante.getNombre());
        System.out.println("Apellido: " + estudiante.getApellido());
        System.out.println("Correo: " + estudiante.getCorreo());
    }

    private void menuSeguimiento() {
        try {
            SeguimientoConsola ui = new SeguimientoConsola();
            ui.iniciar();
        } catch (SQLException e) {
            mostrarError("Error en seguimiento: " + e.getMessage());
        }
    }

    private void gestionarMisInstanciasComunes() {
        try {
            InstanciaComunConsola ui = new InstanciaComunConsola();
            ui.iniciar();
        } catch (SQLException e) {
            mostrarError("Error en instancias comunes: " + e.getMessage());
        }
    }

    private void gestionarMisTelefonos() {
        try {
            TelefonoConsola ui = new TelefonoConsola();
            ui.iniciar();
        } catch (SQLException e) {
            mostrarError("Error al gestionar teléfonos: " + e.getMessage());
        }
    }

    private void gestionarMisNotificaciones() throws Exception {
        try {
            NotificacionConsola ui = new NotificacionConsola();
            ui.iniciar();
        } catch (SQLException e) {
            mostrarError("Error al inicializar notificaciones: " + e.getMessage());
        }
    }
}

