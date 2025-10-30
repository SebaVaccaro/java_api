package consola.Admin;

import PROXY.CarreraProxy;
import consola.interfaz.UIBase;
import modelo.Carrera;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class CarreraAdminUI extends UIBase {

    private final CarreraProxy proxy;

    public CarreraAdminUI() throws Exception {
        this.proxy = new CarreraProxy();
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n===== MENÚ CARRERAS =====");
        System.out.println("1. Crear carrera");
        System.out.println("2. Listar todas");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Buscar por código");
        System.out.println("5. Modificar carrera");
        System.out.println("6. Eliminar carrera");
        System.out.println("0. Volver al menú anterior");
        System.out.println("===========================");
    }

    @Override
    public void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> crearCarrera();
                case 2 -> listarTodas();
                case 3 -> buscarPorId();
                case 4 -> buscarPorCodigo();
                case 5 -> modificarCarrera();
                case 6 -> eliminarCarrera();
                case 0 -> mostrarInfo("Volviendo al menú principal...");
                default -> mostrarError("Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error al ejecutar la opción: " + e.getMessage());
        }
    }

    // ==========================================================
    // OPERACIONES CRUD
    // ==========================================================

    private void crearCarrera() {
        String codigo = leerTexto("Código de la carrera: ");
        String nombre = leerTexto("Nombre de la carrera: ");
        String plan = leerTexto("Plan de estudio: ");

        try {
            Carrera c = proxy.crearCarrera(codigo, nombre, plan);
            mostrarInfo("✅ Carrera creada: " + c);
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al crear carrera: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al crear carrera: " + e.getMessage());
        }
    }

    private void listarTodas() {
        try {
            List<Carrera> lista = proxy.listarTodas();
            if (lista.isEmpty()) mostrarInfo("No hay carreras registradas.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error al listar carreras: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void buscarPorId() {
        int id = leerEntero("ID de la carrera: ");
        try {
            Carrera c = proxy.buscarCarreraPorId(id);
            if (c != null) System.out.println(c);
            else mostrarInfo("Carrera no encontrada.");
        } catch (SQLException e) {
            mostrarError("Error al buscar carrera: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void buscarPorCodigo() {
        String codigo = leerTexto("Código de la carrera: ");
        try {
            Carrera c = proxy.buscarCarreraPorCodigo(codigo);
            if (c != null) System.out.println(c);
            else mostrarInfo("Carrera no encontrada.");
        } catch (SQLException e) {
            mostrarError("Error al buscar carrera: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void modificarCarrera() {
        int id = leerEntero("ID de la carrera a modificar: ");
        String codigo = leerTexto("Nuevo código: ");
        String nombre = leerTexto("Nuevo nombre: ");
        String plan = leerTexto("Nuevo plan de estudio: ");

        try {
            boolean exito = proxy.actualizarCarrera(id, codigo, nombre, plan);
            if (exito) mostrarInfo("✅ Carrera modificada.");
            else mostrarError("No se pudo modificar la carrera.");
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al modificar carrera: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al modificar carrera: " + e.getMessage());
        }
    }

    private void eliminarCarrera() {
        int id = leerEntero("ID de la carrera a eliminar: ");
        try {
            boolean exito = proxy.eliminarCarrera(id);
            if (exito) mostrarInfo("✅ Carrera eliminada.");
            else mostrarError("No se pudo eliminar la carrera.");
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al eliminar carrera: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al eliminar carrera: " + e.getMessage());
        }
    }
}
