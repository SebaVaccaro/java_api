package consola.FuncionarioConsola;

import consola.InterfazConsola.UIBase;
import PROXY.ArchivoAdjuntoProxy;
import modelo.ArchivoAdjunto;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class ArchivoAdjuntoConsola extends UIBase {

    private final ArchivoAdjuntoProxy facade;

    // Constructor: inicializa el proxy para manejar las operaciones de archivos adjuntos
    public ArchivoAdjuntoConsola() throws Exception {
        this.facade = new ArchivoAdjuntoProxy();
    }

    // Mostrar el menú principal de gestión de archivos adjuntos
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

    // Manejar la opción elegida por el usuario
    @Override
    public void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> crearArchivo();       // Crear un nuevo archivo adjunto
            case 2 -> listarActivos();      // Listar archivos activos
            case 3 -> listarPorEstudiante();// Listar archivos de un estudiante específico
            case 4 -> modificarArchivo();   // Modificar un archivo existente
            case 5 -> eliminarArchivo();    // Eliminar (desactivar) un archivo
            case 0 -> mostrarInfo("Volviendo al menú principal...");
            default -> mostrarError("Opción inválida.");
        }
    }

    // Crear un nuevo archivo adjunto
    private void crearArchivo() {
        int idUsuario = leerEntero("ID Usuario (quien crea el archivo): ");
        int idEstudiante = leerEntero("ID Estudiante: ");
        String ruta = leerTexto("Ruta del archivo: ");
        String categoria = leerTexto("Categoría: ");

        try {
            ArchivoAdjunto archivo = facade.crearArchivo(idUsuario, idEstudiante, ruta, categoria);
            mostrarExito("Archivo creado con éxito: " + archivo);
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

    // Listar todos los archivos adjuntos activos
    private void listarActivos() {
        try {
            List<ArchivoAdjunto> list = facade.listarActivos();

            if (list.isEmpty())
                mostrarInfo("No hay archivos activos.");
            else
                list.forEach(System.out::println);

        } catch (SQLException e) {
            mostrarError("Error al listar activos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // Listar archivos adjuntos de un estudiante específico
    private void listarPorEstudiante() {
        int idUsuario = leerEntero("ID Usuario (quien consulta): ");
        int idEstudiante = leerEntero("ID Estudiante: ");

        try {
            List<ArchivoAdjunto> list = facade.listarPorEstudiante(idUsuario, idEstudiante);

            if (list.isEmpty())
                mostrarInfo("No hay archivos para este estudiante.");
            else
                list.forEach(System.out::println);

        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al listar archivos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Modificar un archivo adjunto existente
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

            // Se modifica el campo indicado por el usuario
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

            if (exito)
                mostrarExito("Archivo modificado correctamente.");
            else
                mostrarError("No se pudo modificar el archivo.");

        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al modificar archivo: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Eliminar (desactivar) un archivo adjunto
    private void eliminarArchivo() {
        int idArchivo = leerEntero("ID del archivo a eliminar: ");

        try {
            boolean eliminado = facade.eliminar(idArchivo);

            if (eliminado)
                mostrarExito("Archivo eliminado correctamente.");
            else
                mostrarError("No se pudo eliminar el archivo.");

        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al eliminar archivo: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }
}


