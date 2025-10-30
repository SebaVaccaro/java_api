package consola.Estudiante;

import consola.interfaz.UIBase;
import modelo.Estudiante;
import SINGLETON.LoginSingleton;

import java.sql.SQLException;

public class EstudianteUI extends UIBase {

    private final Estudiante estudiante;

    public EstudianteUI() {
        if (LoginSingleton.getInstance().haySesionActiva()) {
            this.estudiante = (Estudiante) LoginSingleton.getInstance().getUsuarioActual();
        } else {
            this.estudiante = null;
            mostrarError("No hay sesión activa. Por favor inicia sesión.");
        }
    }

    @Override
    protected void mostrarMenu() {
        if (estudiante == null) return;

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

    @Override
    protected void manejarOpcion(int opcion) {
        if (estudiante == null) return;

        switch (opcion) {
            case 1 -> mostrarInformacionPersonal();
            case 2 -> menuSeguimiento();
            case 3 -> gestionarMisInstanciasComunes();
            case 4 -> gestionarMisTelefonos();
            case 5 -> gestionarMisNotificaciones();
            case 0 -> mostrarInfo("🔒 Cerrando sesión del Estudiante...");
            default -> mostrarError("Opción inválida. Intente nuevamente.");
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
            SeguimientoEstudianteUI seguimientoUI = new SeguimientoEstudianteUI();
            seguimientoUI.mostrarMenu();
        } catch (SQLException e) {
            mostrarError("Error al acceder al módulo de seguimiento: " + e.getMessage());
        }
    }

    private void gestionarMisInstanciasComunes() {
        try {
            InstanciaComunEstUI instanciaComunUI = new InstanciaComunEstUI();
            instanciaComunUI.mostrarMenu();
        } catch (SQLException e) {
            mostrarError("Error al inicializar las instancias comunes: " + e.getMessage());
        }
    }

    private void gestionarMisTelefonos() {
        try {
            TelefonoEstudianteUI telefonoEstudianteUI = new TelefonoEstudianteUI();
            telefonoEstudianteUI.iniciar();
        } catch (SQLException e) {
            mostrarError("Error al gestionar los teléfonos: " + e.getMessage());
        }
    }

    private void gestionarMisNotificaciones() {
        try {
            NotificacionUserUI notificacionUserUI = new NotificacionUserUI();
            notificacionUserUI.mostrarMenu();
        } catch (SQLException e) {
            mostrarError("Error de base de datos al inicializar notificaciones: " + e.getMessage());
        } catch (Exception e) {
            mostrarError("Error al inicializar notificaciones: " + e.getMessage());
        }
    }

    @Override
    public void iniciar() {
        if (estudiante == null) return;

        super.iniciar();
        LoginSingleton.getInstance().cerrarSesion();
        mostrarInfo("🔒 Sesión finalizada correctamente.\n");
    }
}

