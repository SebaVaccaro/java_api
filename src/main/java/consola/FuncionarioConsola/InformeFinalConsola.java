package consola.FuncionarioConsola;

import PROXY.InformeFinalProxy;
import consola.InterfazConsola.UIBase;
import modelo.InformeFinal;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class InformeFinalConsola extends UIBase {

    private final InformeFinalProxy proxy;

    // Constructor: inicializa el proxy que gestiona las operaciones con informes finales
    public InformeFinalConsola() throws Exception {
        this.proxy = new InformeFinalProxy();
    }

    // Muestra el menú principal del módulo de informes finales
    @Override
    public void mostrarMenu() {
        System.out.println("\n--- MENÚ INFORMES FINALES ---");
        System.out.println("1. Crear informe");
        System.out.println("2. Listar todos");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Modificar informe");
        System.out.println("5. Eliminar informe");
        System.out.println("0. Volver al menú principal");
    }

    // Maneja la opción seleccionada por el usuario en el menú
    @Override
    public void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> crearInforme();      // Crear un nuevo informe
            case 2 -> listarTodos();       // Listar todos los informes
            case 3 -> buscarPorId();       // Buscar informe por ID
            case 4 -> modificarInforme();  // Modificar un informe existente
            case 5 -> eliminarInforme();   // Eliminar un informe
            case 0 -> System.out.println("Volviendo al menú principal...");
            default -> mostrarError("Opción inválida.");
        }
    }

    // Crea un nuevo informe final solicitando los datos al usuario
    private void crearInforme() {
        String contenido = leerTexto("Contenido: ");
        int valoracion = leerEntero("Valoración (0-100): ");
        LocalDate fecha = leerFecha("Fecha de creación (YYYY-MM-DD): ");
        int idUsuarioPropietario = leerEntero("ID del usuario propietario: ");

        try {
            InformeFinal i = proxy.crearInforme(contenido, valoracion, fecha, idUsuarioPropietario);
            mostrarExito("Informe creado: " + i);
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al crear informe: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al crear informe: " + e.getMessage());
        }
    }

    // Lista todos los informes finales existentes
    private void listarTodos() {
        try {
            List<InformeFinal> lista = proxy.listarInformes();
            if (lista.isEmpty()) {
                mostrarInfo("No hay informes registrados.");
            } else {
                lista.forEach(System.out::println);
            }
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al listar informes: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al listar informes: " + e.getMessage());
        }
    }

    // Busca un informe final según su ID
    private void buscarPorId() {
        int id = leerEntero("ID del informe: ");
        try {
            InformeFinal i = proxy.obtenerInforme(id);
            if (i != null) {
                System.out.println(i);
            } else {
                mostrarError("Informe no encontrado.");
            }
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al buscar informe: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al buscar informe: " + e.getMessage());
        }
    }

    // Modifica los datos de un informe existente
    private void modificarInforme() {
        int id = leerEntero("ID del informe a modificar: ");
        String contenido = leerTexto("Nuevo contenido: ");
        int valoracion = leerEntero("Nueva valoración (0-100): ");
        LocalDate fecha = leerFecha("Nueva fecha de creación (YYYY-MM-DD): ");

        try {
            boolean exito = proxy.actualizarInforme(id, contenido, valoracion, fecha);
            if (exito) {
                mostrarExito("Informe modificado.");
            } else {
                mostrarError("No se pudo modificar el informe.");
            }
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al modificar informe: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al modificar informe: " + e.getMessage());
        }
    }

    // Elimina un informe final según su ID
    private void eliminarInforme() {
        int id = leerEntero("ID del informe a eliminar: ");
        try {
            boolean exito = proxy.eliminarInforme(id);
            if (exito) {
                mostrarExito("Informe eliminado.");
            } else {
                mostrarError("No se pudo eliminar el informe.");
            }
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al eliminar informe: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al eliminar informe: " + e.getMessage());
        }
    }
}
