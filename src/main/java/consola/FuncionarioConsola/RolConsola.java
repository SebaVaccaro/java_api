package consola.FuncionarioConsola;

import PROXY.RolProxy;
import modelo.Rol;
import consola.InterfazConsola.UIBase;

import java.sql.SQLException;
import java.util.List;

public class RolConsola extends UIBase {

    private final RolProxy rolProxy;

    // Constructor: inicializa el proxy
    public RolConsola() throws SQLException {
        this.rolProxy = new RolProxy();
    }

    // Muestra el menú principal del módulo de roles
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

    // Maneja la opción seleccionada por el usuario
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

    // Crea un nuevo rol
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

    // Actualiza un rol existente
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

    // Elimina un rol por su ID
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

    // Lista todos los roles registrados
    private void listarTodos() {
        try {
            List<Rol> roles = rolProxy.listarTodos();
            if (roles.isEmpty()) mostrarInfo("No hay roles registrados.");
            else roles.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error al listar roles: " + e.getMessage());
        }
    }

    // Busca y muestra un rol por su ID
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

    // Permite ejecutar directamente la consola de roles
    public static void main(String[] args) throws SQLException {
        RolConsola ui = new RolConsola();
        ui.iniciar();
    }
}

