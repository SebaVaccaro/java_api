package consola.FuncionarioConsola;

import PROXY.CarreraProxy;
import consola.InterfazConsola.UIBase;
import modelo.Carrera;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class CarreraConsola extends UIBase {

    private final CarreraProxy proxy;

    // Constructor: inicializa el proxy que maneja las operaciones sobre carreras
    public CarreraConsola() throws Exception {
        this.proxy = new CarreraProxy();
    }

    // Mostrar el menú principal de gestión de carreras
    @Override
    public void mostrarMenu() {
        System.out.println("\n🎓 MENÚ DE GESTIÓN DE CARRERAS");
        System.out.println("1. Crear carrera");
        System.out.println("2. Listar todas");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Buscar por código");
        System.out.println("5. Modificar carrera");
        System.out.println("6. Eliminar carrera");
        System.out.println("0. Volver al menú anterior");
    }

    // Manejar la opción seleccionada por el usuario
    @Override
    public void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> crearCarrera();       // Crear una nueva carrera
                case 2 -> listarTodas();        // Listar todas las carreras registradas
                case 3 -> buscarPorId();        // Buscar una carrera por su ID
                case 4 -> buscarPorCodigo();    // Buscar una carrera por su código
                case 5 -> modificarCarrera();   // Modificar los datos de una carrera
                case 6 -> eliminarCarrera();    // Eliminar (desactivar) una carrera
                case 0 -> mostrarInfo("Volviendo al menú principal...");
                default -> mostrarError("Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error al ejecutar la opción: " + e.getMessage());
        }
    }

    // Crear una nueva carrera con código, nombre y plan de estudio
    private void crearCarrera() {
        String codigo = leerTexto("Código de la carrera: ");
        String nombre = leerTexto("Nombre de la carrera: ");
        String plan = leerTexto("Plan de estudio: ");

        try {
            Carrera c = proxy.crearCarrera(codigo, nombre, plan);
            mostrarInfo("✅ Carrera creada con éxito: " + c);
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al crear carrera: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al crear carrera: " + e.getMessage());
        }
    }

    // Listar todas las carreras registradas en el sistema
    private void listarTodas() {
        try {
            List<Carrera> lista = proxy.listarTodas();

            if (lista.isEmpty())
                mostrarInfo("No hay carreras registradas.");
            else
                lista.forEach(System.out::println);

        } catch (SQLException e) {
            mostrarError("Error al listar carreras: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // Buscar y mostrar una carrera por su ID
    private void buscarPorId() {
        int id = leerEntero("ID de la carrera: ");
        try {
            Carrera c = proxy.buscarCarreraPorId(id);
            if (c != null)
                System.out.println(c);
            else
                mostrarInfo("Carrera no encontrada.");
        } catch (SQLException e) {
            mostrarError("Error al buscar carrera: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // Buscar y mostrar una carrera por su código
    private void buscarPorCodigo() {
        String codigo = leerTexto("Código de la carrera: ");
        try {
            Carrera c = proxy.buscarCarreraPorCodigo(codigo);
            if (c != null)
                System.out.println(c);
            else
                mostrarInfo("Carrera no encontrada.");
        } catch (SQLException e) {
            mostrarError("Error al buscar carrera: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // Modificar los datos de una carrera existente
    private void modificarCarrera() {
        int id = leerEntero("ID de la carrera a modificar: ");
        String codigo = leerTexto("Nuevo código: ");
        String nombre = leerTexto("Nuevo nombre: ");
        String plan = leerTexto("Nuevo plan de estudio: ");

        try {
            boolean exito = proxy.actualizarCarrera(id, codigo, nombre, plan);

            if (exito)
                mostrarInfo("✅ Carrera modificada correctamente.");
            else
                mostrarError("No se pudo modificar la carrera.");

        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al modificar carrera: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al modificar carrera: " + e.getMessage());
        }
    }

    // Eliminar (desactivar) una carrera del sistema
    private void eliminarCarrera() {
        int id = leerEntero("ID de la carrera a eliminar: ");
        try {
            boolean exito = proxy.eliminarCarrera(id);

            if (exito)
                mostrarInfo("✅ Carrera eliminada correctamente.");
            else
                mostrarError("No se pudo eliminar la carrera.");

        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al eliminar carrera: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al eliminar carrera: " + e.getMessage());
        }
    }
}
