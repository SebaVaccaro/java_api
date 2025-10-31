package consola.FuncionarioConsola;

import consola.InterfazConsola.UIBase;
import PROXY.CiudadProxy;
import modelo.Ciudad;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class CiudadConsola extends UIBase {

    private final CiudadProxy proxy;

    // Constructor: inicializa el proxy que maneja las operaciones de ciudades
    public CiudadConsola() throws Exception {
        this.proxy = new CiudadProxy();
    }

    // Mostrar el menú principal de gestión de ciudades
    @Override
    public void mostrarMenu() {
        System.out.println("\n🏙️ MENÚ DE GESTIÓN DE CIUDADES");
        System.out.println("1. Crear ciudad");
        System.out.println("2. Listar todas");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Buscar por nombre");
        System.out.println("5. Listar por departamento");
        System.out.println("6. Modificar ciudad");
        System.out.println("7. Eliminar ciudad");
        System.out.println("0. Volver al menú anterior");
    }

    // Manejar la opción seleccionada por el usuario
    @Override
    public void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> crearCiudad();           // Crear una nueva ciudad
                case 2 -> listarTodas();           // Listar todas las ciudades registradas
                case 3 -> buscarPorId();           // Buscar una ciudad por su ID
                case 4 -> buscarPorNombre();       // Buscar una ciudad por su nombre
                case 5 -> listarPorDepartamento(); // Listar ciudades por departamento
                case 6 -> modificarCiudad();       // Modificar los datos de una ciudad
                case 7 -> eliminarCiudad();        // Eliminar (desactivar) una ciudad
                case 0 -> mostrarInfo("Volviendo al menú principal...");
                default -> mostrarError("Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error al ejecutar la opción: " + e.getMessage());
        }
    }

    // Crear una nueva ciudad con código postal, nombre y departamento
    private void crearCiudad() {
        int codPostal = leerEntero("Código postal: ");
        String nombre = leerTexto("Nombre de la ciudad: ");
        String departamento = leerTexto("Departamento: ");

        try {
            Ciudad c = proxy.crearCiudad(codPostal, nombre, departamento);
            mostrarExito("✅ Ciudad creada con éxito: " + c);
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al crear ciudad: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al crear ciudad: " + e.getMessage());
        }
    }

    // Listar todas las ciudades registradas en el sistema
    private void listarTodas() {
        try {
            List<Ciudad> lista = proxy.listarTodas();

            if (lista.isEmpty())
                mostrarInfo("No hay ciudades registradas.");
            else
                lista.forEach(System.out::println);

        } catch (SQLException e) {
            mostrarError("Error al listar ciudades: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // Buscar una ciudad por su ID
    private void buscarPorId() {
        int id = leerEntero("ID de la ciudad: ");
        try {
            Ciudad c = proxy.buscarCiudadPorId(id);
            if (c != null)
                System.out.println(c);
            else
                mostrarInfo("Ciudad no encontrada.");
        } catch (SQLException e) {
            mostrarError("Error al buscar ciudad: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // Buscar una ciudad por su nombre
    private void buscarPorNombre() {
        String nombre = leerTexto("Nombre de la ciudad: ");
        try {
            Ciudad c = proxy.buscarCiudadPorNombre(nombre);
            if (c != null)
                System.out.println(c);
            else
                mostrarInfo("Ciudad no encontrada.");
        } catch (SQLException e) {
            mostrarError("Error al buscar ciudad: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // Listar todas las ciudades pertenecientes a un departamento específico
    private void listarPorDepartamento() {
        String departamento = leerTexto("Departamento: ");
        try {
            List<Ciudad> lista = proxy.listarPorDepartamento(departamento);

            if (lista.isEmpty())
                mostrarInfo("No hay ciudades registradas en este departamento.");
            else
                lista.forEach(System.out::println);

        } catch (SQLException e) {
            mostrarError("Error al listar ciudades por departamento: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // Modificar los datos de una ciudad (código postal, nombre o departamento)
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

            if (exito)
                mostrarExito("✅ Ciudad modificada correctamente.");
            else
                mostrarError("No se pudo modificar la ciudad.");

        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al modificar ciudad: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al modificar ciudad: " + e.getMessage());
        }
    }

    // Eliminar (desactivar) una ciudad del sistema
    private void eliminarCiudad() {
        int id = leerEntero("ID de la ciudad a eliminar: ");
        try {
            boolean exito = proxy.eliminarCiudad(id);

            if (exito)
                mostrarExito("✅ Ciudad eliminada correctamente.");
            else
                mostrarError("No se pudo eliminar la ciudad.");

        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al eliminar ciudad: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al eliminar ciudad: " + e.getMessage());
        }
    }
}
