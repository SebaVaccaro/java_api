package consola.FuncionarioConsola;

import consola.InterfazConsola.UIBase;
import PROXY.FuncionarioProxy;
import SINGLETON.SesionSingleton;
import modelo.Funcionario;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class FuncionarioConsola extends UIBase {

    private final FuncionarioProxy proxy;
    private final SesionSingleton sesionSingleton;

    // Constructor: inicializa el proxy y la sesión del usuario
    public FuncionarioConsola() throws Exception {
        this.proxy = new FuncionarioProxy();
        this.sesionSingleton = SesionSingleton.getInstance();
    }

    // Mostrar menú principal
    @Override
    public void mostrarMenu() {
        System.out.println("\n===== MENÚ DE GESTIÓN DE FUNCIONARIOS =====");
        System.out.println("1. Crear nuevo funcionario");
        System.out.println("2. Listar todos los funcionarios");
        System.out.println("3. Buscar funcionario por ID");
        System.out.println("4. Modificar funcionario existente");
        System.out.println("5. Desactivar funcionario");
        System.out.println("0. Volver al menú principal");
        System.out.println("==========================================");
    }


    // Manejar opción seleccionada
    @Override
    public void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> crearFuncionario();
            case 2 -> listarTodos();
            case 3 -> buscarPorId();
            case 4 -> modificarFuncionario();
            case 5 -> desactivarFuncionario();
            case 0 -> mostrarInfo("Volviendo al menú principal...");
            default -> mostrarError("Opción inválida.");
        }
    }

    // Crear un nuevo funcionario
    private void crearFuncionario() {
        if (!sesionSingleton.haySesionActiva()) {
            mostrarError("No hay sesión activa.");
            return;
        }

        String cedula = leerTexto("Cédula: ");
        String nombre = leerNombreApellido("Nombre: ");
        String apellido = leerNombreApellido("Apellido: ");
        String password = leerTexto("Password: ");
        int idRol = leerEntero("ID de rol: ");
        LocalDate fechaNacimiento = leerFecha("Fecha de nacimiento (YYYY-MM-DD): ");

        try {
            Funcionario f = proxy.crearFuncionario(
                    cedula, nombre, apellido, password, idRol, fechaNacimiento
            );
            mostrarExito("Funcionario creado correctamente: " + f);
        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    // Listar todos los funcionarios
    private void listarTodos() {
        if (!sesionSingleton.haySesionActiva()) {
            mostrarError("No hay sesión activa.");
            return;
        }

        try {
            List<Funcionario> lista = proxy.listarTodos();
            if (lista.isEmpty())
                mostrarInfo("No hay funcionarios registrados.");
            else
                lista.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    // Buscar funcionario por ID
    private void buscarPorId() {
        if (!sesionSingleton.haySesionActiva()) {
            mostrarError("No hay sesión activa.");
            return;
        }

        int idFuncionario = leerEntero("ID del funcionario: ");
        try {
            Funcionario f = proxy.obtenerPorId(idFuncionario);
            if (f != null)
                System.out.println(f);
            else
                mostrarInfo("No se encontró ningún funcionario con ese ID.");
        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    // Modificar los datos de un funcionario existente
    private void modificarFuncionario() {
        if (!sesionSingleton.haySesionActiva()) {
            mostrarError("No hay sesión activa.");
            return;
        }

        int idFuncionario = leerEntero("ID del funcionario a modificar: ");

        try {
            Funcionario f = proxy.obtenerPorId(idFuncionario);
            if (f == null) {
                mostrarInfo("El funcionario no existe.");
                return;
            }

            System.out.println("Funcionario seleccionado: " + f);
            System.out.println("Campos modificables: cedula, nombre, apellido, username, password, idRol, activo");

            String campo = leerTexto("Campo a modificar: ");
            boolean exito = false;

            switch (campo.toLowerCase()) {
                case "cedula" -> f.setCedula(leerTexto("Nueva cédula: "));
                case "nombre" -> f.setNombre(leerNombreApellido("Nuevo nombre: "));
                case "apellido" -> f.setApellido(leerNombreApellido("Nuevo apellido: "));
                case "password" -> f.setPassword(leerTexto("Nuevo password: "));
                case "idrol" -> f.setIdRol(leerEntero("Nuevo ID de rol: "));
                case "activo" -> f.setActivo(leerBoolean("¿Activo? (true/false): "));
                default -> {
                    mostrarError("Campo inválido.");
                    return;
                }
            }

            exito = proxy.actualizarFuncionario(
                    f.getIdUsuario(), f.getCedula(), f.getNombre(), f.getApellido(),
                    f.getUsername(), f.getPassword(), f.getCorreo(), f.getIdRol(), f.isActivo()
            );

            if (exito)
                mostrarExito("Funcionario modificado correctamente.");
            else
                mostrarError("No se pudo modificar el funcionario.");

        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    // Desactivar un funcionario (cambia su estado a inactivo)
    private void desactivarFuncionario() {
        if (!sesionSingleton.haySesionActiva()) {
            mostrarError("No hay sesión activa.");
            return;
        }

        int idFuncionario = leerEntero("ID del funcionario a desactivar: ");
        try {
            boolean exito = proxy.desactivarFuncionario(idFuncionario);
            if (exito)
                mostrarExito("Funcionario desactivado correctamente.");
            else
                mostrarError("No se pudo desactivar el funcionario.");
        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }
}


