package consola.FuncionarioConsola;

import consola.InterfazConsola.UIBase;
import PROXY.GrupoProxy;
import modelo.Grupo;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class GrupoConsola extends UIBase {

    private final GrupoProxy proxy;

    // Constructor: inicializa el proxy encargado de gestionar las operaciones de los grupos
    public GrupoConsola() throws Exception {
        this.proxy = new GrupoProxy();
    }

    // Mostrar el menú principal del módulo de grupos
    @Override
    public void mostrarMenu() {
        System.out.println("\n===== MENÚ GRUPOS =====");
        System.out.println("1. Crear grupo");
        System.out.println("2. Listar todos");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Listar por carrera");
        System.out.println("5. Modificar grupo");
        System.out.println("6. Eliminar grupo");
        System.out.println("0. Volver al menú anterior");
        System.out.println("========================");
    }

    // Manejar la opción seleccionada por el usuario
    @Override
    public void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> crearGrupo();        // Crear nuevo grupo
                case 2 -> listarTodos();       // Listar todos los grupos
                case 3 -> buscarPorId();       // Buscar grupo por ID
                case 4 -> listarPorCarrera();  // Listar grupos por carrera
                case 5 -> modificarGrupo();    // Modificar grupo existente
                case 6 -> eliminarGrupo();     // Eliminar grupo
                case 0 -> mostrarInfo("Volviendo al menú principal...");
                default -> mostrarError("Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error al ejecutar la opción: " + e.getMessage());
        }
    }

    // Crear un nuevo grupo
    private void crearGrupo() {
        String nombre = leerTexto("Nombre del grupo: ");
        int idCarrera = leerEntero("ID de la carrera: ");
        try {
            Grupo g = proxy.crearGrupo(nombre, idCarrera);
            mostrarExito("Grupo creado correctamente: " + g);
        } catch (SecurityException ex) {
            mostrarError(ex.getMessage());
        } catch (SQLException ex) {
            mostrarError("Error SQL al crear grupo: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al crear grupo: " + ex.getMessage());
        }
    }

    // Listar todos los grupos registrados
    private void listarTodos() {
        try {
            List<Grupo> lista = proxy.listarTodos();
            if (lista.isEmpty()) {
                mostrarInfo("No hay grupos registrados.");
            } else {
                lista.forEach(System.out::println);
            }
        } catch (SQLException ex) {
            mostrarError("Error SQL al listar grupos: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al listar grupos: " + ex.getMessage());
        }
    }

    // Buscar un grupo por su ID
    private void buscarPorId() {
        int idGrupo = leerEntero("ID del grupo: ");
        try {
            Grupo g = proxy.obtenerPorId(idGrupo);
            if (g != null) {
                System.out.println(g);
            } else {
                mostrarInfo("Grupo no encontrado.");
            }
        } catch (SQLException ex) {
            mostrarError("Error SQL al buscar grupo: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al buscar grupo: " + ex.getMessage());
        }
    }

    // Listar los grupos de una carrera específica
    private void listarPorCarrera() {
        int idCarrera = leerEntero("ID de la carrera: ");
        try {
            List<Grupo> lista = proxy.listarPorCarrera(idCarrera);
            if (lista.isEmpty()) {
                mostrarInfo("No hay grupos registrados para esta carrera.");
            } else {
                lista.forEach(System.out::println);
            }
        } catch (SQLException ex) {
            mostrarError("Error SQL al listar grupos por carrera: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al listar grupos por carrera: " + ex.getMessage());
        }
    }

    // Modificar los datos de un grupo existente
    private void modificarGrupo() {
        int idGrupo = leerEntero("ID del grupo a modificar: ");
        String nombre = leerTexto("Nuevo nombre: ");
        int idCarrera = leerEntero("Nuevo ID de carrera: ");
        try {
            boolean exito = proxy.actualizarGrupo(idGrupo, nombre, idCarrera);
            if (exito) {
                mostrarExito("Grupo modificado correctamente.");
            } else {
                mostrarError("No se pudo modificar el grupo.");
            }
        } catch (SecurityException ex) {
            mostrarError(ex.getMessage());
        } catch (SQLException ex) {
            mostrarError("Error SQL al modificar grupo: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al modificar grupo: " + ex.getMessage());
        }
    }

    // Eliminar un grupo del sistema
    private void eliminarGrupo() {
        int idGrupo = leerEntero("ID del grupo a eliminar: ");
        try {
            boolean exito = proxy.eliminarGrupo(idGrupo);
            if (exito) {
                mostrarExito("Grupo eliminado correctamente.");
            } else {
                mostrarError("No se pudo eliminar el grupo.");
            }
        } catch (SecurityException ex) {
            mostrarError(ex.getMessage());
        } catch (SQLException ex) {
            mostrarError("Error SQL al eliminar grupo: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al eliminar grupo: " + ex.getMessage());
        }
    }
}
