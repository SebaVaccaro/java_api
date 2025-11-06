package consola.FuncionarioConsola;

import consola.InterfazConsola.UIBase;
import PROXY.CiudadProxy;
import SINGLETON.LoginSingleton;
import modelo.Ciudad;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class CiudadConsola extends UIBase {

    private final CiudadProxy proxy;
    private final LoginSingleton loginSingleton;

    // Constructor: inicializa el proxy y la sesión de usuario
    public CiudadConsola() throws Exception {
        this.proxy = new CiudadProxy();
        this.loginSingleton = LoginSingleton.getInstance();
    }

    // Mostrar menú principal de gestión de ciudades
    @Override
    public void mostrarMenu() {
        System.out.println("\n===== GESTIÓN DE CIUDADES =====");
        System.out.println("1. Crear nueva ciudad");
        System.out.println("2. Listar todas las ciudades");
        System.out.println("3. Buscar ciudad por ID");
        System.out.println("4. Buscar ciudad por nombre");
        System.out.println("5. Listar ciudades por departamento");
        System.out.println("6. Modificar ciudad existente");
        System.out.println("7. Eliminar ciudad");
        System.out.println("0. Volver al menú principal");
        System.out.println("================================");
    }


    // Manejar opción seleccionada
    @Override
    public void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> crearCiudad();
            case 2 -> listarTodas();
            case 3 -> buscarPorId();
            case 4 -> buscarPorNombre();
            case 5 -> listarPorDepartamento();
            case 6 -> modificarCiudad();
            case 7 -> eliminarCiudad();
            case 0 -> mostrarInfo("Volviendo al menú principal...");
            default -> mostrarError("Opción inválida.");
        }
    }

    // Crear una nueva ciudad
    private void crearCiudad() {
        if (!loginSingleton.haySesionActiva()) {
            mostrarError("No hay sesión activa.");
            return;
        }

        int codPostal = leerEntero("Código postal: ");
        String nombre = leerTexto("Nombre de la ciudad: ");
        String departamento = leerTexto("Departamento: ");

        try {
            Ciudad ciudad = proxy.crearCiudad(codPostal, nombre, departamento);
            mostrarExito("Ciudad creada con éxito: " + ciudad);
        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al crear ciudad: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
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

    // Modificar ciudad
    private void modificarCiudad() {
        if (!loginSingleton.haySesionActiva()) {
            mostrarError("No hay sesión activa.");
            return;
        }

        int id = leerEntero("ID de la ciudad a modificar: ");

        try {
            Ciudad ciudad = proxy.buscarCiudadPorId(id);

            if (ciudad == null) {
                mostrarInfo("La ciudad no existe.");
                return;
            }

            System.out.println("Ciudad seleccionada:");
            System.out.println(ciudad);
            System.out.println("\nCampos modificables: codPostal, nombre, departamento");

            String campo = leerTexto("Campo a modificar: ");
            boolean exito = false;

            switch (campo.toLowerCase()) {
                case "codpostal" -> {
                    int nuevoCod = leerEntero("Nuevo código postal: ");
                    ciudad.setCodPostal(nuevoCod);
                    exito = proxy.actualizarCiudad(ciudad.getIdCiudad(), ciudad.getCodPostal(), ciudad.getNombre(), ciudad.getDepartamento());
                }
                case "nombre" -> {
                    String nuevoNombre = leerTexto("Nuevo nombre: ");
                    ciudad.setNombre(nuevoNombre);
                    exito = proxy.actualizarCiudad(ciudad.getIdCiudad(), ciudad.getCodPostal(), ciudad.getNombre(), ciudad.getDepartamento());
                }
                case "departamento" -> {
                    String nuevoDep = leerTexto("Nuevo departamento: ");
                    ciudad.setDepartamento(nuevoDep);
                    exito = proxy.actualizarCiudad(ciudad.getIdCiudad(), ciudad.getCodPostal(), ciudad.getNombre(), ciudad.getDepartamento());
                }
                default -> mostrarError("Campo inválido.");
            }

            if (exito)
                mostrarExito("Ciudad modificada correctamente.");
            else
                mostrarError("No se pudo modificar la ciudad.");

        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al modificar ciudad: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Eliminar ciudad
    private void eliminarCiudad() {
        int id = leerEntero("ID de la ciudad a eliminar: ");

        try {
            boolean eliminado = proxy.eliminarCiudad(id);

            if (eliminado)
                mostrarExito("Ciudad eliminada correctamente.");
            else
                mostrarError("No se pudo eliminar la ciudad.");

        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al eliminar ciudad: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }
}
