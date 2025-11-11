package consola.FuncionarioConsola;

import consola.InterfazConsola.UIBase;
import PROXY.CiudadProxy;
import SINGLETON.SesionSingleton;
import modelo.Ciudad;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class CiudadConsola extends UIBase {

    private final CiudadProxy proxy;
    private final SesionSingleton sesionSingleton;

    // Constructor: inicializa el proxy y la sesión de usuario
    public CiudadConsola() throws Exception {
        this.proxy = new CiudadProxy();
        this.sesionSingleton = SesionSingleton.getInstance();
    }

    // Mostrar menú principal de gestión de ciudades
    @Override
    public void mostrarMenu() {
        System.out.println("\n===== GESTIÓN DE CIUDADES =====");
        System.out.println("1. Listar todas las ciudades");
        System.out.println("2. Buscar ciudad por ID");
        System.out.println("3. Buscar ciudad por nombre");
        System.out.println("4. Listar ciudades por departamento");
        System.out.println("0. Volver al menú principal");
        System.out.println("================================");
    }


    // Manejar opción seleccionada
    @Override
    public void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> listarTodas();
            case 2 -> buscarPorId();
            case 3 -> buscarPorNombre();
            case 4 -> listarPorDepartamento();
            case 0 -> mostrarInfo("Volviendo al menú principal...");
            default -> mostrarError("Opción inválida.");
        }
    }


    // Listar todas las ciudades
    private void listarTodas() {
        try {
            List<Ciudad> lista = proxy.listarTodas();

            if (lista.isEmpty())
                mostrarInfo("No hay ciudades registradas.");
            else
                lista.forEach(System.out::println);

        } catch (SQLException e) {
            mostrarError("Error SQL al listar ciudades: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Buscar ciudad por ID
    private void buscarPorId() {
        int id = leerEntero("ID de la ciudad: ");
        try {
            Ciudad ciudad = proxy.buscarCiudadPorId(id);

            if (ciudad != null)
                System.out.println(ciudad);
            else
                mostrarInfo("No se encontró ninguna ciudad con ese ID.");

        } catch (SQLException e) {
            mostrarError("Error SQL al buscar ciudad: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Buscar ciudad por nombre
    private void buscarPorNombre() {
        String nombre = leerTexto("Nombre de la ciudad: ");
        try {
            Ciudad ciudad = proxy.buscarCiudadPorNombre(nombre);

            if (ciudad != null)
                System.out.println(ciudad);
            else
                mostrarInfo("No se encontró ninguna ciudad con ese nombre.");

        } catch (SQLException e) {
            mostrarError("Error SQL al buscar ciudad: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Listar ciudades por departamento
    private void listarPorDepartamento() {
        String departamento = leerTexto("Departamento: ");
        try {
            List<Ciudad> lista = proxy.listarPorDepartamento(departamento);

            if (lista.isEmpty())
                mostrarInfo("No hay ciudades registradas en ese departamento.");
            else
                lista.forEach(System.out::println);

        } catch (SQLException e) {
            mostrarError("Error SQL al listar ciudades por departamento: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }
}
