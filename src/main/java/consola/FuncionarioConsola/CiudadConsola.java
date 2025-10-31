package consola.FuncionarioConsola;

import consola.InterfazConsola.UIBase;
import PROXY.CiudadProxy;
import modelo.Ciudad;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class CiudadConsola extends UIBase {

    private final CiudadProxy proxy;

    public CiudadConsola() throws Exception {
        this.proxy = new CiudadProxy();
    }

    // ==========================================================
    // MENÚ PRINCIPAL
    // ==========================================================
    @Override
    public void mostrarMenu() {
        System.out.println("\n===== MENÚ CIUDADES =====");
        System.out.println("1. Crear ciudad");
        System.out.println("2. Listar todas");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Buscar por nombre");
        System.out.println("5. Listar por departamento");
        System.out.println("6. Modificar ciudad");
        System.out.println("7. Eliminar ciudad");
        System.out.println("0. Volver al menú anterior");
        System.out.println("===========================");
    }

    @Override
    public void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> crearCiudad();
                case 2 -> listarTodas();
                case 3 -> buscarPorId();
                case 4 -> buscarPorNombre();
                case 5 -> listarPorDepartamento();
                case 6 -> modificarCiudad();
                case 7 -> eliminarCiudad();
                case 0 -> mostrarInfo("Volviendo al menú principal...");
                default -> mostrarError("Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error al ejecutar la opción: " + e.getMessage());
        }
    }

    // ==========================================================
    // CRUD DE CIUDADES
    // ==========================================================

    private void crearCiudad() {
        int codPostal = leerEntero("Código postal: ");
        String nombre = leerTexto("Nombre de la ciudad: ");
        String departamento = leerTexto("Departamento: ");
        try {
            Ciudad c = proxy.crearCiudad(codPostal, nombre, departamento);
            mostrarExito("Ciudad creada: " + c);
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al crear ciudad: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al crear ciudad: " + e.getMessage());
        }
    }

    private void listarTodas() {
        try {
            List<Ciudad> lista = proxy.listarTodas();
            if (lista.isEmpty()) mostrarInfo("No hay ciudades registradas.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error al listar ciudades: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void buscarPorId() {
        int id = leerEntero("ID de la ciudad: ");
        try {
            Ciudad c = proxy.buscarCiudadPorId(id);
            if (c != null) System.out.println(c);
            else mostrarInfo("Ciudad no encontrada.");
        } catch (SQLException e) {
            mostrarError("Error al buscar ciudad: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void buscarPorNombre() {
        String nombre = leerTexto("Nombre de la ciudad: ");
        try {
            Ciudad c = proxy.buscarCiudadPorNombre(nombre);
            if (c != null) System.out.println(c);
            else mostrarInfo("Ciudad no encontrada.");
        } catch (SQLException e) {
            mostrarError("Error al buscar ciudad: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void listarPorDepartamento() {
        String departamento = leerTexto("Departamento: ");
        try {
            List<Ciudad> lista = proxy.listarPorDepartamento(departamento);
            if (lista.isEmpty()) mostrarInfo("No hay ciudades en este departamento.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error al listar ciudades por departamento: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void modificarCiudad() {
        int id = leerEntero("ID de la ciudad a modificar: ");
        mostrarInfo("Campos modificables: codPostal, nombre, departamento");
        String campo = leerTexto("Campo a modificar: ");

        try {
            boolean exito = switch (campo.toLowerCase()) {
                case "codpostal" -> proxy.actualizarCodPostal(id, leerEntero("Nuevo código postal: "));
                case "nombre" -> proxy.actualizarNombre(id, leerTexto("Nuevo nombre: "));
                case "departamento" -> proxy.actualizarDepartamento(id, leerTexto("Nuevo departamento: "));
                default -> {
                    mostrarError("Campo inválido.");
                    yield false;
                }
            };

            if (exito) mostrarExito("Ciudad modificada correctamente.");
            else mostrarError("No se pudo modificar la ciudad.");

        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al modificar ciudad: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al modificar ciudad: " + e.getMessage());
        }
    }

    private void eliminarCiudad() {
        int id = leerEntero("ID de la ciudad a eliminar: ");
        try {
            boolean exito = proxy.eliminarCiudad(id);
            if (exito) mostrarExito("Ciudad eliminada correctamente.");
            else mostrarError("No se pudo eliminar la ciudad.");
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al eliminar ciudad: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al eliminar ciudad: " + e.getMessage());
        }
    }
}

