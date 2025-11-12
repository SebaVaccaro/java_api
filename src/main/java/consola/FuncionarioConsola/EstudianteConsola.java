package consola.FuncionarioConsola;

import consola.InterfazConsola.UIBase;
import PROXY.EstudianteProxy;
import SINGLETON.SesionSingleton;
import modelo.Estudiante;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EstudianteConsola extends UIBase {

    private final EstudianteProxy proxy;
    private final SesionSingleton sesionSingleton;

    // Constructor
    public EstudianteConsola() throws Exception {
        this.proxy = new EstudianteProxy();
        this.sesionSingleton = SesionSingleton.getInstance();
    }

    // Mostrar menú principal
    @Override
    public void mostrarMenu() {
        System.out.println("\n===== MENÚ DE GESTIÓN DE ESTUDIANTES =====");
        System.out.println("1. Crear nuevo estudiante");
        System.out.println("2. Listar todos los estudiantes");
        System.out.println("3. Buscar estudiante por ID");
        System.out.println("4. Modificar estudiante existente");
        System.out.println("5. Desactivar estudiante");
        System.out.println("0. Volver al menú principal");
        System.out.println("==========================================");
    }


    // Manejar opción seleccionada
    @Override
    public void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> crearEstudiante();
            case 2 -> listarTodos();
            case 3 -> buscarPorId();
            case 4 -> modificarEstudiante();
            case 5 -> desactivarEstudiante();
            case 0 -> mostrarInfo("Volviendo al menú principal...");
            default -> mostrarError("Opción inválida. Intente nuevamente.");
        }
    }

    // Crear nuevo estudiante
    private void crearEstudiante() {
        if (!sesionSingleton.haySesionActiva()) {
            mostrarError("No hay sesión activa.");
            return;
        }

        String cedula = leerTextoNoNull("Cédula: ");
        String nombre = leerTextoNoNullNoNumero("Nombre: ");
        String apellido = leerTextoNoNullNoNumero("Apellido: ");
        String password = leerTextoNoNull("Password: ");
        int idGrupo = leerEnteroNoNull("ID de grupo: ");
        LocalDate fechaNacimiento = leerFecha("Fecha de nacimiento (YYYY-MM-DD): ");

        try {
            Estudiante e = proxy.crearEstudiante(
                    cedula, nombre, apellido, password, idGrupo, fechaNacimiento
            );
            mostrarExito("Estudiante creado correctamente: " + e);
        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al crear estudiante: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Listar todos los estudiantes
    private void listarTodos() {
        if (!sesionSingleton.haySesionActiva()) {
            mostrarError("No hay sesión activa.");
            return;
        }

        try {
            List<Estudiante> lista = proxy.listarTodos();
            if (lista.isEmpty())
                mostrarInfo("No hay estudiantes registrados.");
            else
                lista.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error SQL al listar estudiantes: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Buscar estudiante por ID
    private void buscarPorId() {
        if (!sesionSingleton.haySesionActiva()) {
            mostrarError("No hay sesión activa.");
            return;
        }

        int idEstudiante = leerEntero("ID del estudiante: ");
        try {
            Estudiante e = proxy.obtenerPorId(idEstudiante);
            if (e != null)
                System.out.println(e);
            else
                mostrarInfo("No se encontró ningún estudiante con ese ID.");
        } catch (SQLException e) {
            mostrarError("Error SQL al buscar estudiante: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Modificar estudiante existente
    private void modificarEstudiante() {
        if (!sesionSingleton.haySesionActiva()) {
            mostrarError("No hay sesión activa.");
            return;
        }

        int idEstudiante = leerEntero("ID del estudiante a modificar: ");
        try {
            Estudiante e = proxy.obtenerPorId(idEstudiante);
            if (e == null) {
                mostrarInfo("El estudiante no existe.");
                return;
            }

            System.out.println("Estudiante seleccionado: " + e);
            System.out.println("Campos modificables: cedula, nombre, apellido, password, idGrupo, activo");

            String campo = leerTexto("Campo a modificar: ");
            boolean exito = false;

            switch (campo.toLowerCase()) {
                case "cedula" -> e.setCedula(leerTexto("Nueva cédula: "));
                case "nombre" -> e.setNombre(leerTextoNoNullNoNumero("Nuevo nombre: "));
                case "apellido" -> e.setApellido(leerTextoNoNullNoNumero("Nuevo apellido: "));
                case "password" -> e.setPassword(leerTexto("Nuevo password: "));
                case "idgrupo" -> e.setIdGrupo(leerEntero("Nuevo ID de grupo: "));
                case "activo" -> e.setActivo(leerBoolean("¿Activo? (true/false): "));
                default -> {
                    mostrarError("Campo inválido.");
                    return;
                }
            }

            exito = proxy.actualizarEstudiante(e);
            if (exito)
                mostrarExito("Estudiante modificado correctamente.");
            else
                mostrarError("No se pudo modificar el estudiante.");

        } catch (SQLException e) {
            mostrarError("Error SQL al modificar estudiante: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Desactivar estudiante
    private void desactivarEstudiante() {
        if (!sesionSingleton.haySesionActiva()) {
            mostrarError("No hay sesión activa.");
            return;
        }

        int idEstudiante = leerEntero("ID del estudiante a desactivar: ");
        try {
            boolean exito = proxy.desactivarEstudiante(idEstudiante);
            if (exito)
                mostrarExito("Estudiante desactivado correctamente.");
            else
                mostrarError("No se pudo desactivar el estudiante.");
        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al desactivar estudiante: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }
}
