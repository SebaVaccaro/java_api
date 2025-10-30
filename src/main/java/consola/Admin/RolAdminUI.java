package consola.Admin;

import PROXY.RolProxy;
import modelo.Rol;

import java.sql.SQLException;
import java.util.List;

import consola.interfaz.UIBase;

public class RolAdminUI extends UIBase {

    private final RolProxy rolProxy;

    public RolAdminUI() throws SQLException {
        this.rolProxy = new RolProxy();
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n=== Gestión de Roles ===");
        System.out.println("1. Agregar rol");
        System.out.println("2. Actualizar rol");
        System.out.println("3. Eliminar rol");
        System.out.println("4. Listar todos los roles");
        System.out.println("5. Buscar rol por ID");
        System.out.println("0. Salir");
    }

    @Override
    public void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> agregarRol();
            case 2 -> actualizarRol();
            case 3 -> eliminarRol();
            case 4 -> listarTodos();
            case 5 -> buscarPorId();
            case 0 -> mostrarInfo("Saliendo del menú de roles...");
            default -> mostrarError("Opción no válida.");
        }
    }

    private void agregarRol() {
        String nombre = leerTexto("Nombre del rol: ");
        boolean estActivo = leerBoolean("¿Activo? (true/false): ");

        try {
            boolean exito = rolProxy.agregarRol(nombre, estActivo);
            if (exito) mostrarExito("Rol agregado correctamente.");
            else mostrarError("No se pudo agregar el rol.");
        } catch (SQLException e) {
            mostrarError("Error de base de datos: " + e.getMessage());
        }
    }

    private void actualizarRol() {
        int idRol = leerEntero("ID del rol a actualizar: ");
        String nombre = leerTexto("Nuevo nombre: ");
        boolean estActivo = leerBoolean("¿Activo? (true/false): ");

        try {
            boolean exito = rolProxy.actualizarRol(idRol, nombre, estActivo);
            if (exito) mostrarExito("Rol actualizado correctamente.");
            else mostrarError("No se pudo actualizar el rol.");
        } catch (SQLException e) {
            mostrarError("Error de base de datos: " + e.getMessage());
        }
    }

    private void eliminarRol() {
        int idRol = leerEntero("ID del rol a eliminar: ");
        try {
            boolean exito = rolProxy.eliminarRol(idRol);
            if (exito) mostrarExito("Rol eliminado correctamente.");
            else mostrarError("No se pudo eliminar el rol.");
        } catch (SQLException e) {
            mostrarError("Error de base de datos: " + e.getMessage());
        }
    }

    private void listarTodos() {
        try {
            List<Rol> roles = rolProxy.listarTodos();
            if (roles.isEmpty()) mostrarInfo("No hay roles registrados.");
            else roles.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error al listar roles: " + e.getMessage());
        }
    }

    private void buscarPorId() {
        int idRol = leerEntero("ID del rol: ");
        try {
            Rol rol = rolProxy.buscarPorId(idRol);
            if (rol != null) System.out.println(rol);
            else mostrarError("Rol no encontrado.");
        } catch (SQLException e) {
            mostrarError("Error de base de datos: " + e.getMessage());
        }
    }
}
