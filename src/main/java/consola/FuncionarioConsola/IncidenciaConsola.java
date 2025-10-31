package consola.FuncionarioConsola;

import consola.InterfazConsola.UIBase;
import PROXY.IncidenciaProxy;
import modelo.Incidencia;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

public class IncidenciaConsola extends UIBase {

    private final IncidenciaProxy proxy;

    // Constructor: inicializa el proxy que gestiona las operaciones relacionadas con incidencias
    public IncidenciaConsola() throws Exception {
        this.proxy = new IncidenciaProxy();
    }

    // Muestra el menú principal del módulo de incidencias
    @Override
    public void mostrarMenu() {
        System.out.println("\n===== MENÚ INCIDENCIAS =====");
        System.out.println("1. Crear incidencia");
        System.out.println("2. Listar todas");
        System.out.println("3. Buscar por instancia");
        System.out.println("4. Listar por funcionario");
        System.out.println("5. Modificar incidencia");
        System.out.println("6. Eliminar incidencia");
        System.out.println("0. Volver al menú principal");
        System.out.println("=============================");
    }

    // Maneja la opción seleccionada por el usuario en el menú de incidencias
    @Override
    public void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> crearIncidencia();        // Crea una nueva incidencia
                case 2 -> listarTodas();            // Lista todas las incidencias
                case 3 -> buscarPorInstancia();     // Busca una incidencia por ID de instancia
                case 4 -> listarPorFuncionario();   // Lista incidencias según el funcionario
                case 5 -> modificarIncidencia();    // Modifica una incidencia existente
                case 6 -> eliminarIncidencia();     // Elimina una incidencia
                case 0 -> mostrarInfo("Volviendo al menú principal...");
                default -> mostrarError("Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error al ejecutar la opción: " + e.getMessage());
        }
    }

    // Crea una nueva incidencia solicitando los datos al usuario
    private void crearIncidencia() {
        String titulo = leerTexto("Título: ");
        OffsetDateTime fecha = leerFechaHora("Fecha y hora (YYYY-MM-DDTHH:MM): ");
        String descripcion = leerTexto("Descripción: ");
        boolean activo = leerBoolean("¿Está activa? (true/false): ");
        int idFuncionario = leerEntero("ID del funcionario: ");
        String lugar = leerTexto("Lugar: ");

        try {
            Incidencia incidencia = proxy.crearIncidencia(titulo, fecha, descripcion, activo, idFuncionario, lugar);
            mostrarExito("Incidencia creada: " + incidencia);
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al crear incidencia: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al crear incidencia: " + e.getMessage());
        }
    }

    // Lista todas las incidencias registradas en el sistema
    private void listarTodas() {
        try {
            List<Incidencia> lista = proxy.listarIncidencias();
            if (lista.isEmpty()) mostrarInfo("No hay incidencias registradas.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error SQL al listar incidencias: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al listar incidencias: " + e.getMessage());
        }
    }

    // Busca una incidencia según el ID de la instancia asociada
    private void buscarPorInstancia() {
        int idInstancia = leerEntero("ID de la instancia: ");
        try {
            Incidencia i = proxy.obtenerIncidencia(idInstancia);
            if (i != null) System.out.println(i);
            else mostrarInfo("Incidencia no encontrada.");
        } catch (SQLException e) {
            mostrarError("Error SQL al buscar incidencia: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al buscar incidencia: " + e.getMessage());
        }
    }

    // Lista todas las incidencias registradas por un funcionario específico
    private void listarPorFuncionario() {
        int idFuncionario = leerEntero("ID del funcionario: ");
        try {
            List<Incidencia> lista = proxy.listarPorFuncionario(idFuncionario);
            if (lista.isEmpty()) mostrarInfo("No hay incidencias registradas para este funcionario.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error SQL al listar incidencias por funcionario: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al listar incidencias por funcionario: " + e.getMessage());
        }
    }

    // Modifica los datos de una incidencia existente
    private void modificarIncidencia() {
        int id = leerEntero("ID de la incidencia a modificar: ");
        String titulo = leerTexto("Nuevo título: ");
        OffsetDateTime fecha = leerFechaHora("Nueva fecha y hora (YYYY-MM-DDTHH:MM): ");
        String descripcion = leerTexto("Nueva descripción: ");
        boolean activo = leerBoolean("¿Está activa? (true/false): ");
        int idFuncionario = leerEntero("Nuevo ID de funcionario: ");
        String lugar = leerTexto("Nuevo lugar: ");

        try {
            boolean exito = proxy.actualizarIncidencia(id, titulo, fecha, descripcion, activo, idFuncionario, lugar);
            if (exito) mostrarExito("Incidencia modificada correctamente.");
            else mostrarError("No se pudo modificar la incidencia.");
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al modificar incidencia: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al modificar incidencia: " + e.getMessage());
        }
    }

    // Elimina una incidencia del sistema según su ID
    private void eliminarIncidencia() {
        int id = leerEntero("ID de la incidencia a eliminar: ");
        try {
            boolean exito = proxy.eliminarIncidencia(id);
            if (exito) mostrarExito("Incidencia eliminada correctamente.");
            else mostrarError("No se pudo eliminar la incidencia.");
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al eliminar incidencia: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al eliminar incidencia: " + e.getMessage());
        }
    }
}
