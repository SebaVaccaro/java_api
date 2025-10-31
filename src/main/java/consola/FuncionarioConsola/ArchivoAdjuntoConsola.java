package consola.FuncionarioConsola;

import consola.InterfazConsola.UIBase;
import PROXY.ArchivoAdjuntoProxy;
import modelo.ArchivoAdjunto;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;


public class ArchivoAdjuntoConsola extends UIBase {

    private final ArchivoAdjuntoProxy facade;

    public ArchivoAdjuntoConsola() throws Exception {
        this.facade = new ArchivoAdjuntoProxy();
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n--- MENÚ ARCHIVOS ADJUNTOS (ADMIN) ---");
        System.out.println("1. Crear archivo");
        System.out.println("2. Listar activos");
        System.out.println("3. Listar por estudiante");
        System.out.println("4. Modificar archivo");
        System.out.println("5. Eliminar archivo");
        System.out.println("0. Volver al menú principal");
    }

    @Override
    public void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> crearArchivo();
            case 2 -> listarActivos();
            case 3 -> listarPorEstudiante();
            case 4 -> modificarArchivo();
            case 5 -> eliminarArchivo();
            case 0 -> mostrarInfo("Volviendo al menú principal...");
            default -> mostrarError("Opción inválida.");
        }
    }

    // ==========================================================
    // OPCIONES DEL MENÚ
    // ==========================================================
    private void crearArchivo() {
        int idUsuario = leerEntero("ID Usuario (quien crea el archivo): ");
        int idEstudiante = leerEntero("ID Estudiante: ");
        String ruta = leerTexto("Ruta del archivo: ");
        String categoria = leerTexto("Categoría: ");

        try {
            ArchivoAdjunto archivo = facade.crearArchivo(idUsuario, idEstudiante, ruta, categoria);
            mostrarExito("Archivo creado: " + archivo);
        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al crear archivo: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    private void listarActivos() {
        try {
            List<ArchivoAdjunto> list = facade.listarActivos();
            if (list.isEmpty()) mostrarInfo("No hay archivos activos.");
            else list.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error al listar activos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void listarPorEstudiante() {
        int idUsuario = leerEntero("ID Usuario (quien consulta): ");
        int idEstudiante = leerEntero("ID Estudiante: ");

        try {
            List<ArchivoAdjunto> list = facade.listarPorEstudiante(idUsuario, idEstudiante);
            if (list.isEmpty()) mostrarInfo("No hay archivos para este estudiante.");
            else list.forEach(System.out::println);
        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al listar archivos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    private void modificarArchivo() {
        int idUsuario = leerEntero("ID Usuario (quien modifica): ");
        int idArchivo = leerEntero("ID del archivo a modificar: ");

        try {
            ArchivoAdjunto a = facade.obtenerPorId(idUsuario, idArchivo);

            if (a == null) {
                mostrarInfo("El archivo no existe.");
                return;
            }

            System.out.println("Archivo seleccionado:");
            System.out.println(a);

            System.out.println("\nCampos modificables: ruta, categoria, idUsuario, idEstudiante");
            String campo = leerTexto("Campo a modificar: ");

            boolean exito = false;

            switch (campo.toLowerCase()) {
                case "ruta" -> {
                    String nuevaRuta = leerTexto("Nueva ruta: ");
                    a.setRuta(nuevaRuta);
                    exito = facade.actualizarArchivo(a);
                }
                case "categoria" -> {
                    String nuevaCat = leerTexto("Nueva categoría: ");
                    a.setCategoria(nuevaCat);
                    exito = facade.actualizarArchivo(a);
                }
                case "idusuario" -> {
                    int nuevoId = leerEntero("Nuevo ID Usuario: ");
                    a.setIdUsuario(nuevoId);
                    exito = facade.actualizarArchivo(a);
                }
                case "idestudiante" -> {
                    int nuevoId = leerEntero("Nuevo ID Estudiante: ");
                    a.setIdEstudiante(nuevoId);
                    exito = facade.actualizarArchivo(a);
                }
                default -> mostrarError("Campo inválido.");
            }

            if (exito) mostrarExito("Archivo modificado correctamente.");
            else mostrarError("No se pudo modificar el archivo.");

        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al modificar archivo: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    private void eliminarArchivo() {
        int idArchivo = leerEntero("ID del archivo a eliminar: ");
        try {
            if (facade.eliminar(idArchivo)) {
                mostrarExito("Archivo eliminado correctamente.");
            } else {
                mostrarError("No se pudo eliminar el archivo.");
            }
        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al eliminar archivo: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }
}

