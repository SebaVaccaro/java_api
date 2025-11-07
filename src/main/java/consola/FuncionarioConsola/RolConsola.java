package consola.FuncionarioConsola;

import PROXY.RolProxy;
import modelo.Rol;
import consola.InterfazConsola.UIBase;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class RolConsola extends UIBase {

    private final RolProxy proxy;

    // Constructor: inicializa el proxy
    public RolConsola() throws SQLException {
        this.proxy = new RolProxy();
    }

    // Muestra el menú principal del módulo de roles
    @Override
    public void mostrarMenu() {
        System.out.println("\n===== GESTIÓN DE ROLES =====");
        System.out.println("4. Listar todos los roles");
        System.out.println("5. Buscar rol por ID");
        System.out.println("0. Volver al menú principal");
        System.out.println("============================");
    }

    // Maneja la opción seleccionada por el usuario
    @Override
    public void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 4 -> listarTodos();
                case 5 -> buscarPorId();
                case 0 -> mostrarInfo("Volviendo al menú principal...");
                default -> mostrarError("Opción no válida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }



    // Lista todos los roles registrados
    private void listarTodos() {
        try {
            List<Rol> roles = proxy.listarTodos();
            if (roles.isEmpty()) mostrarInfo("No hay roles registrados.");
            else roles.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error SQL al listar roles: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado al listar roles: " + e.getMessage());
        }
    }

    // Busca y muestra un rol por su ID
    private void buscarPorId() {
        int idRol = leerEntero("ID del rol: ");

        try {
            Rol rol = proxy.buscarPorId(idRol);
            if (rol != null) System.out.println(rol);
            else mostrarError("No se encontró un rol con ese ID.");
        } catch (SQLException e) {
            mostrarError("Error SQL al buscar rol: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado al buscar rol: " + e.getMessage());
        }
    }
}
