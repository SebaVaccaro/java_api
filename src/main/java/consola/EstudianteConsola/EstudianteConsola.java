package consola.EstudianteConsola;

import SINGLETON.SesionSingleton;
import consola.InterfazConsola.UIBase;
import modelo.Estudiante;
import FACADE.SesionFacade;

public class EstudianteConsola extends UIBase {

    private Estudiante estudianteActual;
    private String rolActual;


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

        estudianteActual = (Estudiante) sesion.getUsuarioActual();
        rolActual = sesion.getRolActual();

        mostrarInfo("Bienvenido/a, " + estudianteActual.getNombre() + " " +
                estudianteActual.getApellido() + " (" + rolActual + ")");

        super.iniciar(); // Ejecuta el bucle de menú heredado de UIBase

        // Al salir del menú, cerrar sesión usando la fachada
        SesionFacade facade = new SesionFacade();
        facade.logout();

        mostrarInfo("Sesión finalizada correctamente. Hasta pronto, " +
                estudianteActual.getNombre() + ".");
    }

    @Override
    protected void mostrarMenu() {
        System.out.println("\n===== MENÚ PRINCIPAL - " + rolActual.toUpperCase() + " =====");
        System.out.println("1. Gestión de mi información personal");
        System.out.println("2. Gestión de mis seguimientos");
        System.out.println("3. Gestión de mis instancias comunes");
        System.out.println("4. Gestión de mis teléfonos");
        System.out.println("5. Gestión de mis notificaciones");
        System.out.println("6. Gestión de mis direcciones");
        System.out.println("0. Cerrar sesión");
        System.out.println("=============================================");
    }

    @Override
    protected void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> mostrarInformacionPersonal();
                case 2 -> new SeguimientoConsola().iniciar();
                case 3 -> new InstanciaComunConsola().iniciar();
                case 4 -> new TelefonoConsola().iniciar();
                case 5 -> new NotificacionConsola().iniciar();
                case 6 -> new DireccionConsola().iniciar();
                case 0 -> mostrarInfo("Cerrando sesión de " + rolActual + "...");
                default -> mostrarError("Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error al ejecutar la opción: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void mostrarInformacionPersonal() {
        mostrarInfo("\n--- Información Personal ---");
        System.out.println("ID: " + estudianteActual.getIdUsuario());
        System.out.println("Nombre: " + estudianteActual.getNombre());
        System.out.println("Apellido: " + estudianteActual.getApellido());
        System.out.println("Correo: " + estudianteActual.getCorreo());
        System.out.println("=============================================");
    }
}
