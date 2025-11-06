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

    // Constructor: inicializa el proxy encargado de las operaciones con informes finales
    public InformeFinalConsola() throws Exception {
        this.proxy = new InformeFinalProxy();
    }

    // Muestra el menú principal del módulo de informes finales
    @Override
    public void mostrarMenu() {
        System.out.println("\n===== MENÚ INFORMES FINALES =====");
        System.out.println("1. Listar todos");
        System.out.println("2. Buscar por ID");
        System.out.println("3. Modificar informe");
        System.out.println("4. Eliminar informe");
        System.out.println("0. Volver al menú principal");
        System.out.println("=================================");
    }

    // Maneja la opción seleccionada por el usuario en el menú
    @Override
    public void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> listarTodos();       // Listar todos los informes
                case 2 -> buscarPorId();       // Buscar informe por ID
                case 3 -> modificarInforme();  // Modificar un informe existente
                case 4 -> eliminarInforme();   // Eliminar un informe
                case 0 -> mostrarInfo("Volviendo al menú principal...");
                default -> mostrarError("Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error al ejecutar la opción: " + e.getMessage());
        }
    }

    // Lista todos los informes finales registrados
    private void listarTodos() {
        try {
            List<InformeFinal> lista = proxy.listarInformes();
            if (lista.isEmpty()) {
                mostrarInfo("No hay informes registrados.");
            } else {
                lista.forEach(System.out::println);
            }
        } catch (SecurityException ex) {
            mostrarError(ex.getMessage());
        } catch (SQLException ex) {
            mostrarError("Error SQL al listar informes: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al listar informes: " + ex.getMessage());
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
                mostrarInfo("Informe no encontrado.");
            }
        } catch (SecurityException ex) {
            mostrarError(ex.getMessage());
        } catch (SQLException ex) {
            mostrarError("Error SQL al buscar informe: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al buscar informe: " + ex.getMessage());
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
                mostrarExito("Informe modificado correctamente.");
            } else {
                mostrarError("No se pudo modificar el informe.");
            }
        } catch (SecurityException ex) {
            mostrarError(ex.getMessage());
        } catch (SQLException ex) {
            mostrarError("Error SQL al modificar informe: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al modificar informe: " + ex.getMessage());
        }
    }

    // Elimina un informe final según su ID
    private void eliminarInforme() {
        int id = leerEntero("ID del informe a eliminar: ");
        try {
            boolean exito = proxy.eliminarInforme(id);
            if (exito) {
                mostrarExito("Informe eliminado correctamente.");
            } else {
                mostrarError("No se pudo eliminar el informe.");
            }
        } catch (SecurityException ex) {
            mostrarError(ex.getMessage());
        } catch (SQLException ex) {
            mostrarError("Error SQL al eliminar informe: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al eliminar informe: " + ex.getMessage());
        }
    }
}
