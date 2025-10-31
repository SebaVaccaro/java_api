package consola.FuncionarioConsola;

import consola.InterfazConsola.UIBase;
import PROXY.FuncionarioProxy;
import modelo.Funcionario;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class FuncionarioConsola extends UIBase {

    private final FuncionarioProxy proxy;

    // Constructor: inicializa el proxy que gestiona las operaciones de funcionarios
    public FuncionarioConsola() throws Exception {
        this.proxy = new FuncionarioProxy();
    }

    // Mostrar el menú principal del módulo de funcionarios
    @Override
    public void mostrarMenu() {
        System.out.println("\n===== MENÚ FUNCIONARIOS =====");
        System.out.println("1. Crear funcionario");
        System.out.println("2. Listar todos");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Modificar funcionario");
        System.out.println("5. Desactivar funcionario");
        System.out.println("0. Volver al menú anterior");
        System.out.println("==============================");
    }

    // Manejar la opción seleccionada por el usuario
    @Override
    public void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> crearFuncionario();       // Crear nuevo funcionario
                case 2 -> listarTodos();            // Listar todos los funcionarios
                case 3 -> buscarPorId();            // Buscar funcionario por ID
                case 4 -> modificarFuncionario();   // Modificar información de un funcionario
                case 5 -> desactivarFuncionario();  // Desactivar funcionario
                case 0 -> mostrarInfo("Volviendo al menú principal...");
                default -> mostrarError("Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error al ejecutar la opción: " + e.getMessage());
        }
    }

    // Crear un nuevo funcionario
    private void crearFuncionario() {
        String cedula = leerTexto("Cédula: ");
        String nombre = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        String username = leerTexto("Username: ");
        String password = leerTexto("Password: ");
        int idRol = leerEntero("ID de rol: ");
        LocalDate fechaNacimiento = leerFecha("Fecha de nacimiento (YYYY-MM-DD): ");

        try {
            Funcionario f = proxy.crearFuncionario(cedula, nombre, apellido, username, password, idRol, fechaNacimiento);
            mostrarExito("Funcionario creado correctamente: " + f);
        } catch (SecurityException ex) {
            mostrarError(ex.getMessage());
        } catch (SQLException ex) {
            mostrarError("Error SQL al crear funcionario: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al crear funcionario: " + ex.getMessage());
        }
    }

    // Listar todos los funcionarios registrados
    private void listarTodos() {
        try {
            List<Funcionario> lista = proxy.listarTodos();
            if (lista.isEmpty()) {
                mostrarInfo("No hay funcionarios registrados.");
            } else {
                lista.forEach(System.out::println);
            }
        } catch (SQLException ex) {
            mostrarError("Error SQL al listar funcionarios: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al listar funcionarios: " + ex.getMessage());
        }
    }

    // Buscar un funcionario por su ID
    private void buscarPorId() {
        int idFuncionario = leerEntero("ID del funcionario: ");
        try {
            Funcionario f = proxy.obtenerPorId(idFuncionario);
            if (f != null) {
                System.out.println(f);
            } else {
                mostrarInfo("Funcionario no encontrado.");
            }
        } catch (SQLException ex) {
            mostrarError("Error SQL al buscar funcionario: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al buscar funcionario: " + ex.getMessage());
        }
    }

    // Modificar los datos de un funcionario existente
    private void modificarFuncionario() {
        int idFuncionario = leerEntero("ID del funcionario a modificar: ");
        String cedula = leerTexto("Nueva cédula: ");
        String nombre = leerTexto("Nuevo nombre: ");
        String apellido = leerTexto("Nuevo apellido: ");
        String username = leerTexto("Nuevo username: ");
        String password = leerTexto("Nuevo password: ");
        String correo = leerTexto("Nuevo correo: ");
        int idRol = leerEntero("Nuevo ID de rol: ");
        boolean estActivo = leerBoolean("¿Activo? (true/false): ");

        try {
            boolean exito = proxy.actualizarFuncionario(
                    idFuncionario, cedula, nombre, apellido, username, password, correo, idRol, estActivo
            );
            if (exito) {
                mostrarExito("Funcionario modificado correctamente.");
            } else {
                mostrarError("No se pudo modificar el funcionario.");
            }
        } catch (SQLException ex) {
            mostrarError("Error SQL al modificar funcionario: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al modificar funcionario: " + ex.getMessage());
        }
    }

    // Desactivar un funcionario (cambia su estado a inactivo)
    private void desactivarFuncionario() {
        int idFuncionario = leerEntero("ID del funcionario a desactivar: ");
        try {
            boolean exito = proxy.desactivarFuncionario(idFuncionario);
            if (exito) {
                mostrarExito("Funcionario desactivado correctamente.");
            } else {
                mostrarError("No se pudo desactivar el funcionario.");
            }
        } catch (SecurityException ex) {
            mostrarError(ex.getMessage());
        } catch (SQLException ex) {
            mostrarError("Error SQL al desactivar funcionario: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al desactivar funcionario: " + ex.getMessage());
        }
    }
}

