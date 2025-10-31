package consola.EstudianteConsola;

import consola.InterfazConsola.UIBase;
import modelo.Estudiante;
import SINGLETON.LoginSingleton;

import java.sql.SQLException;

public class EstudianteConsola extends UIBase {

    private Estudiante estudiante;

    public EstudianteConsola() {
        // Constructor vacío, la validación se hace en iniciar()
    }

    @Override
    public void iniciar() {
        LoginSingleton login = LoginSingleton.getInstance();

        if (!login.haySesionActiva()) {
            mostrarError("No hay sesión activa.");
            return;
        }

        // Validar que el usuario sea un Estudiante
        if (!(login.getUsuarioActual() instanceof Estudiante)) {
            mostrarError("El usuario actual no es un estudiante.");
            return;
        }

        estudiante = (Estudiante) login.getUsuarioActual();

        super.iniciar(); // 🔁 ejecuta el bucle de menú común
        mostrarInfo("Sesión finalizada correctamente.\n");
        login.cerrarSesion();
    }

    @Override
    protected void mostrarMenu() {
        System.out.println("\n===== MENÚ ESTUDIANTE =====");
        System.out.println("Bienvenido, " + estudiante.getNombre() + " " + estudiante.getApellido());
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
                case 0 -> mostrarInfo("🔒 Cerrando sesión del Estudiante...");
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
            SeguimientoConsola seguimientoUI = new SeguimientoConsola();
            seguimientoUI.iniciar();
        } catch (SQLException e) {
            mostrarError("Error al acceder al módulo de seguimiento: " + e.getMessage());
        }
    }

    private void gestionarMisInstanciasComunes() {
        try {
            InstanciaComunConsola instanciaComunUI = new InstanciaComunConsola();
            instanciaComunUI.iniciar();
        } catch (SQLException e) {
            mostrarError("Error al inicializar las instancias comunes: " + e.getMessage());
        }
    }

    private void gestionarMisTelefonos() {
        try {
            TelefonoConsola telefonoEstudianteUI = new TelefonoConsola();
            telefonoEstudianteUI.iniciar();
        } catch (SQLException e) {
            mostrarError("Error al gestionar los teléfonos: " + e.getMessage());
        }
    }

    private void gestionarMisNotificaciones() {
        try {
            NotificacionConsola notificacionUserUI = new NotificacionConsola();
            notificacionUserUI.iniciar();
        } catch (SQLException e) {
            mostrarError("Error de base de datos al inicializar notificaciones: " + e.getMessage());
        } catch (Exception e) {
            mostrarError("Error al inicializar notificaciones: " + e.getMessage());
        }
    }
}