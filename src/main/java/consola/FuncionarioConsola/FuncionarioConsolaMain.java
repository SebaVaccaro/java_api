package consola.FuncionarioConsola;

import SINGLETON.SesionSingleton;
import consola.Factory.FuncionarioFactory;
import consola.InterfazConsola.UIBase;
import consola.InterfazConsola.UIMenu;
import modelo.Funcionario;
import servicios.FuncionarioServicio;
import FACADE.SesionFacade;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;

public class FuncionarioConsolaMain extends UIBase {

    private final FuncionarioServicio funcionarioServicio;
    private Funcionario funcionarioActual;
    private String rolActual;

    public FuncionarioConsolaMain() {
        FuncionarioServicio tempService = null;
        try {
            tempService = new FuncionarioServicio();
        } catch (Exception e) {
            mostrarError("Error al inicializar FuncionarioServicio: " + e.getMessage());
        }
        this.funcionarioServicio = tempService;
    }

    @Override
    public void iniciar() {
        SesionSingleton login = SesionSingleton.getInstance();

        if (!login.haySesionActiva()) {
            mostrarError("No hay sesión activa.");
            return;
        }

        funcionarioActual = (Funcionario) login.getUsuarioActual();
        rolActual = login.getRolActual();

        mostrarInfo("Bienvenido/a, " + funcionarioActual.getNombre() + " " + funcionarioActual.getApellido() +
                " (" + rolActual + ")");

        super.iniciar();

        SesionFacade facade = new SesionFacade();
        facade.logout();

        mostrarInfo("Sesión finalizada correctamente. Hasta pronto.");
    }

    @Override
    protected void mostrarMenu() {
        System.out.println("\n===== MENÚ PRINCIPAL - " + rolActual.toUpperCase() + " =====");
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
        System.out.println("20. Gestión de participantes en instancias");
        System.out.println("21. Gestión de informes finales");
        System.out.println("0.  Cerrar sesión");
        System.out.println("=============================================");
    }

    @Override
    protected void manejarOpcion(int opcion) {
        try {
            if (opcion == 0) {
                mostrarInfo("Cerrando sesión de " + rolActual + "...");
                return;
            }

            UIMenu consola = FuncionarioFactory.crearConsolaPorOpcion(opcion);
            consola.iniciar();

        } catch (IllegalArgumentException e) {
            mostrarError("Opción inválida. Intente nuevamente.");
        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

}

