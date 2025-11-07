package consola.EstudianteConsola;

import consola.InterfazConsola.UIBase;
import modelo.Estudiante;
import SINGLETON.SesionSingleton;

import java.sql.SQLException;

public class EstudianteConsola extends UIBase {

    private Estudiante estudiante;

    // Constructor vacío; la validación y carga del estudiante se realiza en iniciar()
    public EstudianteConsola() {
        // Constructor vacío
    }

    // Inicia la sesión del estudiante y muestra el menú principal
    @Override
    public void iniciar() {
        SesionSingleton login = SesionSingleton.getInstance();

        // Verificar que haya sesión activa
        if (!login.haySesionActiva()) {
            mostrarError("No hay sesión activa.");
            return;
        }

        // Validar que el usuario actual sea un estudiante
        if (!(login.getUsuarioActual() instanceof Estudiante)) {
            mostrarError("El usuario actual no es un estudiante.");
            return;
        }

        // Asignar el usuario autenticado como estudiante
        estudiante = (Estudiante) login.getUsuarioActual();

        // Ejecutar el bucle principal del menú
        super.iniciar();

        // Cerrar sesión al finalizar
        mostrarInfo("Sesión finalizada correctamente.\n");
        login.cerrarSesion();
    }

    // Mostrar el menú principal del estudiante
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

    // Controla la ejecución de las opciones del menú según la elección del usuario
    @Override
    protected void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> mostrarInformacionPersonal();  // Ver datos personales del estudiante
                case 2 -> menuSeguimiento();              // Consultar su seguimiento académico
                case 3 -> gestionarMisInstanciasComunes(); // Acceder a sus instancias comunes
                case 4 -> gestionarMisTelefonos();        // Administrar sus teléfonos
                case 5 -> gestionarMisNotificaciones();   // Ver sus notificaciones
                case 0 -> mostrarInfo("🔒 Cerrando sesión del Estudiante...");
                default -> mostrarError("Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error al ejecutar la opción: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Mostrar la información personal del estudiante autenticado
    private void mostrarInformacionPersonal() {
        mostrarInfo("--- Información personal ---");
        System.out.println("ID: " + estudiante.getIdUsuario());
        System.out.println("Nombre: " + estudiante.getNombre());
        System.out.println("Apellido: " + estudiante.getApellido());
        System.out.println("Correo: " + estudiante.getCorreo());
    }

    // Acceder al módulo de seguimiento académico del estudiante
    private void menuSeguimiento() {
        try {
            SeguimientoConsola seguimientoUI = new SeguimientoConsola();
            seguimientoUI.iniciar();
        } catch (SQLException e) {
            mostrarError("Error al acceder al módulo de seguimiento: " + e.getMessage());
        }
    }

    // Gestionar las instancias comunes asociadas al estudiante
    private void gestionarMisInstanciasComunes() {
        try {
            InstanciaComunConsola instanciaComunUI = new InstanciaComunConsola();
            instanciaComunUI.iniciar();
        } catch (SQLException e) {
            mostrarError("Error al inicializar las instancias comunes: " + e.getMessage());
        }
    }

    // Gestionar los teléfonos personales del estudiante
    private void gestionarMisTelefonos() {
        try {
            TelefonoConsola telefonoEstudianteUI = new TelefonoConsola();
            telefonoEstudianteUI.iniciar();
        } catch (SQLException e) {
            mostrarError("Error al gestionar los teléfonos: " + e.getMessage());
        }
    }

    // Gestionar las notificaciones del estudiante autenticado
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
