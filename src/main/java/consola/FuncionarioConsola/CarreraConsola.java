package consola.FuncionarioConsola;

import consola.InterfazConsola.UIBase;
import PROXY.CarreraProxy;
import SINGLETON.SesionSingleton;
import modelo.Carrera;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class CarreraConsola extends UIBase {

    private final CarreraProxy proxy;
    private final SesionSingleton sesionSingleton;

    // Constructor: inicializa el proxy para manejar las operaciones de carreras
    public CarreraConsola() throws Exception {
        this.proxy = new CarreraProxy();
        this.sesionSingleton = SesionSingleton.getInstance();
    }

    // Mostrar el menú principal de gestión de carreras
    @Override
    public void mostrarMenu() {
        System.out.println("\n===== GESTIÓN DE CARRERAS =====");
        System.out.println("1. Listar carreras activas");
        System.out.println("2. Buscar carrera por ID");
        System.out.println("3. Buscar carrera por código");
        System.out.println("0. Volver al menú principal");
        System.out.println("================================");
    }


    // Manejar la opción elegida por el usuario
    @Override
    public void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> listarActivas();
            case 2 -> buscarPorId();
            case 3 -> buscarPorCodigo();
            case 0 -> mostrarInfo("Volviendo al menú principal...");
            default -> mostrarError("Opción inválida.");
        }
    }



    // Listar todas las carreras activas
    private void listarActivas() {
        try {
            List<Carrera> list = proxy.listarTodas(); // si el servicio diferencia activas, usar listarActivas()

            if (list.isEmpty())
                mostrarInfo("No hay carreras activas.");
            else
                list.forEach(System.out::println);

        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Buscar una carrera por ID
    private void buscarPorId() {
        int id = leerEntero("ID de la carrera: ");
        try {
            Carrera carrera = proxy.buscarCarreraPorId(id);

            if (carrera != null)
                System.out.println(carrera);
            else
                mostrarInfo("No se encontró ninguna carrera con ese ID.");

        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Buscar una carrera por su código
    private void buscarPorCodigo() {
        String codigo = leerTexto("Código de la carrera: ");
        try {
            Carrera carrera = proxy.buscarCarreraPorCodigo(codigo);

            if (carrera != null)
                System.out.println(carrera);
            else
                mostrarInfo("No se encontró ninguna carrera con ese código.");

        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }


}
