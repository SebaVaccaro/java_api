package consola.FuncionarioConsola;

import consola.InterfazConsola.UIBase;
import PROXY.ObservacionProxy;
import modelo.Observacion;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

public class ObservacionConsola extends UIBase {

    private final ObservacionProxy proxy;

    // Constructor: inicializa el proxy de observaciones
    public ObservacionConsola() throws Exception {
        this.proxy = new ObservacionProxy();
    }

    // Muestra el menú principal de observaciones
    @Override
    public void mostrarMenu() {
        System.out.println("\n===== MENÚ OBSERVACIONES =====");
        System.out.println("1. Crear observación");
        System.out.println("2. Listar todas");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Modificar observación");
        System.out.println("5. Desactivar observación");
        System.out.println("0. Volver al menú principal");
        System.out.println("================================");
    }

    // Maneja la opción seleccionada por el usuario
    @Override
    public void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> crearObservacion();
                case 2 -> listarTodas();
                case 3 -> buscarPorId();
                case 4 -> modificarObservacion();
                case 5 -> desactivarObservacion();
                case 0 -> mostrarInfo("Volviendo al menú principal...");
                default -> mostrarError("Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error al procesar la opción: " + e.getMessage());
        }
    }

    // Crea una nueva observación
    private void crearObservacion() {
        int idFuncionario = leerEntero("ID del funcionario: ");
        int idEstudiante = leerEntero("ID del estudiante: ");
        String titulo = leerTexto("Título: ");
        String contenido = leerTexto("Contenido: ");
        OffsetDateTime fecHora = OffsetDateTime.now();

        try {
            Observacion nueva = proxy.crearObservacion(idFuncionario, idEstudiante, titulo, contenido, fecHora);
            mostrarExito("Observación creada correctamente: " + nueva);
        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Lista todas las observaciones registradas
    private void listarTodas() {
        try {
            List<Observacion> lista = proxy.listarTodas();
            if (lista.isEmpty()) mostrarInfo("No hay observaciones registradas.");
            else lista.forEach(System.out::println);
        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Busca una observación por su ID
    private void buscarPorId() {
        int id = leerEntero("ID de observación: ");
        try {
            Observacion obs = proxy.obtenerObservacion(id);
            if (obs != null) System.out.println(obs);
            else mostrarInfo("Observación no encontrada.");
        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Modifica una observación existente
    private void modificarObservacion() {
        int id = leerEntero("ID de observación a modificar: ");
        try {
            Observacion existente = proxy.obtenerObservacion(id);
            if (existente == null) {
                mostrarInfo("Observación no encontrada.");
                return;
            }

            int idFuncionario = leerEntero("Nuevo ID funcionario [" + existente.getIdFuncionario() + "]: ", existente.getIdFuncionario());
            int idEstudiante = leerEntero("Nuevo ID estudiante [" + existente.getIdEstudiante() + "]: ", existente.getIdEstudiante());
            String titulo = leerTexto("Nuevo título [" + existente.getTitulo() + "]: ", existente.getTitulo());
            String contenido = leerTexto("Nuevo contenido [" + existente.getContenido() + "]: ", existente.getContenido());
            OffsetDateTime fecHora = OffsetDateTime.now();

            Observacion actualizada = new Observacion(
                    id,
                    idFuncionario,
                    idEstudiante,
                    titulo,
                    contenido,
                    fecHora,
                    true
            );

            boolean exito = proxy.actualizarObservacion(actualizada);
            if (exito) mostrarExito("Observación modificada correctamente.");
            else mostrarError("No se pudo modificar la observación.");
        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Desactiva una observación por su ID
    private void desactivarObservacion() {
        int id = leerEntero("ID de observación a desactivar: ");
        try {
            boolean exito = proxy.desactivarObservacion(id);
            if (exito) mostrarExito("Observación desactivada correctamente.");
            else mostrarError("No se pudo desactivar la observación.");
        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }
}
