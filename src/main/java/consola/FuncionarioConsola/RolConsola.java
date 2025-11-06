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
        System.out.println("1. Agregar rol");
        System.out.println("2. Actualizar rol");
        System.out.println("3. Eliminar rol");
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
                case 1 -> agregarRol();
                case 2 -> actualizarRol();
                case 3 -> eliminarRol();
                case 4 -> listarTodos();
                case 5 -> buscarPorId();
                case 0 -> mostrarInfo("Volviendo al menú principal...");
                default -> mostrarError("Opción no válida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Crea un nuevo rol
    private void agregarRol() {
        String nombre = leerTexto("Nombre del rol: ");
        boolean activo = leerBoolean("¿Activo? (true/false): ");

        try {
            boolean exito = proxy.agregarRol(nombre, activo);
            if (exito) mostrarExito("Rol agregado correctamente.");
            else mostrarError("No se pudo agregar el rol.");
        } catch (SQLException e) {
            mostrarError("Error SQL al agregar rol: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado al agregar rol: " + e.getMessage());
        }
    }

    // Actualiza un rol existente
    private void actualizarRol() {
        int idRol = leerEntero("ID del rol a actualizar: ");
        String nuevoNombre = leerTexto("Nuevo nombre: ");
        boolean activo = leerBoolean("¿Activo? (true/false): ");

        try {
            boolean exito = proxy.actualizarRol(idRol, nuevoNombre, activo);
            if (exito) mostrarExito("Rol actualizado correctamente.");
            else mostrarError("No se pudo actualizar el rol.");
        } catch (SQLException e) {
            mostrarError("Error SQL al actualizar rol: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado al actualizar rol: " + e.getMessage());
        }
    }

    // Elimina un rol por su ID
    private void eliminarRol() {
        int idRol = leerEntero("ID del rol a eliminar: ");

        try {
            boolean exito = proxy.eliminarRol(idRol);
            if (exito) mostrarExito("Rol eliminado correctamente.");
            else mostrarError("No se pudo eliminar el rol.");
        } catch (SQLException e) {
            mostrarError("Error SQL al eliminar rol: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado al eliminar rol: " + e.getMessage());
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
