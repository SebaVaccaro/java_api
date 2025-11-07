package servicios;

import SINGLETON.SesionSingleton;
import algoritmos.Encriptador;
import modelo.Usuario;
import modelo.Estudiante;
import modelo.Funcionario;

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

        String correo = usuario.getCorreo().toLowerCase();

        // Estudiante
        if (correo.endsWith("@estudiantes.utec.edu.uy")) {

            Estudiante est = estudianteServicio.obtenerPorId(usuario.getIdUsuario());

            if (est == null)
                throw new IllegalArgumentException("No se encontró el estudiante en la base de datos.");

            if (!est.isActivo())
                aceptarPoliticasEstudiante(est);

            SesionSingleton.getInstance().setUsuarioActual(est, "estudiante");
            return;
        }

        // Funcionario
        if (correo.endsWith("@utec.edu.uy")) {

            Funcionario func = funcionarioServicio.obtenerPorId(usuario.getIdUsuario());

            if (func == null)
                throw new IllegalArgumentException("No se encontró el funcionario en la base de datos.");

            if (!func.isActivo())
                aceptarPoliticasFuncionario(func);

            String nombreRol = rolServicio.buscarPorId(func.getIdRol()).getNombre();

            SesionSingleton.getInstance().setUsuarioActual(func, nombreRol);
            return;
        }

        throw new IllegalArgumentException("Tipo de correo no permitido: " + correo);
    }

    private void aceptarPoliticasEstudiante(Estudiante est) throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.println("Debes aceptar las políticas de UTEC para continuar.");

        while (true) {
            System.out.print("¿Aceptas las políticas? (SI/NO): ");
            String op = sc.nextLine().trim().toUpperCase();

            if (op.equals("SI")) {
                est.setActivo(true);
                estudianteServicio.actualizarEstudiante(est);
                System.out.println("Políticas aceptadas.");
                return;
            }

            if (op.equals("NO")) {
                System.out.println("No puedes acceder sin aceptar las políticas.");
                continue;
            }

            System.out.println("Opción inválida.");
        }
    }

    private void aceptarPoliticasFuncionario(Funcionario func) throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.println("Debes aceptar las políticas de UTEC para continuar.");

        while (true) {
            System.out.print("¿Aceptas las políticas? (SI/NO): ");
            String op = sc.nextLine().trim().toUpperCase();

            if (op.equals("SI")) {
                func.setActivo(true);

                funcionarioServicio.actualizarFuncionario(
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

                System.out.println("Políticas aceptadas.");
                return;
            }

            if (op.equals("NO")) {
                System.out.println("No puedes acceder sin aceptar las políticas.");
                continue;
            }

            System.out.println("Opción inválida.");
        }
    }
}
