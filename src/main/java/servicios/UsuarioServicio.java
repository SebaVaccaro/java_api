package servicios;

import SINGLETON.SesionSingleton;
import algoritmos.Encriptador;
import modelo.Usuario;
import modelo.Estudiante;
import modelo.Funcionario;
import modelo.Rol;

import java.util.Scanner;

public class UsuarioServicio {

    private final EstudianteServicio estudianteServicio;
    private final FuncionarioServicio funcionarioServicio;
    private final RolServicio rolServicio;

    public UsuarioServicio() throws Exception {
        this.estudianteServicio = new EstudianteServicio();
        this.funcionarioServicio = new FuncionarioServicio();
        this.rolServicio = new RolServicio();
    }

    public void cargarUsuario(Usuario usuario) throws Exception {

        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser null.");
        }

        if (usuario.getCorreo() == null || usuario.getCorreo().isBlank()) {
            throw new IllegalArgumentException("El correo del usuario no puede estar vacío.");
        }

        if (usuario.getIdUsuario() <= 0) {
            throw new IllegalArgumentException("ID de usuario inválido: debe ser mayor que 0.");
        }

        String correo = usuario.getCorreo().toLowerCase().trim();

        if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("El formato del correo electrónico es inválido.");
        }

        if (correo.endsWith("@estudiantes.utec.edu.uy")) {
            cargarEstudiante(usuario.getIdUsuario());
            return;
        }

        if (correo.endsWith("@utec.edu.uy")) {
            cargarFuncionario(usuario.getIdUsuario());
            return;
        }

        throw new IllegalArgumentException("Tipo de correo no permitido. Debe ser @estudiantes.utec.edu.uy o @utec.edu.uy");
    }

    private void cargarEstudiante(int idUsuario) throws Exception {
        Estudiante est = estudianteServicio.obtenerPorId(idUsuario);

        if (est == null) {
            throw new IllegalArgumentException("No se encontró el estudiante con ID " + idUsuario + " en la base de datos.");
        }

        validarDatosUsuario(est);

        if (!est.isActivo()) {
            aceptarPoliticasEstudiante(est);
        }

        SesionSingleton.getInstance().setUsuarioActual(est, "estudiante");
        System.out.println("Sesión iniciada como estudiante: " + est.getNombre() + " " + est.getApellido());
    }

    private void cargarFuncionario(int idUsuario) throws Exception {
        Funcionario func = funcionarioServicio.obtenerPorId(idUsuario);

        if (func == null) {
            throw new IllegalArgumentException("No se encontró el funcionario con ID " + idUsuario + " en la base de datos.");
        }

        validarDatosUsuario(func);

        if (func.getIdRol() <= 0) {
            throw new IllegalArgumentException("El funcionario no tiene un rol asignado.");
        }

        Rol rol = rolServicio.buscarPorId(func.getIdRol());
        if (rol == null) {
            throw new IllegalArgumentException("El rol con ID " + func.getIdRol() + " no existe.");
        }

        if (!func.isActivo()) {
            aceptarPoliticasFuncionario(func);
        }

        String nombreRol = rol.getNombre();
        SesionSingleton.getInstance().setUsuarioActual(func, nombreRol);
        System.out.println("Sesión iniciada como " + nombreRol + ": " + func.getNombre() + " " + func.getApellido());
    }

    private void validarDatosUsuario(Usuario usuario) {

        if (usuario.getNombre() == null || usuario.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del usuario no puede estar vacío.");
        }

        if (usuario.getApellido() == null || usuario.getApellido().isBlank()) {
            throw new IllegalArgumentException("El apellido del usuario no puede estar vacío.");
        }

        if (usuario.getUsername() == null || usuario.getUsername().isBlank()) {
            throw new IllegalArgumentException("El username del usuario no puede estar vacío.");
        }

        if (usuario.getCedula() == null || usuario.getCedula().isBlank()) {
            throw new IllegalArgumentException("La cédula del usuario no puede estar vacía.");
        }

        if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
            throw new IllegalArgumentException("La contraseña del usuario no puede estar vacía.");
        }
    }

    private void aceptarPoliticasEstudiante(Estudiante est) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n========================================");
        System.out.println("POLÍTICAS DE USO - UTEC");
        System.out.println("========================================");
        System.out.println("Debes aceptar las políticas de UTEC para continuar.");
        System.out.println("Si no aceptas, no podrás acceder al sistema.");
        System.out.println("========================================\n");

        int intentos = 0;
        final int MAX_INTENTOS = 3;

        while (intentos < MAX_INTENTOS) {
            System.out.print("¿Aceptas las políticas? (SI/NO): ");
            String op = sc.nextLine().trim().toUpperCase();

            if (op.equalsIgnoreCase("si")) {
                est.setActivo(true);
                boolean actualizado = estudianteServicio.actualizarEstudiante(est);

                if (!actualizado) {
                    throw new RuntimeException("Error al actualizar el estado del estudiante.");
                }

                System.out.println("Políticas aceptadas correctamente.");
                return;
            }

            if (op.equalsIgnoreCase("no")) {
                System.out.println("No puedes acceder al sistema sin aceptar las políticas.");
                throw new IllegalStateException("El estudiante rechazó las políticas de uso.");
            }

            intentos++;
            System.out.println("Opción inválida. Por favor ingresa SI o NO.");

            if (intentos >= MAX_INTENTOS) {
                System.out.println("Número máximo de intentos alcanzado.");
                throw new IllegalStateException("Demasiados intentos fallidos al aceptar políticas.");
            }
        }
    }

    private void aceptarPoliticasFuncionario(Funcionario func) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n========================================");
        System.out.println("POLÍTICAS DE USO - UTEC");
        System.out.println("========================================");
        System.out.println("Debes aceptar las políticas de UTEC para continuar.");
        System.out.println("Si no aceptas, no podrás acceder al sistema.");
        System.out.println("========================================\n");

        int intentos = 0;
        final int MAX_INTENTOS = 3;

        while (intentos < MAX_INTENTOS) {
            System.out.print("¿Aceptas las políticas? (SI/NO): ");
            String op = sc.nextLine().trim().toUpperCase();

            if (op.equalsIgnoreCase("si")) {
                func.setActivo(true);

                boolean actualizado = funcionarioServicio.actualizarFuncionario(
                        func.getIdUsuario(),
                        func.getCedula(),
                        func.getNombre(),
                        func.getApellido(),
                        func.getUsername(),
                        Encriptador.desencriptar(func.getPassword()),
                        func.getCorreo(),
                        func.getIdRol(),
                        func.isActivo()
                );

                if (!actualizado) {
                    throw new RuntimeException("Error al actualizar el estado del funcionario.");
                }

                System.out.println("Políticas aceptadas correctamente.");
                return;
            }

            if (op.equalsIgnoreCase("no")) {
                System.out.println("No puedes acceder al sistema sin aceptar las políticas.");
                throw new IllegalStateException("El funcionario rechazó las políticas de uso.");
            }

            intentos++;
            System.out.println("Opción inválida. Por favor ingresa SI o NO.");

            if (intentos >= MAX_INTENTOS) {
                System.out.println("Número máximo de intentos alcanzado.");
                throw new IllegalStateException("Demasiados intentos fallidos al aceptar políticas.");
            }
        }
    }
}