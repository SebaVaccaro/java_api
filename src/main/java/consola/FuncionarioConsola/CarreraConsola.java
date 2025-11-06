package consola.FuncionarioConsola;

import consola.InterfazConsola.UIBase;
import PROXY.CarreraProxy;
import SINGLETON.LoginSingleton;
import modelo.Carrera;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class CarreraConsola extends UIBase {

    private final CarreraProxy proxy;
    private final LoginSingleton loginSingleton;

    // Constructor: inicializa el proxy para manejar las operaciones de carreras
    public CarreraConsola() throws Exception {
        this.proxy = new CarreraProxy();
        this.loginSingleton = LoginSingleton.getInstance();
    }

    // Mostrar el menú principal de gestión de carreras
    @Override
    public void mostrarMenu() {
        System.out.println("\n===== GESTIÓN DE CARRERAS =====");
        System.out.println("1. Crear nueva carrera");
        System.out.println("2. Listar carreras activas");
        System.out.println("3. Buscar carrera por ID");
        System.out.println("4. Buscar carrera por código");
        System.out.println("5. Modificar carrera existente");
        System.out.println("6. Eliminar carrera");
        System.out.println("0. Volver al menú principal");
        System.out.println("================================");
    }


    // Manejar la opción elegida por el usuario
    @Override
    public void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> crearCarrera();
            case 2 -> listarActivas();
            case 3 -> buscarPorId();
            case 4 -> buscarPorCodigo();
            case 5 -> modificarCarrera();
            case 6 -> eliminarCarrera();
            case 0 -> mostrarInfo("Volviendo al menú principal...");
            default -> mostrarError("Opción inválida.");
        }
    }

    // Crear una nueva carrera
    private void crearCarrera() {
        if (!loginSingleton.haySesionActiva()) {
            mostrarError("No hay sesión activa.");
            return;
        }

        String codigo = leerTexto("Código de la carrera: ");
        String nombre = leerTexto("Nombre: ");
        String plan = leerTexto("Plan de estudio: ");

        try {
            Carrera carrera = proxy.crearCarrera(codigo, nombre, plan);
            mostrarExito("Carrera creada con éxito: " + carrera);
        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al crear carrera: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
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
            mostrarError("Error SQL al listar carreras: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
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
            mostrarError("Error SQL al buscar carrera: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
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
            mostrarError("Error SQL al buscar carrera: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Modificar una carrera existente
    private void modificarCarrera() {
        if (!loginSingleton.haySesionActiva()) {
            mostrarError("No hay sesión activa.");
            return;
        }

        int id = leerEntero("ID de la carrera a modificar: ");

        try {
            Carrera carrera = proxy.buscarCarreraPorId(id);

            if (carrera == null) {
                mostrarInfo("La carrera no existe.");
                return;
            }

            System.out.println("Carrera seleccionada:");
            System.out.println(carrera);
            System.out.println("\nCampos modificables: código, nombre, plan");

            String campo = leerTexto("Campo a modificar: ");
            boolean exito = false;

            switch (campo.toLowerCase()) {
                case "codigo" -> {
                    String nuevoCodigo = leerTexto("Nuevo código: ");
                    carrera.setCodigo(nuevoCodigo);
                    exito = proxy.actualizarCarrera(carrera.getIdCarrera(), carrera.getCodigo(), carrera.getNombre(), carrera.getPlan());
                }
                case "nombre" -> {
                    String nuevoNombre = leerTexto("Nuevo nombre: ");
                    carrera.setNombre(nuevoNombre);
                    exito = proxy.actualizarCarrera(carrera.getIdCarrera(), carrera.getCodigo(), carrera.getNombre(), carrera.getPlan());
                }
                case "plan" -> {
                    String nuevoPlan = leerTexto("Nuevo plan de estudio: ");
                    carrera.setPlan(nuevoPlan);
                    exito = proxy.actualizarCarrera(carrera.getIdCarrera(), carrera.getCodigo(), carrera.getNombre(), carrera.getPlan());
                }
                default -> mostrarError("Campo inválido.");
            }

            if (exito)
                mostrarExito("Carrera modificada correctamente.");
            else
                mostrarError("No se pudo modificar la carrera.");

        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al modificar carrera: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Eliminar (desactivar) una carrera
    private void eliminarCarrera() {
        int id = leerEntero("ID de la carrera a eliminar: ");

        try {
            boolean eliminado = proxy.eliminarCarrera(id);

            if (eliminado)
                mostrarExito("Carrera eliminada correctamente.");
            else
                mostrarError("No se pudo eliminar la carrera.");

        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al eliminar carrera: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }
}
