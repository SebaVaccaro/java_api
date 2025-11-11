package consola.FuncionarioConsola;

import consola.InterfazConsola.UIBase;
import PROXY.ITRProxy;
import modelo.ITR;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class ITRConsola extends UIBase {

    private final ITRProxy proxy;

    // Constructor: inicializa el proxy que maneja las operaciones con ITR
    public ITRConsola() throws Exception {
        this.proxy = new ITRProxy();
    }

    // Muestra el menú principal del módulo de ITR
    @Override
    public void mostrarMenu() {
        System.out.println("\n===== MENÚ ITR =====");
        System.out.println("1. Listar todos");
        System.out.println("2. Buscar por ID");
        System.out.println("0. Volver al menú principal");
        System.out.println("====================");
    }

    // Maneja la opción seleccionada por el usuario
    @Override
    public void manejarOpcion(int opcion) {
        try {
            switch (opcion) {      // Crear un nuevo ITR
                case 1 -> listarTodos();     // Listar todos los ITR
                case 2 -> buscarPorId();    // Eliminar un ITR
                case 0 -> mostrarInfo("Volviendo al menú principal...");
                default -> mostrarError("Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error al ejecutar la opción: " + e.getMessage());
        }
    }



    // Lista todos los ITR registrados
    private void listarTodos() {
        try {
            List<ITR> lista = proxy.listarTodos();
            if (lista.isEmpty()) mostrarInfo("No hay ITRs registrados.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error SQL al listar ITRs: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al listar ITRs: " + e.getMessage());
        }
    }

    // Busca un ITR por su ID
    private void buscarPorId() {
        int idItr = leerEntero("ID del ITR: ");
        try {
            ITR itr = proxy.obtenerITR(idItr);
            if (itr != null) System.out.println(itr);
            else mostrarInfo("ITR no encontrado.");
        } catch (SQLException e) {
            mostrarError("Error SQL al buscar ITR: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al buscar ITR: " + e.getMessage());
        }
    }
}
